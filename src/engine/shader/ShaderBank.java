package engine.shader;

import java.util.HashMap;

public class ShaderBank {
    private static HashMap<String, Shader> materials = new HashMap<>();
    public static void addShader(String name, Shader material){
        if(materials.containsKey(name))
            throw new IllegalStateException("Shader with name \""+name + "\" already exist!");

        materials.put(name,material);
    }

    public static <T extends Shader>  T getShader(String name, Class<T> mat){
        return mat.cast(materials.get(name));
    }

    public static Shader getShader(String name){
        return materials.get(name);
    }

    static {
        addShader("albedo", new AlbedoShader());
    }
}
