package runecraft.variables;

public class Cone extends RunecraftObject {
    private final Substance material;
    private final int damage;
    private final float size;
    private final float angle;
    
    public Cone(Substance material, int damage, int size, int angle) {
        this.material = material;
        this.damage = damage;
        this.size = size;
        this.angle = angle;
    }
    
    public Substance getMaterial() {
        return material;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public float getSize() {
        return size;
    }
    
    public float getAngle() {
        return angle;
    }
    
    @Override
    public String toStringWithoutAddress() {
        return "Cone(Material=" + material + ", Damage=" + damage + ", Size=" + size + ", Angle=" + angle + " x=" + x + " y=" + y +")";
    }
}
