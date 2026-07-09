package drunvisual.cosmetic;

import ru.drunvisual.DrunVisual;
import drunvisual.model.BedrockModelParser;
import drunvisual.model.ModelDefinition;

public class CosmeticModelLoader {
    public static int a;

    public ModelDefinition a(CosmeticModel cosmeticModel) {
        try {
            String strD = cosmeticModel.d();
            if (strD == null) {

                DrunVisual.getLOGGER().error("crypt" + cosmeticModel.a());
                return null;
            }
            ModelDefinition modelDefinitionA = BedrockModelParser.a(strD);
            if (modelDefinitionA == null) {

                DrunVisual.getLOGGER().error("crypt" + cosmeticModel.a());
                return null;
            }
            DrunVisual.getLOGGER().info("crypt" + cosmeticModel.a() + "crypt" + modelDefinitionA.a.size() + "crypt");
            return modelDefinitionA;
        } catch (Exception e) {

            DrunVisual.getLOGGER().error("crypt" + cosmeticModel.a(), e);
            return null;
        }
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
