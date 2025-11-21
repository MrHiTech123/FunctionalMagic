package runecraft.variables;

public class PointerObject extends RunecraftObject {
    @Override
    public String toStringWithoutAddress() {
        return "Pointer";
    }
    
    @Override
    public boolean exists() {
        return true;
    }
}
