package runecraft.variables;

public class UnknownObject extends RunecraftObject {
    @Override
    public String toStringWithoutAddress() {
        return "Unknown";
    }
    
    @Override
    public Substance getMaterial() {
        return Substance.UNKNOWN;
    }
}
