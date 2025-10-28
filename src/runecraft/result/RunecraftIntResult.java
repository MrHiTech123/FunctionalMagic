package runecraft.result;

import runecraft.error.RunecraftTypeException;

public class RunecraftIntResult implements RunecraftResult{
    private final int result;
    private final String remainingTokens;
    
    public RunecraftIntResult(int result, String remainingTokens) {
        this.result = result;
        this.remainingTokens = remainingTokens;
    }
    
    @Override
    public String resultString() {
        throw new RunecraftTypeException("Error: Runecraft int result was asked for String");
    }
    
    @Override
    public int resultInt() {
        return result;
    }
    
    @Override
    public String remainingTokens() {
        return remainingTokens;
    }
}
