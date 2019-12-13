package engine.tools;

public class Const {
    public static String SHADER_SRC = "/res/shaders/";
    public static String DEFFERED_SHADER_SRC = "/res/deffered_shaders/";
    public static String IMAGES_SRC = "/res/images/";
    public static String CUBEMAP_SRC = IMAGES_SRC + "cubemaps/";

    public static String SHADER_SRC_RENDERER() {
        return /*Core.renderer == RenderType.DEFERED ? DEFFERED_SHADER_SRC :*/ SHADER_SRC;
    }
}