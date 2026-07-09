package drunvisual.render.shader;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;

public class ShaderRegistry {
    private final Map<String, DrunVisualShaderProgram> programs = Maps.newLinkedHashMap();

    public void register(String str, DrunVisualShaderProgram pulseShaderProgram) {
        this.programs.put(str, pulseShaderProgram);
    }

    public void replace(String str, DrunVisualShaderProgram pulseShaderProgram) {
        this.programs.replace(str, pulseShaderProgram);
    }

    public Optional<DrunVisualShaderProgram> find(String str) {
        return Optional.ofNullable(this.programs.get(str));
    }
}
