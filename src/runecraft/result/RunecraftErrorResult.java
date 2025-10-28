package runecraft.result;

import runecraft.error.RunecraftTypeException;

import java.util.LinkedList;
import java.util.List;

public class RunecraftErrorResult implements RunecraftResult {
    private final List<String> errors = new LinkedList<>();
    
    public RunecraftErrorResult(String error) {
        errors.add(error);
    }
    
    public void addStackTrace(String stackTrace) {
        errors.add(stackTrace);
    }
    
    @Override
    public String resultString() {
        String toReturn = "";
        for (String error : errors) {
            toReturn += error + '\n';
        }
        return toReturn;
    }
    
    @Override
    public int resultInt() {
        throw new RunecraftTypeException("Error: Error runtime exception was asked for int");
    }
    
    @Override
    public String remainingTokens() {
        return "";
    }
}
