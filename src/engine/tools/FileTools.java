package engine.tools;

import engine.Engine;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class FileTools {
    private static HashMap<String, String> readFiles = new HashMap<String, String>();
    public static String readFile(String src) {
        if(readFiles.containsKey(src))
            return readFiles.get(src);
        InputStream stream;
        try {
            stream = new FileInputStream(new File(FileTools.class.getResource(src).toURI()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            String content = builder.toString();
            readFiles.put(src,content);
            return content;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static GeneratedTexture generateImage(BufferedImage image) {
        GeneratedBuffer buffer = getBuffer(image,0,0,1,1);
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer.buffer);
        GeneratedTexture generatedTexture = new GeneratedTexture();
        generatedTexture.textureID = textureID;
        generatedTexture.width = image.getWidth();
        generatedTexture.height = image.getHeight();
        generatedTexture.image = image;
        return generatedTexture;
    }

    public static GeneratedTexture getImage(BufferedImage image) {
        return generateImage(image);
    }

    public static class GeneratedTexture {
        public int textureID, width, height;
        public BufferedImage image;
    }
    public static class GeneratedBuffer {
        public int width, height;
        public ByteBuffer buffer;
    }
    public static GeneratedBuffer getBuffer(BufferedImage image, float ox, float oy, float width, float height){
        int[] pixels = new int[(int)((image.getWidth()*width) * (image.getHeight()*height))];
        image.getRGB((int)(image.getWidth()*ox), (int)(image.getHeight()*oy), (int)(image.getWidth()*width), (int)(image.getHeight()*height), pixels, 0, (int)(image.getWidth()*width));
        ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length * 4);
        for (int i = 0; i < pixels.length; i++) {
            buffer.put((byte) ((pixels[i] >> 16) & 0xFF));
            buffer.put((byte) ((pixels[i] >> 8) & 0xFF));
            buffer.put((byte) (pixels[i] & 0xFF));
            buffer.put((byte) ((pixels[i] >> 24) & 0xFF));
        }
        buffer.flip();
        GeneratedBuffer b = new GeneratedBuffer();
        b.width = (int)(image.getWidth()*width);
        b.height = (int)(image.getHeight()*height);
        b.buffer = buffer;
        return b;
    }
    public static GeneratedBuffer getBuffer(String path){
        return getBuffer(path,0,0,1,1);
    }
    public static GeneratedBuffer getBuffer(String path, float ox, float oy, float width, float height){
        BufferedImage image;
        try {
            image = ImageIO.read(FileTools.class.getResourceAsStream(path));
            return getBuffer(image,ox,oy,width,height);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public static GeneratedTexture getImage(String path) {
        BufferedImage image;
        try {
            image = ImageIO.read(FileTools.class.getResourceAsStream(path));
            return generateImage(image);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
