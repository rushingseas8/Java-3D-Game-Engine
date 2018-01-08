import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class DrawPanel extends JPanel implements ComponentListener {
    private ArrayList<DrawObject> objects;
    public Camera camera;
    
    public DrawPanel() {
        super();
        
        this.objects = new ArrayList<>();
        this.camera = new Camera(this.getWidth(), this.getHeight());
        
        this.addComponentListener(this);
    }
    
    public void addObject(DrawObject d) {
        objects.add(d);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        g.setColor(Color.WHITE);
        for (int i = 0; i < objects.size(); i++) {
            DrawObject di = objects.get(i);
            if (di instanceof DrawSquare) {
                g.fillPolygon(di.getBounds(camera));
            }
        }
    }

    @Override
    public void componentHidden(ComponentEvent c) {}
    
    @Override
    public void componentMoved(ComponentEvent e) {}
    
    @Override
    public void componentResized(ComponentEvent e) {
        this.camera.setScreenSize(this.getSize());
    }
    
    @Override
    public void componentShown(ComponentEvent e) {}
}