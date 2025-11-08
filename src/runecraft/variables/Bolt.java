package runecraft.variables;

public class Bolt extends RunecraftObject {
    private Substance material;
    private int damage;
    private int length;
    private int angle;
    
    
    public Bolt(Substance material, int damage, int length, int angle) {
        this.material = material;
        this.damage = damage;
        this.length = length;
        this.angle = angle;
    }
    
    public Substance getMaterial() {
        return material;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public int getLength() {
        return length;
    }
    
    public int getAngle() {
        return angle;
    }
    
    @Override
    public String toString() {
        return "Bolt(Material=" + material + ", Damage=" + damage + ", Length=" + length + ", Angle=" + angle + ", x=" + x + ", y=" + y +")";
    }
}
