package runecraft.variables;

public class Bolt implements RunecraftObject {
    private Substance substance;
    
    public Bolt(Substance substance) {
        this.substance = substance;
    }
    
    public Substance getSubstance() {
        return substance;
    }
    
    @Override
    public String toString() {
        return "(boltOf " + substance + ")";
    }
}
