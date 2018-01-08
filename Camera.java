import java.awt.*;

public class Camera {
    public int width, height;
    
    public float fov;
    public float near, far;
    public Vector position;
    public Vector rotation;
    
    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        
        this.fov = 90;
        this.near = 1;
        this.far = 100;
        this.position = new Vector();
        this.rotation = new Vector();
        
        assert near < far;
    }
    
    public void setScreenSize(Dimension dim) {
        this.width = (int)dim.getWidth();
        this.height = (int)dim.getHeight();
    }
}