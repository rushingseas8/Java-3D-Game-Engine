import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class AppMain {
    private static JFrame frame;
    private static DrawPanel panel;

    private static JSlider fov;

    protected static DrawSquare sq;

    public static void main(String[] args) {
        frame = new JFrame("3D Engine");
        frame.setLayout(new BorderLayout());

        panel = new DrawPanel();
        panel.setPreferredSize(new Dimension(640, 480));
        panel.camera.fov = 90;
        //panel.camera.position = new Vector(0, 0, 0);

        //DrawSquare sq = new DrawSquare(10, 10, 100, 100);
        sq = new DrawSquare(0, 0, 50, 50);
        sq.setPosition(-25, -25, 50f);

        //sq.setRotationX(1f);
        //sq.setRotation(1f, 1f, 0.2f);
        //sq.setRotation(0, 0, 0);
        panel.addObject(sq);
        panel.addMouseListener(new DragListener());
        panel.addMouseMotionListener(new DragListener());
        panel.addKeyListener(new InputListener());

        fov = new JSlider(JSlider.HORIZONTAL, 0, 180, 90);
        fov.addChangeListener(new FOVChangeListener());

        frame.add(panel, BorderLayout.CENTER);
        frame.add(fov, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static class InputListener implements KeyListener {
        private final float movementScale = 1f;

        private static boolean wDown = false;
        private static boolean sDown = false;
        private static boolean aDown = false;
        private static boolean dDown = false;

        public InputListener() {
            new Thread(() -> {
                    while (true) {
                        Vector pos = panel.camera.position;
                        if (wDown) {
                            pos = pos.add(new Vector(0, 0,  movementScale));
                        }
                        if (sDown) {
                            pos = pos.add(new Vector(0, 0, -movementScale));
                        }
                        if (aDown) {
                            pos = pos.add(new Vector(-movementScale, 0, 0));
                        }
                        if (dDown) {
                            pos = pos.add(new Vector( movementScale, 0, 0));
                        }

                        if (!panel.camera.position.equals(pos)) {
                            panel.camera.position = pos;
                            frame.repaint();
                        }

                        try {
                            // Roughly 60 fps update time
                            Thread.sleep(16);
                        } catch (InterruptedException e) {
                        }	
                    }
                }).start();
        }

        public void keyReleased(KeyEvent k) {
            switch(k.getKeyChar()) {
                case 'w': wDown = false; break;
                case 's': sDown = false; break;
                case 'a': aDown = false; break;
                case 'd': dDown = false; break;
            }
        }

        public void keyPressed(KeyEvent k) {
            System.out.println(k.getKeyChar());
            switch(k.getKeyChar()) {
                case 'w': wDown = true; break;
                case 's': sDown = true; break;
                case 'a': aDown = true; break;
                case 'd': dDown = true; break;
            }
        }

        public void keyTyped(KeyEvent k) {}
    }

    private static class FOVChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent c) {
            panel.camera.fov = ((JSlider)c.getSource()).getValue();
            frame.repaint();
        }
    }

    private static class DragListener implements MouseListener, MouseMotionListener {
        private static Point lastMovePoint = null;
        private static Point lastDragPoint = null;

        private final float rotationScale = (float)(Math.PI / 300);
        private static float rx = 0, ry = 0, rz = 0;

        private static float cx = 0, cy = 0, cz = 0;

        public void mouseEntered(MouseEvent m) {
            lastMovePoint = m.getPoint();
        }

        public void mouseExited(MouseEvent m) {
            lastMovePoint = null;
        }

        public void mouseReleased(MouseEvent m) {
            lastDragPoint = null;
        }

        public void mousePressed(MouseEvent m) {}

        public void mouseClicked(MouseEvent m) {
            panel.requestFocusInWindow();
        }

        public void mouseMoved(MouseEvent m) {
            if (lastMovePoint == null) {
                lastMovePoint = m.getPoint();
            }

            float dx = rotationScale * (float)(m.getX() - lastMovePoint.getX());
            cx += dx;

            float dy = rotationScale * (float)(m.getY() - lastMovePoint.getY());
            cy += dy;

            panel.camera.rotation = new Vector(cy, -cx, 0);
            frame.repaint();

            lastMovePoint = m.getPoint();
        }

        public void mouseDragged(MouseEvent m) {
            if (lastDragPoint == null) {
                lastDragPoint = m.getPoint();
            }

            float dx = rotationScale * (float)(m.getX() - lastDragPoint.getX());
            rx += dx;

            float dy = rotationScale * (float)(m.getY() - lastDragPoint.getY());
            ry += dy;

            sq.setRotation(-ry, rx, 0);

            frame.repaint();
            lastDragPoint = m.getPoint();
        }
    }
}