package engine.ui;

import engine.render.Transform;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.text.NumberFormat;

public class UITransform extends Transform<UITransform> {

    private Vector4f margin = new Vector4f(0,0,0,0);
    private Vector4f padding = new Vector4f(0,0,0,0);
    private Vector2i pivot = new Vector2i(0,0);
    private Vector2i origin = new Vector2i(0,0);

    public UITransform(float x, float y, float z) {
        super(x, y, z);
    }


    public void setPadding(int left,int top,int right,int bottom) {
        padding.set(left,top,right,bottom);
    }

    public void setMargin(int left,int top,int right,int bottom) {
        margin.set(left,top,right,bottom);
    }

    private static Vector2i offset = new Vector2i();

    public void setPivot(Origin pivot,int width,int height) {
        if(parent == null) return;
        float xOff = pivot.getX();
        float yOff = pivot.getY();

        offset.set((int)(width *-xOff),(int)(height*-yOff));
        /*
        switch (pivot) {
            case TOP_LEFT:
                offset.set(0,0);
                break;
            case CENTER:
                offset.set(width/2,height/2);
                break;
            case RIGHT_TOP:
                offset.set(width,0);
                break;
            case CENTER_TOP:
                offset.set(width/2,0);
                break;
            case BOTTOM_LEFT:
                offset.set(0,height);
                break;
            case CENTER_LEFT:
                offset.set(0,height/2);
                break;
            case BOTTOM_RIGHT:
                offset.set(width,height);
                break;
            case CENTER_RIGHT:
                offset.set(width,height/2);
                break;
            case BOTTOM_CENTER:
                offset.set(width/2,height);
                break;
        }

         */
        this.pivot.set(offset);
    }

    public void setOrigin(Origin pivot,int width,int height) {
        if(parent == null) return;

        float xOff = pivot.getX();
        float yOff = pivot.getY();

        offset.set((int)(width *xOff),(int)(height*yOff));
        /*
        switch (pivot) {
            case TOP_LEFT:
                offset.set(0,0);
                break;
            case CENTER:
                offset.set(-width/2,-height/2);
                break;
            case RIGHT_TOP:
                offset.set(-width,0);
                break;
            case CENTER_TOP:
                offset.set(-width/2,0);
                break;
            case BOTTOM_LEFT:
                offset.set(0,-height);
                break;
            case CENTER_LEFT:
                offset.set(0,-height/2);
                break;
            case BOTTOM_RIGHT:
                offset.set(-width,-height);
                break;
            case CENTER_RIGHT:
                offset.set(-width,-height/2);
                break;
            case BOTTOM_CENTER:
                offset.set(-width/2,-height);
                break;
        }
         */
        this.origin.set(offset);
    }

    @Override
    public Vector3d getPosition() {
        Vector3d pos = super.getPosition().add(margin.x,margin.y,0).add(origin.x,origin.y,0).add(pivot.x,pivot.y,0);
        if(parent != null) {
            return pos.add(parent.padding.x,parent.padding.y,0);
        }
        return pos;
    }

    private static Vector3d scaleRet = new Vector3d(0,0,0);

    @Override
    public Vector3d getScale() {
        Vector3d scale = scaleRet.set(super.getScale());
        if(parent != null) {
            return scale.add(-parent.padding.z,-parent.padding.w,0);
        }
        return scale;
    }

    public Vector4f getMargin() {
        return margin;
    }

    public Vector4f getPadding() {
        return padding;
    }
}
