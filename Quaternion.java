public class Quaternion {
    public float w, x, y, z;
    
    public Quaternion() {
        this(0, 0, 0, 0);
    }
    
    public Quaternion(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Quaternion identity() {
        return new Quaternion(0, 0, 0, 1);
    }
    
    public Quaternion angleAxis(double theta, Vector v) {
        return angleAxis(theta, v.x, v.y, v.z);
    }
    
    public Quaternion angleAxis(double theta, float x, float y, float z) {
        float s = (float) Math.sin(theta / 2);
        return new Quaternion((float)Math.cos(theta / 2), s * x, s * y, s * z).normalize();
    }
    
    public Quaternion normalize() {
        float norm = (float) Math.sqrt(w * w + x * x + y * y + z * z);
        this.w /= norm;
        this.x /= norm;
        this.y /= norm;
        this.z /= norm;
        
        return this;
    }
    
    public Quaternion inverse() {
        return new Quaternion(w, -x, -y, -z);
    }
    
    public Vector toEulerAngles() {
        float newY = (float)Math.asin(2 * (w * y - x * z));
        
        if (x * y + z * w == 0.5) {
            return new Vector((float)(2 * Math.atan2(x, w)), newY, 0);
        }
        if (x * y + z * w == -0.5) {
            return new Vector((float)(-2 * Math.atan2(x, w)), newY, 0);
        }
        
        return new Vector(
            (float)Math.atan2(2 * (w * x + y * z), 1 - 2 * (x * x + y * y)),
            newY,
            (float)Math.atan2(2 * (w * z + x * y), 1 - 2 * (y * y + z * z))
        );
    }
    
    /**
     * Per wikipedia (https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation)
     */
    public Quaternion multiply(Quaternion other) {
        float newW = w * other.w - x * other.x - y * other.y - z * other.z;
        float newX = w * other.x + x * other.w + y * other.z - z * other.y;
        float newY = w * other.y - x * other.z + y * other.w + z * other.x;
        float newZ = w * other.z + x * other.y - y * other.x + z * other.w;
        
        return new Quaternion(newW, newX, newY, newZ);
        /*
        this.w = newW;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        
        return this;
        */
    }
    
    public Vector getVector() {
        return new Vector(this.x, this.y, this.z);
    }
}