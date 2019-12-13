package engine.ui;

import engine.model.Model;
import engine.ui.font.Hiero;

public class Text extends Component{

    private Model model;
    private Hiero font;
    private int width = 20;
    private String text = "";
    public Text(String text, String font) {
        super(Scale.NONE);
        this.font = FontProvider.getProvider().getFont(font);
        setWidth(1);
        setHeight(1);
        this.setBackgroundImage(this.font.getFontTexture());
        model = this.font.toModel(text,1,80);
        this.text = text;
    }

    @Override
    protected void scaleWidth(int w) {
        if(this.model != null)
            this.model.dispose();
        model = this.font.toModel(text,1,w);
    }

    @Override
    protected void scaleHeight(int h) {
        super.scaleHeight(h);
    }

    @Override
    public Model getModel() {
        return model;
    }
}
