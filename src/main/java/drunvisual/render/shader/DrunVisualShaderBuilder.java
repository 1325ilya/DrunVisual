package drunvisual.render.shader;

import java.io.InputStream;
import ru.drunvisual.DrunVisual;
import drunvisual.util.ResourceTextReader;

public class DrunVisualShaderBuilder {
    private final DrunVisualShaderProgram program = new DrunVisualShaderProgram();

    public enum Stage {
        VERTEX(35633),
        FRAGMENT(35632);

        private final int glType;

        public int glType() {
            return this.glType;
        }

        Stage(int i) {
            this.glType = i;
        }
    }

    private DrunVisualShaderBuilder() {
    }

    public static DrunVisualShaderBuilder create() {
        return new DrunVisualShaderBuilder();
    }

    public DrunVisualShaderBuilder attach(String str, Stage stage) {
        InputStream resourceAsStream = getClass().getResourceAsStream("/assets/drunvisual/shaders/".concat(str));
        if (resourceAsStream == null) {
            DrunVisual.getLOGGER().error("Shader file not found: /assets/drunvisual/shaders/{}", str);
            return this;
        }
        this.program.a(ResourceTextReader.a(resourceAsStream), stage.glType());
        return this;
    }

    public DrunVisualShaderBuilder link() {
        this.program.c();
        return this;
    }

    public DrunVisualShaderProgram build() {
        return this.program;
    }
}
