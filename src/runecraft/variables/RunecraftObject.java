package runecraft.variables;

public abstract class RunecraftObject {
    protected boolean exists;
    protected float x;
    protected float y;
    
    public boolean exists() {
        return exists;
    }
    public void instantiate(int x, int y) {
        this.x = x;
        this.y = y;
        this.exists = true;
    }
    
    public abstract String toStringWithoutAddress();
    
    @Override
    public String toString() {
        return toStringWithoutAddress() + "-@" + String.valueOf(hashCode());
    }
}
