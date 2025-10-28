package runecraft.result;

import runecraft.error.RunecraftTypeException;

public class RunecraftObjectResult implements RunecraftResult {
    private final String result;
    private final String remainingTokens;
    
    public RunecraftObjectResult(String result, String remainingTokens) {
        this.result = result;
        this.remainingTokens = remainingTokens;
    }
    
    @Override
    public String resultString() {
        return result;
    }
    
    @Override
    public int resultInt() {
        throw new RunecraftTypeException("Error: Object runtime exception was asked for int");
    }
    
    @Override
    public String remainingTokens() {
        return remainingTokens;
    }
}
