package ru.drunvisual.mixin;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.text.Style;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.PlainTextContent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import drunvisual.modules.utilities.StreamerMode;
import drunvisual.client.MinecraftContext;
import drunvisual.module.ModuleRegistry;
import ru.drunvisual.DrunVisual;
import drunvisual.gui.config.ConfigTextDialog;

@Mixin({PlainTextContent.Literal.class})
public class TextMixin implements MinecraftContext {

    @Shadow
    @Final
    private String string;

    @Unique
    private static final Pattern holyWorldPattern = Pattern.compile("(СолоЛайт|ДуоЛайт|ТриоЛайт|КланЛайт)\\s*#(\\d{1,2})");

    @Unique
    private static final Pattern funTimePattern = Pattern.compile("Анархия-(\\d+)");

    @Redirect(method = {"visit(Lnet/minecraft/text/StringVisitable$Visitor;)Ljava/util/Optional;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/text/StringVisitable$Visitor;accept(Ljava/lang/String;)Ljava/util/Optional;"))
    private <ConfigTextDialog> Optional<ConfigTextDialog> redirectVisitor(StringVisitable.Visitor<ConfigTextDialog> class_5245Var, String str) {
        return class_5245Var.accept(getProcessedText(str));
    }

    @Redirect(method = {"visit(Lnet/minecraft/text/StringVisitable$StyledVisitor;Lnet/minecraft/text/Style;)Ljava/util/Optional;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/text/StringVisitable$StyledVisitor;accept(Lnet/minecraft/text/Style;Ljava/lang/String;)Ljava/util/Optional;"))
    private <ConfigTextDialog> Optional<ConfigTextDialog> redirectStyledVisitor(StringVisitable.StyledVisitor<ConfigTextDialog> class_5246Var, Style StyleVar, String str) {
        return class_5246Var.accept(StyleVar, getProcessedText(str));
    }

    @Unique
    private String getProcessedText(String str) {
        if (str == null) {
            return str;
        }
        try {
            String strHideServerNumber = str;
            if (shouldReplaceNick() && c.getSession() != null) {
                String username = c.getSession().getUsername();
                if (username != null && !username.isEmpty()) {
                    strHideServerNumber = strHideServerNumber.replace(username, DrunVisual.CLIENT_NAME);
                }
            }
            if (shouldHideServerNumber()) {
                strHideServerNumber = hideServerNumber(strHideServerNumber);
            }
            return strHideServerNumber;
        } catch (Throwable t) {
            return str;
        }
    }

    @Unique
    private boolean shouldReplaceNick() {
        try {
            StreamerMode streamerMode = ModuleRegistry.STREAMER_MODE;
            if (streamerMode != null && streamerMode.k() && streamerMode.hidePlayerName.k().booleanValue() && c != null) {
                if (c.getSession() != null) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Unique
    private boolean shouldHideServerNumber() {
        try {
            StreamerMode streamerMode = ModuleRegistry.STREAMER_MODE;
            if (streamerMode == null || !streamerMode.k() || !streamerMode.hideServerNumber.k().booleanValue() || c == null || c.getCurrentServerEntry() == null) {
                return false;
            }
            String lowerCase = c.getCurrentServerEntry().address.toLowerCase();
            if (!lowerCase.contains("holyworld")) {
                if (!lowerCase.contains("funtime")) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Unique
    private String hideServerNumber(String str) {
        if (str == null) {
            return str;
        }
        Matcher matcher = holyWorldPattern.matcher(str);
        if (matcher.find()) {
            return matcher.replaceAll("DrunVisuals");
        }
        Matcher matcher2 = funTimePattern.matcher(str);
        return matcher2.find() ? matcher2.replaceAll("DrunVisuals") : str;
    }
}
