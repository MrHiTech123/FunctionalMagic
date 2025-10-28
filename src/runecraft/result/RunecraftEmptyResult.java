package runecraft.result;

public class RunecraftEmptyResult implements RunecraftResult{
    private final String remainingTokens;
    
    public RunecraftEmptyResult(String remainingTokens) {
        this.remainingTokens = remainingTokens;
    }
    
    @Override
    public String resultString() {
        return "";
    }
    
    @Override
    public int resultInt() {
        return 0;
    }
    
    @Override
    public String remainingTokens() {
        return remainingTokens;
    }
}
