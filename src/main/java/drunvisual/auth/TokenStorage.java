package drunvisual.auth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;
import ru.drunvisual.DrunVisual;

public class TokenStorage {
    private static final String c = ".drunvisual";
    private static final String d = "token.dat";
    public static int a;
    public static boolean b;

    public static String a() {
        return Paths.get(System.getProperty("user.home"), c).resolve(d).toString();
    }

    public static boolean b() {
        return Files.exists(Paths.get(a(), new String[0]), new LinkOption[0]);
    }

    public static String c() {
        try {
            Path path = Paths.get(a(), new String[0]);
            if (!Files.exists(path, new LinkOption[0])) {
                return null;
            }
            String strTrim = new String(Files.readAllBytes(path), StandardCharsets.UTF_8).trim();
            if (strTrim.isEmpty()) {
                return null;
            }
            return strTrim;
        } catch (IOException e) {
            DrunVisual.getLOGGER().error("Ошибка чтения токена", e);
            return null;
        }
    }

    public static void a(String str) throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), c);
        Files.createDirectories(path, new FileAttribute[0]);
        Path pathResolve = path.resolve(d);
        Files.write(pathResolve, str.getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            try {

                Files.setPosixFilePermissions(pathResolve, PosixFilePermissions.fromString("rw-------"));
            } catch (Exception e) {
            }
        }

        DrunVisual.getLOGGER().info("Токен сохранен: " + String.valueOf(pathResolve));
    }

    public static void d() {
        try {
            Files.deleteIfExists(Paths.get(a(), new String[0]));

            DrunVisual.getLOGGER().info("Токен удален");
        } catch (IOException e) {
            DrunVisual.getLOGGER().error("Ошибка удаления токена", e);
        }
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
