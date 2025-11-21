package runecraft.variables;

public class PointerObject extends RunecraftObject {
    private RunecraftObject value = new UnknownObject();
    
    public void setValue(RunecraftObject value) {
        this.value = value;
    }
    
    public RunecraftObject getValue() {
        return value;
    }
    
    @Override
    public String toStringWithoutAddress() {
        return "Pointer(value=" + value.toStringWithoutAddress() + ")";
    }
    
    @Override
    public boolean exists() {
        return true;
    }
}
