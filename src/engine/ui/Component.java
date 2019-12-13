package engine.ui;

import engine.materials.UIMaterial;
import engine.model.Model;
import engine.model.ModelBuilder;
import engine.render.IRenderable;
import engine.render.Material;
import engine.render.Renderer;
import engine.render.Transform;
import engine.texture.Texture;
import engine.tools.RoffColor;
import org.joml.Vector2fc;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.text.NumberFormat;

public class Component implements IRenderable, ParentResizeListener {

    private int width, height, minWidth = 0, minHeight = 0, maxWidth = 0, maxHeight = 0;
    private boolean hasMaxWidth = false,hasMaxHeight = false;
    private Component parent;
    private UIMaterial material = new UIMaterial();
    private UITransform transform = new UITransform(0,0,0);
    private static Model model = ModelBuilder.createQuad();
    private ParentResizeListener onResizeListener;
    private Unit widthUnit = Unit.PIXEL , heightUnit = Unit.PIXEL, paddingUnit = Unit.PIXEL, marginUnit = Unit.PIXEL;
    private Vector4f margin = new Vector4f(0,0,0,0);
    private Origin origin = Origin.TOP_LEFT, pivot = Origin.TOP_LEFT;
    public Component() {
    }

    public Component(Scale scale) {
        if(scale == Scale.SCALE_TO_FIT) {
            this.widthUnit = Unit.PERCENT;
            this.heightUnit = Unit.PERCENT;
            this.width = 100;
            this.height = 100;
        } else {
            widthUnit = Unit.PIXEL;
            heightUnit = Unit.PIXEL;
        }
    }

    public void render(Renderer renderer) {
        renderer.render(this);

    }

    public void setPadding(int padding, Unit unit) {
        setPadding(padding,padding,padding,padding,unit);
    }

    public void setPadding(int px,int py, Unit unit) {
        setPadding(px,py,px,py,unit);
    }

    public void setPadding(int left,int top,int right,int bottom, Unit unit) {
        transform.setPadding(left,top,right,bottom);
        this.paddingUnit = unit;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
        if(parent != null)
            transform.setOrigin(origin,getWidth(),getHeight());
    }

    public void setPivot(Origin pivot) {
        this.pivot = pivot;
        if(parent != null)
            transform.setPivot(pivot,parent.getWidth(),parent.getHeight());
    }

    public void setMargin(int margin, Unit unit) {
        setMargin(margin,margin,margin,margin,unit);
    }

    public void setMargin(int px,int py, Unit unit) {
        setMargin(px,py,px,py,unit);
    }

    public void setMargin(int left,int top,int right,int bottom, Unit unit) {
        this.marginUnit = unit;
        this.margin.set(left,top,right,bottom);
        setRealMargin(margin);
        onParentComponentResize();
    }


    protected void setParent(Component parent) {
        parent.onResize(this);
        transform.setParent(parent.transform);
        this.parent = parent;
        this.onParentComponentResize();
    }

    public int getWidth() {
        if(widthUnit == Unit.PERCENT && parent != null) {
            int w= (int) (norm(width)*parent.getWidth());
            if(hasMaxWidth)
                w = Math.min(maxWidth,w);
            w = Math.max(minWidth,w);
            return w;
        }
        return width;
    }

    public int getHeight() {
        if(heightUnit == Unit.PERCENT && parent != null) {

            int h = (int) (norm(height)*parent.getHeight());
            if(hasMaxHeight)
                h = Math.min(maxHeight,h);
            h = Math.max(minHeight,h);
            return h;
        }
        return height;
    }

    protected void onParentResize() {
        onResizeListener.onParentComponentResize();
    }

    public void onResize(ParentResizeListener listener) {
        this.onResizeListener = listener;
    }

    public void onParentComponentResize() {

        if(parent == null) return;
        if(marginUnit == Unit.PERCENT) {
            setRealMargin(margin);
        }

        if(widthUnit == Unit.PERCENT) {
            setRealWidth(width);
        }

        if(heightUnit == Unit.PERCENT) {
            setRealHeight(height);
        }


        if(onResizeListener != null) {
            onParentResize();
        }
        transform.setPivot(pivot,parent.getWidth(),parent.getHeight());
        transform.setOrigin(origin,getWidth(),getHeight());

    }

    private Vector2i offset = new Vector2i(0,0);

    public void setWidth(int width) {
        setWidth(width,widthUnit);
    }

    public void setHeight(int height) {
        setHeight(height,heightUnit);
    }

    private float norm(int scale) {
        return (scale*1f)/100f;
    }

    public void setWidth(int width,Unit unit) {
        this.width = width;
        this.widthUnit = unit;
        setRealWidth(width);

    }

    public void setMinDimension(int minWidth,int minHeight) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        hasMaxHeight = true;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        hasMaxWidth = true;
    }

    private void setRealHeight(int height) {
        int h;
        Vector3f scale = transform.getScale();
        if(heightUnit == Unit.PERCENT && parent != null) {
            int parentHeight = parent.getHeight();
            h = (int) (norm(height)*parentHeight);
            if(h+transform.getPosition().y >= parentHeight) {
                h -= transform.getMargin().w + transform.getMargin().y;
            }
            h = Math.max(minHeight,h);
        }else {
            h = height;
        }

        if(hasMaxHeight)
            h = Math.min(maxHeight,h);

        scaleHeight(h);
    }

    private void setRealWidth(int width) {
        int w;
        if(widthUnit == Unit.PERCENT && parent != null) {
            int parentWidth = parent.getWidth();
            w = (int) (norm(width)*parentWidth);
            if(w+transform.getPosition().x >= parentWidth) {
                w -= (int) transform.getMargin().z+(int) transform.getMargin().x;
            }
            w = Math.max(minWidth,w);
            if(hasMaxWidth)
                w = Math.min(maxWidth,w);
        }else {
            w = width;
        }


        //if(parent != null)
        //  System.out.println("width: " + w + " : " + this.parent.getWidth() + " | " + norm(width) + " | " + this.width);
        scaleWidth(w);
    }

    protected void scaleWidth(int w) {
        Vector3f scale = transform.getScale();
        transform.setScale(w, scale.y, 1);
    }

    protected void scaleHeight(int h) {
        Vector3f scale = transform.getScale();
        transform.setScale(scale.x, h, 1);
    }

    private void setRealMargin(Vector4f margin) {
        if(this.marginUnit == Unit.PERCENT && parent != null) {
            int parentHeight = parent.getHeight();
            int parentWidth = parent.getWidth();
            this.transform.setMargin(   (int)(norm((int) margin.x)*parentWidth),
                                        (int)(norm((int) margin.y)*parentHeight),
                                        (int)(norm((int) margin.z)*parentWidth),
                                        (int)(norm((int) margin.w)*parentHeight)
            );
        } else {
            this.transform.setMargin((int)margin.x,(int)margin.y,(int)margin.z,(int)margin.w);
        }
    }

    public void setHeight(int height, Unit unit) {
        this.height = height;
        this.heightUnit = unit;
        setRealHeight(height);

    }

    public void setOpacity(float alpha) {
        this.material.setAlpha((int)(alpha*256));
    }

    public void setBackgroundColor(RoffColor color) {
        this.material.setColor(color);
    }

    public void setBackgroundImage(Texture texture) {
        this.material.setAlbedoTexture(texture);
    }

    protected Component getParent() {
        return parent;
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    public void setDimension(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setPosition(int x, int y) {
        this.transform.setPosition(x,y,0);
    }

}
