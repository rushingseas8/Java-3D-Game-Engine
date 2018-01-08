public class Helper {
    public static Vector worldToCamera(Camera camera, Vector pos) {
        //System.out.println("[0] Input position: " + pos);
        
        // First, subtract the camera position and rotation.
        Vector toReturn = pos.subtract(camera.position);
        
        Vector cameraRotation = camera.rotation;
        Vector inverseCameraRotation = new Vector(-cameraRotation.x, -cameraRotation.y, -cameraRotation.z);
        
        Matrix4 icrM = Matrix4.getRotation(inverseCameraRotation);
        toReturn = toReturn.multiply(icrM);
        
        // Multiply by the camera transformation
        toReturn = toReturn.multiply(Matrix4.getPerspective(camera));
        //System.out.println("[1] Perspective transform: " + toReturn);
        
        // Normalize from perspective to Cartesian coordinates
        toReturn = toReturn.normalizeW();
        
        float min = Math.min(camera.width, camera.height);
        
        // The current Vector is now normalized from -1 to 1. This adjusts the
        // normalization to screen (raster) coordinates.
        toReturn = new Vector(
            (float) (( (toReturn.x + 1) * 0.5 * camera.width)), 
            (float) ((( (toReturn.y + 1) * 0.5 * camera.height))),
            toReturn.z,
            toReturn.w
        );
        
        // Normalize the Z coordinate. If it's outside the drawing planes, clip to them.
        //toReturn.z = clamp(toReturn.z, camera.near, camera.far);
        
        return toReturn;
    }
    
    public static float clamp(float value, double min, double max) {
        if (value < min) {
            return (float)min;
        }
        if (value > max) {
            return (float)max;
        }
        return value;
    }
    
    public static float mod(float value, double mod) {
        return (float)(value - ( (int)(value / mod) * mod));
    }
}