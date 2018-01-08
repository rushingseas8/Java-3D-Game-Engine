public class Transform {
    public Vector position;
    public Vector rotation;
    //public Quaternion rotation;
    
    // localCenter?
    public Matrix4 localRotation;
    public Matrix4 localScale;
    
    public Transform() {
        this.position = Vector.zero();
        //this.rotation = Vector.zero();
        //this.rotation = Quaternion.identity();
        
        this.localRotation = Matrix4.getIdentity();
        this.localScale = Matrix4.getIdentity();
    }
}