package runecraft.result;

public class RunecraftEmptyResult extends RunecraftResult<String> {
    public RunecraftEmptyResult(String remainingTokens) {
        super(null, remainingTokens);
    }
    
    public String get() {
        throw new RuntimeException("Error: Cannot get result from empty Runecraft result");
    }
    
}
