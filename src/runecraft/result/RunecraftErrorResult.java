package runecraft.result;

import runecraft.error.RunecraftError;

import java.util.LinkedList;
import java.util.List;

public class RunecraftErrorResult extends RunecraftResult<String> {
    private final RunecraftError errorType;
    private final String errorMessage;
    private final List<String> stackTrace = new LinkedList<>();
    
    public RunecraftErrorResult(RunecraftError errorType, String errorMessage, String remainingTokens) {
        super("", remainingTokens);
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }
    
    public void addStackTrace(String tokens, String remainingTokens) {
        if (remainingTokens.isEmpty()) {
            this.stackTrace.add(tokens);
        }
        else {
            this.stackTrace.add(tokens.substring(0, tokens.lastIndexOf(remainingTokens)));
        }
    }
    
    @Override
    public String get() {
        String toReturn = errorType + ": " + errorMessage + '\n';
        for (String error : stackTrace) {
            toReturn += "\tat " + error + '\n';
        }
        return toReturn;
    }
}
