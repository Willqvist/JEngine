package engine.ui;

import engine.texture.Texture;
import engine.tools.RoffColor;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.*;
import java.util.ArrayList;

public class Component {

    private int width, height, minWidth = 0, minHeight = 0, maxWidth = 0, maxHeight = 0;

    private boolean hasMaxWidth = false,hasMaxHeight = false;
    private Component parent;
    private UITransform transform = new UITransform(0,0,0);
    private ParentResizeListener onResizeListener;
    private Unit widthUnit, heightUnit, paddingUnit = Unit.PIXEL, marginUnit = Unit.PIXEL;
    private Vector4f margin = new Vector4f(0,0,0,0);
    private Origin origin = Origin.TOP_LEFT, pivot = Origin.TOP_LEFT;

    private float opacity;
    private RoffColor color = RoffColor.from(Color.WHITE);
    private Texture texture;

    private ArrayList<Component> components = new ArrayList<>();

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

    public void render(GUIBatch guiBatch) {

        Vector3d pos = transform.getPosition();
        Vector3d scale = transform.getScale();
        guiBatch.renderTexture((int) pos.x, (int) pos.y,(int) scale.x,(int) scale.y , texture,color);

        for(Component component : components) {
            //renderer.render(component);
            component.render(guiBatch);
        }
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
        //parent.onResize(this);
        parent.components.add(this);
        transform.setParent(parent.transform);
        this.parent = parent;
        this.revalidate();
    }

    public int getWidth() {
        if(widthUnit == Unit.PERCENT && parent != null) {
            int w= (int) (norm(width)*parent.getWidth());
            if(hasMaxWidth)
                w = Math.min(maxWidth,w);
            w = Math.max(minWidth,w);
            return w;
        } else if(widthUnit == Unit.HEIGHT_RATIO) {
            return (int) transform.getScale().y;
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
        } else if(heightUnit == Unit.WIDTH_RATIO) {
            return (int) transform.getScale().x;
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

        if(widthUnit == Unit.PERCENT || widthUnit == Unit.HEIGHT_RATIO) {
            setRealWidth(width);
        }

        if(heightUnit == Unit.PERCENT || heightUnit == Unit.WIDTH_RATIO) {
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

    public void setMaxDimension(int maxWidth,int maxHeight) {
        setMaxHeight(maxHeight);
        setMaxWidth(maxWidth);
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
        Vector3d scale = transform.getScale();
        if(heightUnit == Unit.PERCENT && parent != null) {
            int parentHeight = parent.getHeight();
            h = (int) (norm(height)*parentHeight);
            if(h+transform.getPosition().y >= parentHeight) {
                h -= transform.getMargin().w + transform.getMargin().y;
            }
            h = Math.max(minHeight,h);
        } else if(heightUnit == Unit.WIDTH_RATIO) {
            h = (int) transform.getScale().x;
            if(widthUnit == Unit.HEIGHT_RATIO) {
                setWidth(width,widthUnit);
            }
        } else {
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
        } else if(widthUnit == Unit.HEIGHT_RATIO) {
            w = (int) transform.getScale().y;
            if(heightUnit == Unit.WIDTH_RATIO) {
                setHeight(height,heightUnit);
            }
        } else {
            w = width;
        }


        //if(parent != null)
        //  System.out.println("width: " + w + " : " + this.parent.getWidth() + " | " + norm(width) + " | " + this.width);
        scaleWidth(w);
    }

    protected void scaleWidth(int w) {
        Vector3d scale = transform.getScale();
        transform.setScale(w, scale.y, 1);
    }

    protected void scaleHeight(int h) {
        Vector3d scale = transform.getScale();
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
        opacity = (int)(alpha*256);
        color.setAlpha((int)opacity);
    }

    public void setBackgroundColor(RoffColor color) {
        this.color = color;
    }

    public void setBackgroundImage(Texture texture) {
        this.texture = texture;
    }

    protected Component getParent() {
        return parent;
    }

    public void setDimension(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void add(Component component) {
        component.setParent(this);
    }

    public void setPosition(int x, int y) {
        this.transform.setPosition(x,y,0);
    }

    public void revalidate() {
        if(parent != null) {
            onParentComponentResize();
        }
        for(Component component : components) {
            component.revalidate();
        }
    }
}
