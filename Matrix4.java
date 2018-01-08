public class Matrix4 {
    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;
    
    public Matrix4() {
        this.m00 = this.m01 = this.m02 = this.m03 = 0;
        this.m10 = this.m11 = this.m12 = this.m13 = 0;
        this.m20 = this.m21 = this.m22 = this.m23 = 0;
        this.m30 = this.m31 = this.m32 = this.m33 = 0;
    }
    
    public static Matrix4 getIdentity() {
        Matrix4 m = new Matrix4();
        m.m00 = m.m11 = m.m22 = m.m33 = 1;
        return m;
    }
    
    public static Matrix4 getTransform(Vector v) {
        return getTransform(v.x, v.y, v.z);
    }
    
    public static Matrix4 getTransform(float dx, float dy, float dz) {
        Matrix4 m = getIdentity();
        
        m.m30 = dx;
        m.m31 = dy;
        m.m32 = dz;
        
        return m;
    }
    
    public static Matrix4 getScale(float sx, float sy, float sz) {
        Matrix4 m = getIdentity();
        
        m.m00 = sx;
        m.m11 = sy;
        m.m22 = sz;
        
        return m;
    }
    
    public static Matrix4 getShear(float hxy, float hxz, float hyx, float hyz, float hzx, float hzy) {
        Matrix4 m = getIdentity();
        
        m.m01 = hxy;
        m.m02 = hxz;
        m.m10 = hyx;
        m.m20 = hzx;
        m.m12 = hyz;
        m.m21 = hzy;
        
        return m;
    }
    
    public static Matrix4 getRotationX(float rx) {
        Matrix4 m = getIdentity();
        if (Math.abs(rx) < 0.01) {
            return m;
        }
        
        m.m11 = (float)  Math.cos(rx);
        m.m12 = (float) -Math.sin(rx);
        m.m21 = (float)  Math.sin(rx);
        m.m22 = (float)  Math.cos(rx);
        
        return m;
    }
    
    public static Matrix4 getRotationY(float ry) {
        Matrix4 m = getIdentity();
        if (Math.abs(ry) < 0.01) {
            return m;
        }
        
        m.m00 = (float)  Math.cos(ry);
        m.m02 = (float)  Math.sin(ry);
        m.m20 = (float) -Math.sin(ry);
        m.m22 = (float)  Math.cos(ry);
        
        return m;
    }    
    
    public static Matrix4 getRotationZ(float rz) {
        Matrix4 m = getIdentity();
        if (Math.abs(rz) < 0.01) {
            return m;
        }
        
        m.m00 = (float)  Math.cos(rz);
        m.m01 = (float) -Math.sin(rz);
        m.m10 = (float)  Math.sin(rz);
        m.m11 = (float)  Math.cos(rz);
        
        return m;
    }
    
    // Note: rotations are dependent upon the order done; decide this later when
    // making a general rotation function.
    public static Matrix4 getRotation(Vector rot) {
        return getRotation(rot.x, rot.y, rot.z);
    }
    
    public static Matrix4 getRotation(float rx, float ry, float rz) {
        return getRotationZ(rz).multiply(getRotationY(ry)).multiply(getRotationX(rx));
    }
    
    public static Matrix4 getPerspective(Camera cam) {
        
        Matrix4 m = new Matrix4();
       
        float S = (float)(1 / Math.tan( cam.fov * 0.5 * Math.PI / 180 ));
        m.m00 = S;
        m.m11 = S;
        m.m22 = -cam.far / (cam.far - cam.near);
        m.m32 = (-cam.far * cam.near) / (cam.far - cam.near);
        m.m23 = -1;
        m.m33 = 0;
        
        return m;
        
        //return getPerspective(cam.near, cam.far, 0, cam.width, cam.height, 0);
    }
    
    public static Matrix4 getPerspective(float n, float f, float l, float r, float t, float b) {
        Matrix4 m = new Matrix4();
        
        m.m00 = 2 * n / (r - l);
        m.m11 = 2 * n / (t - b);
        m.m20 = (r + l) / (r - l);
        m.m21 = (t + b) / (t - b);
        m.m22 = -(f + n) / (f - n);
        m.m23 = -1;
        m.m32 = -2 * f * n / (f - n);
        
        return m;
    }
    
    public Matrix4 multiply(Matrix4 o) {
        Matrix4 m = new Matrix4();
        
        m.m00 = m00 * o.m00 + m01 * o.m10 + m02 * o.m20 + m03 * o.m30;
        m.m01 = m00 * o.m01 + m01 * o.m11 + m02 * o.m21 + m03 * o.m31;
        m.m02 = m00 * o.m02 + m01 * o.m12 + m02 * o.m22 + m03 * o.m32;
        m.m03 = m00 * o.m03 + m01 * o.m13 + m02 * o.m23 + m03 * o.m33;
               
        m.m10 = m10 * o.m00 + m11 * o.m10 + m12 * o.m20 + m13 * o.m30;
        m.m11 = m10 * o.m01 + m11 * o.m11 + m12 * o.m21 + m13 * o.m31;
        m.m12 = m10 * o.m02 + m11 * o.m12 + m12 * o.m22 + m13 * o.m32;
        m.m13 = m10 * o.m03 + m11 * o.m13 + m12 * o.m23 + m13 * o.m33;
               
        m.m20 = m20 * o.m00 + m21 * o.m10 + m22 * o.m20 + m23 * o.m30;
        m.m21 = m20 * o.m01 + m21 * o.m11 + m22 * o.m21 + m23 * o.m31;
        m.m22 = m20 * o.m02 + m21 * o.m12 + m22 * o.m22 + m23 * o.m32;
        m.m23 = m20 * o.m03 + m21 * o.m13 + m22 * o.m23 + m23 * o.m33;
               
        m.m30 = m30 * o.m00 + m31 * o.m10 + m32 * o.m20 + m33 * o.m30;
        m.m31 = m30 * o.m01 + m31 * o.m11 + m32 * o.m21 + m33 * o.m31;
        m.m32 = m30 * o.m02 + m31 * o.m12 + m32 * o.m22 + m33 * o.m32;
        m.m33 = m30 * o.m03 + m31 * o.m13 + m32 * o.m23 + m33 * o.m33;
        
        return m;
    }
}