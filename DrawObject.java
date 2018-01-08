import java.util.*;
import java.awt.*;

public abstract class DrawObject {
    protected Transform transform = new Transform();
    
    public void setPosition(float dx, float dy, float dz) {
        this.transform.position = new Vector(dx, dy, dz);
    }

    public void setRotationX(float dx) {
        this.transform.localRotation = Matrix4.getRotationX(dx);
    }

    public void setRotationY(float dy) {
        this.transform.localRotation = Matrix4.getRotationY(dy);
    }

    public void setRotationZ(float dz) {
        this.transform.localRotation = Matrix4.getRotationZ(dz);
    }
    
    public void setRotation(Vector rot) {
        this.transform.rotation = rot;
    }
    
    public void setRotation(float dx, float dy, float dz) {
        this.transform.rotation = new Vector(dx, dy, dz);
    }
    
    public abstract Polygon getBounds(Camera camera);

    public abstract Vector getCenter();
    
    /**
     * Applies the values in this object's transform to the given point. 
     * Handles position and rotation (pivoting about the object's center).
     */
    protected Vector applyTransform(Vector point) {
        //System.out.println("1 point: " + point);
        Vector center = getCenter();
        
        Vector centeredPoint = point.subtract(center);
        //System.out.println("2 centered point: " + centeredPoint);
        Matrix4 rotation = Matrix4.getRotation(transform.rotation);
        
        Vector rotatedPoint = centeredPoint.multiply(rotation, true);
        //System.out.println("3 rotated point: " + rotatedPoint);
        
        Vector transformedPoint = rotatedPoint.add(center).add(transform.position);
        //System.out.println("4 transformed point: " + transformedPoint);
        
        //return centeredPoint.multiply(transform.localRotation).add(center).add(transform.position).toVector3();
        return transformedPoint;
    }
    
    protected Polygon pointsToPolygon(Vector[] points) {
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            Vector point = points[i];
            xPoints[i] = (int)point.x;
            yPoints[i] = (int)point.y;
        }

        return new Polygon(xPoints, yPoints, points.length);
    }

    protected Polygon pointsToPolygon(ArrayList<Vector> points) {
        int[] xPoints = new int[points.size()];
        int[] yPoints = new int[points.size()];

        for (int i = 0; i < points.size(); i++) {
            Vector point = points.get(i);
            xPoints[i] = (int)point.x;
            yPoints[i] = (int)point.y;
        }

        return new Polygon(xPoints, yPoints, points.size());
    }
}