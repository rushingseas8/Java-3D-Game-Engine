import java.util.*;
import java.awt.*;

public class DrawSquare extends DrawObject {
    public float x, y, width, height;
    
    public DrawSquare(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }
    
    public Vector[] getRawBounds() {
        
        return new Vector[] {
            new Vector(x, y, 0),
            new Vector(x + width, y, 0),
            new Vector(x + width, y + height, 0),
            new Vector(x, y + height, 0)
        };
        
        /*
        return new Vector3[] {
            new Vector3(x, y + height, 0),
            new Vector3(x + width, y + height, 0),
            new Vector3(x + width, y, 0),
            new Vector3(x, y, 0)
        };
        */
    }
    
    @Override
    public Vector getCenter() {
        Vector[] points = getRawBounds();
        Vector center = Vector.zero();
        for (int i = 0; i < points.length; i++) {
            center = center.add(points[i]);
        }
        center = center.divide(4);
        return center;
    }
    
    @Override
    public Polygon getBounds(Camera camera) {
        Vector[] points = getRawBounds();
        
        for (int i = 0; i < points.length; i++) {
            String raw = "Original point is: " + points[i];
            
            //System.out.println("Raw point " + i + ":" + points[i]);
            //points[i] = ;
            //System.out.println("Transformed point " + i + ":" + points[i]);
            
            //points[i] = points[i].multiply(cameraMatrix);
            //points[i] = Helper.worldToCamera(camera, applyTransform(points[i]));
            points[i] = applyTransform(points[i]);
            
            String trans = "Transformed point is: " + points[i];
            //System.out.println(points[i]);
            
            if (points[i].z < 0.01) {
                points[i].z = 0.01f;
            }
            
            points[i] = Helper.worldToCamera(camera, points[i]);
            //if (Math.abs(points[i].x) > 1000) {
            if (i == 2) {
                System.out.println(raw);
                System.out.println(trans);
                System.out.println("Found outlier point: " + points[i]);
            }
            //System.out.println(points[i]);
            
            //System.out.println("Camera normalized point " + i + ":" + points[i]);
        }
        
        return pointsToPolygon(points);
    }
}