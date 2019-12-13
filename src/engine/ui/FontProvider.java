package engine.ui;

import engine.ui.font.Hiero;

import java.util.HashMap;

public class FontProvider {

    private HashMap<String, Hiero> fonts = new HashMap<>();

    private static FontProvider provider;

    public static FontProvider getProvider() {
        if(provider == null)
            provider = new FontProvider();
        return provider;
    }

    public Hiero getFont(String name) {
        return fonts.get(name);
    }

    public void addFont(String name, Hiero font) {
        fonts.put(name,font);
    }

}
