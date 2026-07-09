package drunvisual.gui.core;

import drunvisual.module.ClientModule;

public interface GuiEntry {
    String a();

    boolean b();

    void a(boolean z);

    default boolean c() {
        return true;
    }

    default boolean d() {
        return false;
    }

    default ClientModule e() {
        return null;
    }

    default int f() {
        ClientModule clientModuleE = e();
        if (clientModuleE != null) {
            return clientModuleE.j();
        }
        return -1;
    }

    default void a(int i) {
        ClientModule clientModuleE = e();
        if (clientModuleE != null) {
            clientModuleE.a(i);
        }
    }

    default boolean g() {
        ClientModule clientModuleE = e();
        return (clientModuleE == null || clientModuleE.m().isEmpty()) ? false : true;
    }

    default boolean h() {
        return d();
    }
}
