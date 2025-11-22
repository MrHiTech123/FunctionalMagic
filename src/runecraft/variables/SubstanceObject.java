package runecraft.variables;

public class SubstanceObject extends RunecraftObject {
    private final Substance substance;
    
    public SubstanceObject(Substance substance) {
        this.substance = substance;
    }
    
    @Override
    public String toStringWithoutAddress() {
        return substance.toString();
    }
    
    // @Override
    // public String toString() {
    //     return toStringWithoutAddress();
    // }
    
    @Override
    public boolean exists() {
        return true;
    }
}
