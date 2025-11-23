package runecraft.variables;

public class Bolt extends RunecraftObject {
    private final Substance material;
    private final int damage;
    
    public Bolt(Substance material, int damage) {
        this.material = material;
        this.damage = damage;
    }
    
    public Substance getMaterial() {
        return material;
    }
    
    public int getDamage() {
        return damage;
    }
    
    @Override
    public String toStringWithoutAddress() {
        return "Bolt(Material=" + material + ", Damage=" + damage + ", x=" + x + ", y=" + y +")";
    }
}
