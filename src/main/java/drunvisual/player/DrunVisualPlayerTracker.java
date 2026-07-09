package drunvisual.player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DrunVisualPlayerTracker {
    private static final Logger LOGGER = LogManager.getLogger("DrunVisualPlayerTracker");
    private static final String[][] BACKENDS = {new String[]{"https://dlnogqacbjwzrbgrtrtd.supabase.co", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRsbm9ncWFjYmp3enJiZ3J0cnRkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODExMDc5MTYsImV4cCI6MjA5NjY4MzkxNn0._BDk_C7WPNrMS6yfvMWesaTr3_IxnvwnhNzKzis09Bc"}, new String[]{"https://ofingkqficcmnvuzxykx.supabase.co", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9maW5na3FmaWNjbW52dXp4eWt4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODExMTAzMDUsImV4cCI6MjA5NjY4NjMwNX0.GXNqnzDolg_mzBVipL0QFRVAoU6d1fpwWalUlztwvqM"}};
    private static final DrunVisualPlayerTracker INSTANCE = new DrunVisualPlayerTracker();
    private volatile Set<String> drunvisualUsers = Collections.emptySet();
    private final AtomicBoolean pendingRegister = new AtomicBoolean(false);
    private final AtomicInteger activeBackend = new AtomicInteger(0);

    private DrunVisualPlayerTracker() {
    }

    public static DrunVisualPlayerTracker get() {
        return INSTANCE;
    }

    public void init() {
        ClientLifecycleEvents.CLIENT_STARTED.register(MinecraftClientVar -> {
            fetchUsers();
        });
        ClientPlayConnectionEvents.JOIN.register((ClientPlayNetworkHandlerVar, packetSender, MinecraftClientVar2) -> {
            this.pendingRegister.set(true);
            tryRegisterSelf(MinecraftClientVar2);
            fetchUsers();
        });
        ClientTickEvents.END_CLIENT_TICK.register(MinecraftClientVar3 -> {
            if (!this.pendingRegister.get() || MinecraftClientVar3.player == null) {
                return;
            }
            this.pendingRegister.set(false);
            registerSelf(MinecraftClientVar3.player.getName().getString());
        });
    }

    private void tryRegisterSelf(MinecraftClient MinecraftClientVar) {
        if (MinecraftClientVar.player == null) {
            return;
        }
        this.pendingRegister.set(false);
        registerSelf(MinecraftClientVar.player.getName().getString());
    }

    public void fetchUsers() {
        Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = new Thread(runnable, "drunvisual-tracker-fetch");
            thread.setDaemon(true);
            return thread;
        }).execute(() -> {
            int i = this.activeBackend.get();
            for (int i2 = 0; i2 < BACKENDS.length; i2++) {
                int length = (i + i2) % BACKENDS.length;
                if (tryFetch(length)) {
                    this.activeBackend.set(length);
                    return;
                }
                LOGGER.warn("[DrunVisual] Backend {} failed, trying next...", Integer.valueOf(length));
            }
            LOGGER.warn("[DrunVisual] All backends failed");
        });
    }

    private boolean tryFetch(int i) {
        try {
            String str = BACKENDS[i][0] + "/rest/v1/drunvisual_users?select=username";
            String str2 = BACKENDS[i][1];
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("apikey", str2);
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + str2);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            if (httpURLConnection.getResponseCode() != 200) {
                httpURLConnection.disconnect();
                return false;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    httpURLConnection.disconnect();
                    Set<String> usernames = parseUsernames(sb.toString());
                    this.drunvisualUsers = usernames;
                    LOGGER.info("[DrunVisual] Loaded {} DrunVisual users from backend {}", Integer.valueOf(usernames.size()), Integer.valueOf(i));
                    return true;
                }
                sb.append(line);
            }
        } catch (Exception e) {
            LOGGER.warn("[DrunVisual] Backend {} error: {}", Integer.valueOf(i), e.getMessage());
            return false;
        }
    }

    private void registerSelf(String str) {
        LOGGER.info("[DrunVisual] Registering self as: {}", str);
        Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = new Thread(runnable, "drunvisual-tracker-register");
            thread.setDaemon(true);
            return thread;
        }).execute(() -> {
            int i = this.activeBackend.get();
            for (int i2 = 0; i2 < BACKENDS.length; i2++) {
                if (tryRegister((i + i2) % BACKENDS.length, str)) {
                    fetchUsers();
                    return;
                }
            }
        });
    }

    private boolean tryRegister(int i, String str) {
        try {
            String str2 = BACKENDS[i][0] + "/rest/v1/drunvisual_users";
            String str3 = BACKENDS[i][1];
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str2).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("apikey", str3);
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + str3);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Prefer", "resolution=ignore-duplicates");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.getOutputStream().write(("{\"username\":\"" + str.toLowerCase() + "\"}").getBytes());
            httpURLConnection.getOutputStream().close();
            int responseCode = httpURLConnection.getResponseCode();
            httpURLConnection.disconnect();
            LOGGER.info("[DrunVisual] Register HTTP {} on backend {}", Integer.valueOf(responseCode), Integer.valueOf(i));
            return responseCode == 200 || responseCode == 201;
        } catch (Exception e) {
            LOGGER.warn("[DrunVisual] Register backend {} error: {}", Integer.valueOf(i), e.getMessage());
            return false;
        }
    }

    private static Set<String> parseUsernames(String str) {
        int iIndexOf;
        HashSet hashSet = new HashSet();
        String[] strArrSplit = str.split("\"username\":");
        for (int i = 1; i < strArrSplit.length; i++) {
            String strTrim = strArrSplit[i].trim();
            if (strTrim.startsWith("\"") && (iIndexOf = strTrim.indexOf("\"", 1)) > 1) {
                hashSet.add(strTrim.substring(1, iIndexOf).toLowerCase());
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public boolean has(String str) {
        if (str == null || str.isBlank()) {
            return false;
        }
        return this.drunvisualUsers.contains(str.toLowerCase());
    }
}
