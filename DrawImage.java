import java.awt.image.*;
import java.util.*;
import java.awt.*;

public class DrawImage extends DrawObject {
    private BufferedImage image;
    private float x, y, z;
    
    public DrawImage(BufferedImage img, float x, float y, float z) {
        this.image = img;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public Polygon getBounds(Camera camera) {
        return null;
    }
    
    @Override
    public Vector getCenter() {
        return null;
    }
}