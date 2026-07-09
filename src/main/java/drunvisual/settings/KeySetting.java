package drunvisual.settings;

import java.util.Locale;
import org.lwjgl.glfw.GLFW;

public class KeySetting extends Setting<Integer> {
    public KeySetting(String str, String str2, int i) {
        super(str, str2, Integer.valueOf(i));
    }

    public KeySetting(String str, int i) {
        this(str, "", i);
    }

    public KeySetting(String str) {
        this(str, "", -1);
    }

    public int key() {
        return k().intValue();
    }

    public void setKey(int i) {
        super.a(Integer.valueOf(i));
    }

    public boolean isBound() {
        return key() != -1;
    }

    public void clear() {
        setKey(-1);
    }

    public String displayName() {
        int iKey = key();
        if (iKey == -1) {
            return "";
        }
        String strGlfwGetKeyName = GLFW.glfwGetKeyName(iKey, 0);
        return strGlfwGetKeyName != null ? strGlfwGetKeyName.toUpperCase(Locale.ROOT) : "KEY " + iKey;
    }

    public int a() {
        return key();
    }

    public void a(int i) {
        setKey(i);
    }

    public boolean b() {
        return isBound();
    }

    public void c() {
        clear();
    }

    public String d() {
        return displayName();
    }
}
