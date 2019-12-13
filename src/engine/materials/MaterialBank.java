package engine.materials;

import engine.render.Material;

import java.util.HashMap;

public class MaterialBank {
    private static HashMap<String, Material> materials = new HashMap<>();
    public static void addMaterial(String name, Material material){
        if(materials.containsKey(name))
            throw new IllegalStateException("Material with name \""+name + "\" already exist!");

        materials.put(name,material);
    }

    public static <T extends Material>  T getMaterial(String name, Class<T> mat){
        return mat.cast(materials.get(name));
    }

    public static Material getMaterial(String name){
        return materials.get(name);
    }

    static {
        addMaterial("standard", new StandardMaterial());
    }

}
