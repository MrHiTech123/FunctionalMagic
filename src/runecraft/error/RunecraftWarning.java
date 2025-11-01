package runecraft.error;


public class RunecraftWarning {
    private final RunecraftWarningType type;
    private final String message;
    
    public RunecraftWarning(RunecraftWarningType type, String message) {
        this.type = type;
        this.message = message;
    }
    
    @Override
    public String toString() {
        return type + ": " + message;
    }
}
