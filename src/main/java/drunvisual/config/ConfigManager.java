package drunvisual.config;

import java.awt.Color;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import drunvisual.config.CloudConfigRepository;
import drunvisual.config.ConfigState;
import drunvisual.gui.core.DockPanelController;
import drunvisual.hud.notifications.Notification;
import drunvisual.config.LocalConfigManager;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.ItemToggleSetting;
import drunvisual.settings.KeySetting;
import drunvisual.settings.ModeSetting;
import drunvisual.settings.Setting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.settings.TokenSetting;
import ru.drunvisual.DrunVisual;
import drunvisual.auth.AccountServiceClient;
import drunvisual.gui.core.DrunVisualClickGuiScreen;
import drunvisual.gui.friends.FriendsPanel;
import drunvisual.gui.friends.FriendsTab;
import drunvisual.gui.friends.HistoryEntry;
import drunvisual.hud.core.HudElement;
import drunvisual.hud.core.HudElementManager;
import drunvisual.hud.notifications.HudNotificationCenter;
import drunvisual.hud.notifications.HudNotificationSettingsPanel;
import drunvisual.hud.notifications.NotificationStore;
import drunvisual.markers.MarkerOptions;
import drunvisual.modules.hud.ClientColor;

public class ConfigManager {
    private static final int b = 30;
    private CloudConfigRepository d;
    private ScheduledExecutorService e;
    private volatile boolean f = false;
    private static final ConfigManager a = new ConfigManager();
    private static final DateTimeFormatter c = DateTimeFormatter.ISO_LOCAL_DATE;

    private ConfigManager() {
    }

    public static ConfigManager a() {
        return a;
    }

    public void b() {
    }

    public void c() {
    }

    public void d() {
    }

    private void m() {
        if (this.e != null) {
            this.e.shutdown();
        }
        this.e = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "crypt");
            thread.setDaemon(true);
            return thread;
        });
        this.e.scheduleAtFixedRate(() -> {
            if (this.d == null || !this.d.i()) {
                return;
            }
            try {
                this.d.a(l(), (Runnable) null, (Consumer<String>) null);
            } catch (Exception e) {
            }
        }, 30L, 30L, TimeUnit.SECONDS);
    }

    public void e() {
    }

    public void f() {
    }

    public void a(String str, Runnable runnable, Consumer<String> consumer) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public void b(String str, Runnable runnable, Consumer<String> consumer) {
        if (str != null) {
            CloudConfigRepository cloudConfigRepositoryJ = j();
            if (cloudConfigRepositoryJ != null) {
                try {
                    Field declaredField = CloudConfigRepository.class.getDeclaredField("i");
                    declaredField.setAccessible(true);
                    ((Map) declaredField.get(cloudConfigRepositoryJ)).remove(str);
                } catch (Throwable th) {
                }
            }
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public void a(String str, String str2, Runnable runnable, Consumer<String> consumer) {
    }

    public boolean a(String str) {
        return false;
    }

    public String g() {
        return "default";
    }

    public void h() {
        try {
            LocalConfigManager.get().requestSave("state-changed");
        } catch (Exception e) {
            LogManager.getLogger("drunvisual/config").warn("[DrunVisual] Autosave request failed", e);
        }
    }

    public boolean i() {
        return true;
    }

    public CloudConfigRepository j() {
        if (this.d == null) {
            this.d = new CloudConfigRepository(AccountServiceClient.A());
            try {
                CloudConfigRepository.RemoteConfigRecord remoteConfigRecord = new CloudConfigRepository.RemoteConfigRecord();
                remoteConfigRecord.b = "default";
                remoteConfigRecord.c = "";
                remoteConfigRecord.g = System.currentTimeMillis();
                Field declaredField = CloudConfigRepository.class.getDeclaredField("i");
                declaredField.setAccessible(true);
                ((Map) declaredField.get(this.d)).put("default", remoteConfigRecord);
            } catch (Throwable th) {
            }
        }
        return this.d;
    }

    public void a(String str, int i, Integer num, Integer num2, Consumer<String> consumer, Consumer<String> consumer2) {
    }

    public void c(String str, Runnable runnable, Consumer<String> consumer) {
        CloudConfigRepository cloudConfigRepositoryJ;
        if (str == null || str.trim().isEmpty() || (cloudConfigRepositoryJ = j()) == null) {
            return;
        }
        String strTrim = str.trim();
        try {
            CloudConfigRepository.RemoteConfigRecord remoteConfigRecord = new CloudConfigRepository.RemoteConfigRecord();
            remoteConfigRecord.b = strTrim;
            remoteConfigRecord.c = "";
            remoteConfigRecord.g = System.currentTimeMillis();
            Field declaredField = CloudConfigRepository.class.getDeclaredField("i");
            declaredField.setAccessible(true);
            ((Map) declaredField.get(cloudConfigRepositoryJ)).put(strTrim, remoteConfigRecord);
        } catch (Throwable th) {
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public List<CloudConfigRepository.SharedConfigRecord> k() {
        return Collections.emptyList();
    }

    public void a(int i, Consumer<ConfigState> consumer, Consumer<String> consumer2) {
    }

    public ConfigState l() {
        return null;
    }

    public void a(ConfigState configState) {
    }

    private Map<String, ConfigState.ModuleState> n() {
        HashMap map = new HashMap();
        for (ClientModule clientModule : ModuleRegistry.all()) {
            ConfigState.ModuleState moduleState = new ConfigState.ModuleState();
            moduleState.a(clientModule.k());
            moduleState.a(clientModule.j());
            moduleState.a(a(clientModule.m()));
            map.put(clientModule.g(), moduleState);
        }
        return map;
    }

    private Map<String, ConfigState.SettingState> a(List<Setting<?>> list) {
        HashMap map = new HashMap();
        for (Setting<?> setting : list) {
            if (!(setting instanceof SettingGroup)) {
                ConfigState.SettingState settingState = new ConfigState.SettingState();
                if (setting instanceof BooleanSetting) {
                    settingState.a("crypt");
                    settingState.a(Boolean.valueOf(((BooleanSetting) setting).a()));
                } else if (setting instanceof SliderSetting) {
                    settingState.a("crypt");
                    settingState.a(Float.valueOf(((SliderSetting) setting).a()));
                } else if (setting instanceof ColorSetting) {
                    ColorSetting colorSetting = (ColorSetting) setting;
                    settingState.a("crypt");
                    settingState.a(Float.valueOf(colorSetting.c()));
                    settingState.b(Float.valueOf(colorSetting.d()));
                    settingState.c(Float.valueOf(colorSetting.e()));
                } else if (setting instanceof KeySetting) {
                    settingState.a("crypt");
                    settingState.a(Integer.valueOf(((KeySetting) setting).a()));
                } else if (setting instanceof ModeSetting) {
                    ModeSetting modeSetting = (ModeSetting) setting;
                    settingState.a("crypt");
                    settingState.a(modeSetting.k());
                    if (modeSetting.c()) {
                        settingState.a(modeSetting.e().stream().mapToInt((v0) -> {
                            return v0.intValue();
                        }).toArray());
                    }
                } else if (setting instanceof TokenSetting) {
                    settingState.a("crypt");
                    settingState.a((Object) ((TokenSetting) setting).a());
                } else if (setting instanceof ItemToggleSetting) {
                    ItemToggleSetting itemToggleSetting = (ItemToggleSetting) setting;
                    settingState.a("crypt");
                    settingState.a(Boolean.valueOf(itemToggleSetting.a()));
                    settingState.a(Integer.valueOf(itemToggleSetting.e()));
                } else {
                    DrunVisual.getLOGGER().warn("crypt", setting.getClass().getSimpleName(), setting.f());
                }
                map.put(setting.f(), settingState);
            }
        }
        return map;
    }

    private List<ConfigState.FriendHistoryState> o() {
        ArrayList arrayList = new ArrayList();
        FriendsPanel friendsPanelV = v();
        if (friendsPanelV != null) {
            for (HistoryEntry historyEntry : friendsPanelV.a()) {
                ConfigState.FriendHistoryState friendHistoryState = new ConfigState.FriendHistoryState();
                friendHistoryState.a(historyEntry.a());
                friendHistoryState.b(historyEntry.b().format(c));
                arrayList.add(friendHistoryState);
            }
        }
        return arrayList;
    }

    private List<ConfigState.NotificationState> p() {
        ArrayList arrayList = new ArrayList();
        for (Notification notification : NotificationStore.a()) {
            if (!notification.j()) {
                ConfigState.NotificationState notificationState = new ConfigState.NotificationState();
                notificationState.a(notification.a());
                notificationState.a(notification.b());
                notificationState.b(notification.c());
                notificationState.c(notification.d());
                notificationState.d(notification.e().getRGB());
                notificationState.b(notification.f().name());
                notificationState.a(notification.j());
                notificationState.a(notification.k());
                notificationState.e(notification.l());
                notificationState.b(notification.n());
                arrayList.add(notificationState);
            }
        }
        return arrayList;
    }

    private ConfigState.MarkerOptionsState q() {
        ConfigState.MarkerOptionsState markerOptionsState = new ConfigState.MarkerOptionsState();
        markerOptionsState.a(MarkerOptions.a());
        markerOptionsState.a(MarkerOptions.b());
        markerOptionsState.b(MarkerOptions.c());
        markerOptionsState.c(MarkerOptions.d());
        markerOptionsState.d(MarkerOptions.e());
        return markerOptionsState;
    }

    private Map<String, ConfigState.HudElementState> r() {
        HashMap map = new HashMap();
        HudElementManager hudElementManagerA = HudElementManager.a();
        if (hudElementManagerA.i() != null) {
            map.put("crypt", a(hudElementManagerA.i()));
        }
        if (hudElementManagerA.j() != null) {
            map.put("crypt", a(hudElementManagerA.j()));
        }
        if (hudElementManagerA.k() != null) {
            map.put("crypt", a(hudElementManagerA.k()));
        }
        if (hudElementManagerA.l() != null) {
            map.put("crypt", a(hudElementManagerA.l()));
        }
        return map;
    }

    private ConfigState.HudElementState a(HudElement hudElement) {
        return new ConfigState.HudElementState(hudElement.t().a(), hudElement.t().b(), hudElement.g());
    }

    private String s() {
        DockPanelController dockPanelControllerJ;
        try {
            if ((DrunVisual.getInstance().getClickGui() instanceof DrunVisualClickGuiScreen) && (dockPanelControllerJ = ((DrunVisualClickGuiScreen) DrunVisual.getInstance().getClickGui()).j()) != null) {
                return dockPanelControllerJ.a().name();
            }
        } catch (Exception e) {

            DrunVisual.getLOGGER().error("crypt", e);
        }
        return "crypt";
    }

    private ConfigState.NotificationSettingsState t() {
        ConfigState.NotificationSettingsState notificationSettingsState = new ConfigState.NotificationSettingsState();
        HudNotificationCenter hudNotificationCenterA = HudNotificationCenter.a();
        if (hudNotificationCenterA != null) {
            HudNotificationSettingsPanel hudNotificationSettingsPanelI = hudNotificationCenterA.i();
            notificationSettingsState.a(hudNotificationSettingsPanelI.h());
            notificationSettingsState.b(hudNotificationSettingsPanelI.i());
            notificationSettingsState.c(hudNotificationSettingsPanelI.j());
            notificationSettingsState.d(hudNotificationSettingsPanelI.k());
            notificationSettingsState.e(hudNotificationSettingsPanelI.l());
        }
        return notificationSettingsState;
    }

    private void a(Map<String, ConfigState.ModuleState> map) {
        if (map != null) {
            for (ClientModule clientModule : ModuleRegistry.all()) {
                ConfigState.ModuleState moduleState = map.get(clientModule.g());
                if (moduleState != null) {
                    clientModule.a(moduleState.b());
                    clientModule.b(moduleState.a());
                    a(clientModule.m(), moduleState.c());
                }
            }
        }
    }

    private void a(List<Setting<?>> list, Map<String, ConfigState.SettingState> map) {
        if (map != null) {
            for (Setting<?> setting : list) {
                ConfigState.SettingState settingState = map.get(setting.f());
                if (settingState != null) {
                    try {
                        if (setting instanceof BooleanSetting) {

                            if ("crypt".equals(settingState.a())) {
                                ((BooleanSetting) setting).a((Boolean) settingState.b());
                            }
                        }
                        if (setting instanceof SliderSetting) {

                            if ("crypt".equals(settingState.a())) {
                                Object objB = settingState.b();
                                if (objB instanceof Number) {
                                    ((SliderSetting) setting).a(((Number) objB).floatValue());
                                }
                            }
                        }
                        if (setting instanceof ColorSetting) {

                            if ("crypt".equals(settingState.a())) {
                                ((ColorSetting) setting).a(settingState.c().floatValue(), settingState.d().floatValue(), settingState.e().floatValue());
                            }
                        }
                        if (setting instanceof KeySetting) {

                            if ("crypt".equals(settingState.a())) {
                                Object objB2 = settingState.b();
                                if (objB2 instanceof Number) {
                                    ((KeySetting) setting).a(((Number) objB2).intValue());
                                }
                            }
                        }
                        if (setting instanceof ModeSetting) {

                            if ("crypt".equals(settingState.a())) {
                                ModeSetting modeSetting = (ModeSetting) setting;
                                Object objB3 = settingState.b();
                                if (objB3 instanceof Number) {
                                    modeSetting.a(Integer.valueOf(((Number) objB3).intValue()));
                                }
                                if (modeSetting.c() && settingState.f() != null) {
                                    HashSet hashSet = new HashSet();
                                    for (int i6 : settingState.f()) {
                                        hashSet.add(Integer.valueOf(i6));
                                    }
                                    modeSetting.a((Set<Integer>) hashSet);
                                }
                            }
                        }
                        if (setting instanceof TokenSetting) {

                            if ("crypt".equals(settingState.a())) {
                                ((TokenSetting) setting).a((String) settingState.b());
                            }
                        }
                        if (setting instanceof ItemToggleSetting) {

                            if ("crypt".equals(settingState.a())) {
                                ItemToggleSetting itemToggleSetting = (ItemToggleSetting) setting;
                                if (settingState.g() != null) {
                                    itemToggleSetting.a(settingState.g());
                                }
                                if (settingState.h() != null) {
                                    itemToggleSetting.a(new Color(settingState.h().intValue()));
                                }
                            }
                        }
                    } catch (Exception e) {

                        DrunVisual.getLOGGER().error("crypt" + setting.f(), e);
                    }
                }
            }
        }
    }

    private void b(List<ConfigState.FriendHistoryState> list) {
        FriendsPanel friendsPanelV;
        if (list == null || (friendsPanelV = v()) == null) {
            return;
        }
        friendsPanelV.b();
        for (ConfigState.FriendHistoryState friendHistoryState : list) {
            try {
                friendsPanelV.a(new HistoryEntry(friendHistoryState.a(), LocalDate.parse(friendHistoryState.b(), c)));
            } catch (Exception e) {
                friendsPanelV.a(new HistoryEntry(friendHistoryState.a()));
            }
        }
    }

    private void c(List<ConfigState.NotificationState> list) {
        if (list != null) {
            NotificationStore.g().forEach(NotificationStore::b);
            for (ConfigState.NotificationState notificationState : list) {
                try {
                    NotificationStore.a(new Notification(notificationState.a(), notificationState.b(), notificationState.c(), notificationState.d(), new Color(notificationState.e()), Notification.Icon.valueOf(notificationState.f())));
                } catch (Exception e) {

                    DrunVisual.getLOGGER().error("crypt" + notificationState.a(), e);
                }
            }
        }
    }

    private void a(ConfigState.MarkerOptionsState markerOptionsState) {
        if (markerOptionsState != null) {
            MarkerOptions.a(markerOptionsState.a());
            MarkerOptions.a(markerOptionsState.b());
            MarkerOptions.b(markerOptionsState.c());
            MarkerOptions.c(markerOptionsState.d());
        }
    }

    private void b(Map<String, ConfigState.HudElementState> map) {
        if (map != null) {
            HudElementManager hudElementManagerA = HudElementManager.a();
            MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
            float fGetFramebufferWidth = (float) (((double) MinecraftClientVarGetInstance.getWindow().getFramebufferWidth()) / 2.0d);
            float fGetFramebufferHeight = (float) (((double) MinecraftClientVarGetInstance.getWindow().getFramebufferHeight()) / 2.0d);
            a(hudElementManagerA.i(), map.get("crypt"), fGetFramebufferWidth, fGetFramebufferHeight);
            a(hudElementManagerA.j(), map.get("crypt"), fGetFramebufferWidth, fGetFramebufferHeight);
            a(hudElementManagerA.k(), map.get("crypt"), fGetFramebufferWidth, fGetFramebufferHeight);
            a(hudElementManagerA.l(), map.get("crypt"), fGetFramebufferWidth, fGetFramebufferHeight);
            hudElementManagerA.d();
        }
    }

    private void a(HudElement hudElement, ConfigState.HudElementState hudElementState, float f, float f2) {
        if (hudElement == null || hudElementState == null) {
            return;
        }
        hudElement.t().a(hudElementState.a());
        hudElement.t().b(hudElementState.b());
        hudElement.b();
        float fA = hudElement.t().a(hudElement.n(), f);
        float fB = hudElement.t().b(hudElement.o(), f2);
        hudElement.a(fA);
        hudElement.b(fB);
    }

    private void b(String str) {
        DockPanelController dockPanelControllerJ;
        if (str != null) {
            try {
                DockPanelController.Dock dockValueOf = DockPanelController.Dock.valueOf(str);
                if ((DrunVisual.getInstance().getClickGui() instanceof DrunVisualClickGuiScreen) && (dockPanelControllerJ = ((DrunVisualClickGuiScreen) DrunVisual.getInstance().getClickGui()).j()) != null) {
                    dockPanelControllerJ.a(dockValueOf);
                }
            } catch (IllegalArgumentException e) {

                DrunVisual.getLOGGER().warn("crypt" + str);
            } catch (Exception e2) {

                DrunVisual.getLOGGER().error("crypt", e2);
            }
        }
    }

    private void a(ConfigState.NotificationSettingsState notificationSettingsState) {
        HudNotificationCenter hudNotificationCenterA;
        if (notificationSettingsState == null || (hudNotificationCenterA = HudNotificationCenter.a()) == null) {
            return;
        }
        HudNotificationSettingsPanel hudNotificationSettingsPanelI = hudNotificationCenterA.i();
        hudNotificationSettingsPanelI.a(notificationSettingsState.a());
        hudNotificationSettingsPanelI.b(notificationSettingsState.b());
        hudNotificationSettingsPanelI.c(notificationSettingsState.c());
        hudNotificationSettingsPanelI.d(notificationSettingsState.d());
        hudNotificationSettingsPanelI.e(notificationSettingsState.e());
    }

    private ConfigState.ClientColorState u() {
        ConfigState.ClientColorState clientColorState = new ConfigState.ClientColorState();
        ClientColor clientColor = ModuleRegistry.CLIENT_COLOR;
        clientColorState.a(clientColor.a.d());
        clientColorState.a(clientColor.b.c());
        clientColorState.b(clientColor.b.d());
        clientColorState.c(clientColor.b.e());
        clientColorState.d(clientColor.e.c());
        clientColorState.e(clientColor.e.d());
        clientColorState.f(clientColor.e.e());
        clientColorState.g(clientColor.f.c());
        clientColorState.h(clientColor.f.d());
        clientColorState.i(clientColor.f.e());
        return clientColorState;
    }

    private void a(ConfigState.ClientColorState clientColorState) {
        if (clientColorState != null) {
            ClientColor clientColor = ModuleRegistry.CLIENT_COLOR;
            String[] strArrA = clientColor.a.a();
            int i = 0;
            while (true) {
                if (i >= strArrA.length) {
                    break;
                }
                if (strArrA[i].equals(clientColorState.a())) {
                    clientColor.a.a(Integer.valueOf(i));
                    break;
                }
                i++;
            }
            clientColor.b.a(clientColorState.b(), clientColorState.c(), clientColorState.d());
            clientColor.e.a(clientColorState.e(), clientColorState.f(), clientColorState.g());
            clientColor.f.a(clientColorState.h(), clientColorState.i(), clientColorState.j());
        }
    }

    private FriendsPanel v() {
        FriendsTab friendsTabH;
        try {
            if (!(DrunVisual.getInstance().getClickGui() instanceof DrunVisualClickGuiScreen) || (friendsTabH = ((DrunVisualClickGuiScreen) DrunVisual.getInstance().getClickGui()).h()) == null) {
                return null;
            }
            return friendsTabH.e();
        } catch (Exception e) {

            DrunVisual.getLOGGER().error("crypt", e);
            return null;
        }
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
