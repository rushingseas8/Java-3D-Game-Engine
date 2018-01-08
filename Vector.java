public class Vector {
    public float x, y, z, w;
    
    // The different constructors allow for creating implicit Vector2, Vector3, etc.
    
    public Vector() {
        this(0, 0, 0, 0);
    }
    
    public Vector(float x) {
        this(x, 0, 0, 0);
    }
    
    public Vector(float x, float y) {
        this(x, y, 0, 0);
    }
    
    public Vector(float x, float y, float z) {
        this(x, y, z, 0);
    }
    
    public Vector(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public static Vector zero() {
        return new Vector();
    }
    
    public static Vector one() {
        return new Vector(1, 1, 1, 1);
    }

    // Vector-scalar operations
    
    public Vector add(float val) {
        this.x += val;
        this.y += val;
        this.z += val;
        this.w += val;
        
        return this;
    }
    
    public Vector subtract(float val) {
        this.x -= val;
        this.y -= val;
        this.z -= val;
        this.w -= val;
        
        return this;
    }
    
    public Vector multiply(float val) {
        this.x *= val;
        this.y *= val;
        this.z *= val;
        this.w *= val;
        
        return this;
    }
    
    public Vector divide(float val) {
        this.x /= val;
        this.y /= val;
        this.z /= val;
        this.w /= val;
        
        return this;
    }
    
    // Vector-vector operations
    
    public Vector add(Vector other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        this.w += other.w;
        
        return this;
    }
        
    public Vector subtract(Vector other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        this.w -= other.w;
        
        return this;
    }
    
    // Vector-matrix operations

    public Vector multiply(Matrix4 m) {
        return multiply(m, false);
    }
    
    /**
     * For multiplication using Vector4s, you need to have w = 1. Thus, for Vectors with
     * dimension less than 4, their w values need to be set to 1. I left in the option because
     * I'm not sure if some regular multiplication will be needed.
     * 
     * tl;dr if appendW == false, regular multiplication;
     *  appendW == true -> set w = 1 first (useful for rotations)
     *  
     * This function returns a new Vector.
     */
    public Vector multiply(Matrix4 m, boolean appendW) {     
        if (appendW) {
            this.w = 1;
        }
        
        Vector newVector = new Vector(
                this.x * m.m00 + this.y * m.m10 + this.z * m.m20 + this.w * m.m30,
                this.x * m.m01 + this.y * m.m11 + this.z * m.m21 + this.w * m.m31,
                this.x * m.m02 + this.y * m.m12 + this.z * m.m22 + this.w * m.m32,
                this.x * m.m03 + this.y * m.m13 + this.z * m.m23 + this.w * m.m33
            );

        if (newVector.w != 1.0) {
            //System.out.println("Normalization operation needs to occur: " + newVector);
            newVector = newVector.normalizeW();
            //System.out.println("Vector afterwards: " + newVector.normalizeW());
        }
        return newVector;
    }
    
    /**
     * Rotates this Vector by the Quaternion q.
     * Calculates the conjugation (p' = q * p * q^-1) with this vector converted to a pure Quaternion.
     */
    public Vector multiply(Quaternion q) {
        return q.multiply(new Quaternion(0, x, y, z)).multiply(q.inverse()).getVector();
    }
    
    // Object conversion/normalization stuff

    public Vector normalizeW() {
        if (w == 0) {
            return this;
        }
        if (w != 1.0) {
            this.x /= -w;
            this.y /= -w;
            this.z /= -w;
            this.w = -1;
        }
        return this;
    }

    public String toString() {
        return "x=" + x + ", y=" + y + ", z=" + z + ", w=" + w;
    }
    
    public boolean equals(Object other) {
        if (other instanceof Vector) {
            Vector v = (Vector)other;
            return
                Math.abs(this.x - v.x) < 0.01 &&
                Math.abs(this.y - v.y) < 0.01 &&
                Math.abs(this.z - v.z) < 0.01 &&
                Math.abs(this.w - v.w) < 0.01;
        }
        return false;
    }
}