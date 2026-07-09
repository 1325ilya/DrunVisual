package drunvisual.hud.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;
import ru.drunvisual.DrunVisual;
import drunvisual.config.LocalConfigManager;

public final class RemoteWatermarkText {
    private static final String DEFAULT_TEXT = "DrunVisuals";
    private static final String WATERMARK_URL = "https://raw.githubusercontent.com/1325ilya/DrunVisual/main/WaterMark";
    private static final String WATERMARK_API_URL = "https://api.github.com/repos/1325ilya/DrunVisual/contents/WaterMark?ref=main";
    private static final int CONNECT_TIMEOUT_MS = 5000;
    private static final int READ_TIMEOUT_MS = 5000;
    private static final int MAX_BYTES = 8192;
    private static final AtomicBoolean STARTED = new AtomicBoolean(false);
    private static volatile String currentText = DEFAULT_TEXT;

    private RemoteWatermarkText() {
    }

    public static void initAsync() {
        if (!STARTED.compareAndSet(false, true)) {
            return;
        }
        loadCachedText();
        Thread thread = new Thread(RemoteWatermarkText::refreshFromRemote, "drunvisual-watermark-text");
        thread.setDaemon(true);
        thread.start();
    }

    public static String getText() {
        String str = currentText;
        return str == null || str.isBlank() ? DEFAULT_TEXT : str;
    }

    private static void loadCachedText() {
        Path path = getCachePath();
        if (!Files.isRegularFile(path)) {
            return;
        }
        try {
            updateText(normalize(Files.readString(path, StandardCharsets.UTF_8)), "cache");
        } catch (IOException e) {
            DrunVisual.getLOGGER().warn("[WaterMark] Не удалось прочитать кэш {}", path, e);
        }
    }

    private static void refreshFromRemote() {
        try {
            String str = fetchFromRaw();
            if (str == null) {
                str = fetchFromApi();
            }
            if (str == null) {
                DrunVisual.getLOGGER().warn("[WaterMark] Не удалось получить содержимое WaterMark ни из raw, ни из GitHub API");
                return;
            }
            String strNormalize = normalize(str);
            updateText(strNormalize, "remote");
            writeCache(strNormalize);
        } catch (Exception e) {
            DrunVisual.getLOGGER().warn("[WaterMark] Не удалось обновить текст ватермарки из GitHub", e);
        }
    }

    private static String readUtf8Limited(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i < 0) {
                return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
            }
            if (byteArrayOutputStream.size() + i > MAX_BYTES) {
                throw new IOException("WaterMark file exceeds " + MAX_BYTES + " bytes");
            }
            byteArrayOutputStream.write(bArr, 0, i);
        }
    }

    private static void writeCache(String str) {
        Path path = getCachePath();
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, str, StandardCharsets.UTF_8);
        } catch (IOException e) {
            DrunVisual.getLOGGER().warn("[WaterMark] Не удалось сохранить кэш {}", path, e);
        }
    }

    private static void updateText(String str, String str2) {
        if (str == null || str.isBlank()) {
            return;
        }
        currentText = str;
        DrunVisual.getLOGGER().info("[WaterMark] Загружен текст ({}) -> {}", str2, str);
    }

    private static String normalize(String str) {
        if (str == null) {
            return DEFAULT_TEXT;
        }
        String strTrim = str.replace("\uFEFF", "").replace("\r", "\n").trim();
        if (strTrim.isBlank()) {
            return DEFAULT_TEXT;
        }
        String[] strArrSplit = strTrim.split("\n+");
        StringBuilder sb = new StringBuilder();
        for (String str2 : strArrSplit) {
            String strTrim2 = str2.trim();
            if (strTrim2.isEmpty()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(strTrim2);
        }
        return sb.length() > 0 ? sb.toString() : DEFAULT_TEXT;
    }

    private static Path getCachePath() {
        return Path.of(LocalConfigManager.get().configDirectory()).resolve("cache").resolve("WaterMark.txt");
    }

    private static String fetchFromRaw() {
        try {
            return fetchUtf8(WATERMARK_URL + "?ts=" + System.currentTimeMillis());
        } catch (Exception e) {
            DrunVisual.getLOGGER().warn("[WaterMark] raw.githubusercontent.com недоступен: {}", e.toString());
            return null;
        }
    }

    private static String fetchFromApi() {
        try {
            JSONObject jSONObject = new JSONObject(fetchUtf8(WATERMARK_API_URL));
            String strOptString = jSONObject.optString("encoding", "");
            String strOptString2 = jSONObject.optString("content", "");
            if (!"base64".equalsIgnoreCase(strOptString) || strOptString2.isBlank()) {
                throw new IOException("GitHub API returned unexpected payload");
            }
            return new String(Base64.getDecoder().decode(strOptString2.replace("\n", "")), StandardCharsets.UTF_8);
        } catch (Exception e) {
            DrunVisual.getLOGGER().warn("[WaterMark] GitHub API недоступен: {}", e.toString());
            return null;
        }
    }

    private static String fetchUtf8(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        try {
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT_MS);
            httpURLConnection.setReadTimeout(READ_TIMEOUT_MS);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Cache-Control", "no-cache, no-store, max-age=0");
            httpURLConnection.setRequestProperty("Pragma", "no-cache");
            httpURLConnection.setRequestProperty("Accept", "application/vnd.github+json");
            httpURLConnection.setRequestProperty("User-Agent", "DrunVisual/2.0.1");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("HTTP " + responseCode + " for " + str);
            }
            try (InputStream inputStream = httpURLConnection.getInputStream()) {
                return readUtf8Limited(inputStream);
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
