package drunvisual.gui.friends;

import ru.drunvisual.DrunVisual;
import drunvisual.gui.core.DrunVisualClickGuiScreen;

public final class FriendLookup {
    private FriendLookup() {
    }

    public static boolean isFriendName(String str) {
        FriendsTab friendsTabH;
        if ((DrunVisual.getInstance().getClickGui() instanceof DrunVisualClickGuiScreen) && (friendsTabH = ((DrunVisualClickGuiScreen) DrunVisual.getInstance().getClickGui()).h()) != null) {
            return friendsTabH.e().a().stream().anyMatch(historyEntry -> {
                return historyEntry.a().equalsIgnoreCase(str);
            });
        }
        return false;
    }

    public static boolean a(String str) {
        return isFriendName(str);
    }
}
