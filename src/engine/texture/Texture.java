package engine.texture;

import engine.tools.Const;
import engine.tools.FileTools;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;

public class Texture {
    protected int texture = -1;
    private int width, height;
    private int fullWidth, fullHeight;
    private int x = 0, y = 0;
    private static int[] lastBoundTextures = new int[GL13.GL_TEXTURE31 - GL13.GL_TEXTURE0];

    /**
     * @param image image to transform to a lwjgl texture id.
     */
    public Texture(BufferedImage image) {
        this(FileTools.getImage(image).textureID, 0, 0, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight());
    }

    /**
     * @param src source to a image.
     */
    public Texture(String src) {
        final FileTools.GeneratedTexture tex = FileTools.getImage(Const.IMAGES_SRC + src);
        init(tex.textureID, 0, 0, tex.width, tex.height, tex.width, tex.height);
    }

    /**
     * @param image  image to save
     * @param x      x offset in image
     * @param y      y offset in image
     * @param width  width of image
     * @param height height of image
     */
    public Texture(BufferedImage image, int x, int y, int width, int height) {
        init(FileTools.getImage(image).textureID, x, y, width, height, width, height);
    }

    /**
     * @param image  image to save
     * @param width  width of image
     * @param height height of image
     */
    public Texture(BufferedImage image, int width, int height) {
        init(FileTools.getImage(image).textureID, 0, 0, width, height, width, height);
    }

    /**
     * @param textureID texture id of a image
     * @param width     width of image
     * @param height    height of image
     */
    public Texture(int textureID, int width, int height) {
        init(textureID, 0, 0, width, height, width, height);
    }

    /**
     * @param textureID  texture id of image
     * @param width      width of image
     * @param height     height of image
     * @param fullWidth  full width of the texture, for calculation of uvs
     * @param fullHeight full height of texture, for calculation of uvs.
     */
    public Texture(int textureID, int width, int height, int fullWidth, int fullHeight) {
        init(textureID, 0, 0, width, height, fullWidth, fullHeight);
    }

    /**
     * @param textureID  texture id of image
     * @param x          x offset in image
     * @param y          y offset in image
     * @param width      width of image
     * @param height     height of image
     * @param fullWidth  full width of the texture, for calculation of uvs
     * @param fullHeight full height of texture, for calculation of uvs.
     */
    public Texture(int textureID, int x, int y, int width, int height, int fullWidth, int fullHeight) {
        init(textureID, x, y, width, height, fullWidth, fullHeight);
    }

    protected Texture() {

    }

    public static void unbind(int tpos) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0+tpos);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
        lastBoundTextures[tpos] = -1;
    }

    public void clone(Texture texture){
        texture.texture = this.texture;
        texture.x = x;
        texture.y = y;
        texture.width = width;
        texture.height = height;
        texture.fullHeight = fullHeight;
        texture.fullWidth = fullWidth;
    }

    private void init(int textureID, int x, int y, int width, int height, int fullWidth, int fullHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = textureID;
        this.fullHeight = fullHeight;
        this.fullWidth = fullWidth;
    }

    public int getWidth() {
        return width;
    }

    public int getFullWidth() {
        return fullWidth;
    }

    public int getFullHeight() {
        return fullHeight;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getTextureId() {
        return this.texture;
    }

    public Texture getSubTexture(int x, int y, int width, int height) {
        return new Texture(texture, x, y, width, height, this.width, this.height);
    }

    public Texture getSubTexture(int x, int y) {
        return new Texture(texture, x, y, this.width, this.height, this.width, this.height);
    }

    public Texture getSubTexture(int scale, int x, int y) {
        return new Texture(texture, x * scale, y * scale, scale, scale, this.width, this.height);
    }

    public static void bind(int texture, int tpos) {
        bind(texture, tpos, GL11.GL_TEXTURE_2D);
    }

    public static void bind(int texture, int tpos, int textureType) {
        if (lastBoundTextures[tpos-GL13.GL_TEXTURE0] == texture) return;
        GL13.glActiveTexture(tpos);
        lastBoundTextures[tpos-GL13.GL_TEXTURE0] = texture;
        GL11.glBindTexture(textureType, texture);
    }

    public void bind() {
        bind(this.texture, GL13.GL_TEXTURE0);
    }

    public void bind(int tpos) {
        bind(this.texture,GL13.GL_TEXTURE0 + tpos);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + texture;
        result = prime * result + width;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Texture other = (Texture) obj;
        if (height != other.height)
            return false;
        if (texture != other.texture)
            return false;
        if (width != other.width)
            return false;
        return true;
    }

    public boolean isSameTexture(Texture texture) {
        if(texture == null) return false;
        return getTextureId() == texture.getTextureId();
    }

    public static Texture createTexture(int width, int height, float[] data) {
        int texture = GL11.glGenTextures();
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_RGB32F, width, height, 0, GL11.GL_RGB, GL11.GL_FLOAT, buffer);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        return new Texture(texture, width, height);
    }
}