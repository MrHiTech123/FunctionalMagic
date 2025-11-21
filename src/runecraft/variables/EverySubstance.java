package runecraft.variables;

public class EverySubstance extends RunecraftObject {
    private final Substance substance;
    
    
    public EverySubstance(Substance substance) {
        this.substance = substance;
    }
    
    
    @Override
    public String toStringWithoutAddress() {
        return substance.toString();
    }
}
