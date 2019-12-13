package engine.ui.font;

import engine.texture.Texture;
import engine.texture.TextureLoader;

import java.io.*;

public class HieroFontParser {

    private static HieroFontParser parser;

    public static HieroFontParser getInstance() {
        if(parser == null)
            parser = new HieroFontParser();

        return parser;
    }


    public Hiero parse(String font) {
        if(!font.endsWith("fnt")) return null;
        InputStream stream = HieroFontParser.class.getResourceAsStream(font);
        if(stream == null) {
            System.err.println(font + " is null");
            return null;
        }
        BufferedReader reader = null;
        HieroBuilder builder = new HieroBuilder();
        reader = new BufferedReader(new InputStreamReader(stream));
        try {
            String line;
            int i = 0;

            while((line = reader.readLine()) != null) {
                parseLine(builder,i,line);
                i++;
            }
            return builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int getValue(String var, String line) {
        int index = line.indexOf(var);
        if(index < 0) return -1;
        int spaceIndex = index;
        while(spaceIndex < line.length() && line.charAt(spaceIndex) != ' '){
            spaceIndex ++;
        }

        return Integer.parseInt(line.substring(index+var.length()+1,spaceIndex));
    }

    private String getStringValue(String var, String line) {
        int index = line.indexOf(var);
        int spaceIndex = index;
        while(spaceIndex < line.length() && line.charAt(spaceIndex) != ' '){
            spaceIndex ++;
        }
        return line.substring(index+var.length()+2,spaceIndex-1);
    }

    private void parseLine(HieroBuilder builder,int lineIndex,String line) {
        if(line.startsWith("common")) {
            builder.setLineHeight(getValue("lineHeight",line));
            builder.setBase(getValue("base",line));
            builder.setScaleW(getValue("scaleW",line));
            builder.setScaleH(getValue("scaleH",line));
            builder.setPages(getValue("pages",line));
            builder.setPacked(getValue("packed",line));
            return;
        }
        if(line.startsWith("page")) {
            String val = getStringValue("file",line);
            Texture texture = TextureLoader.load(val);
            builder.setFontTexture(texture);
            return;
        }

        if(line.startsWith("char ")) {
            HieroCharacterBuilder characterBuilder = new HieroCharacterBuilder();
            int id = getValue("id",line);
            characterBuilder.setId(id);
            characterBuilder.setX(getValue("x",line));
            characterBuilder.setY(getValue("y",line));
            characterBuilder.setWidth(getValue("width",line));
            characterBuilder.setHeight(getValue("height",line));
            characterBuilder.setxOffset(getValue("xoffset",line));
            characterBuilder.setyOffset(getValue("yoffset",line));
            characterBuilder.setxAdvance(getValue("xadvance",line));
            characterBuilder.setPage(getValue("page",line));
            characterBuilder.setChnl(getValue("chnl",line));
            builder.addCharacter(id,characterBuilder.build());
        }
    }

}
