package runecraft.result;

import java.util.LinkedList;
import java.util.List;

public class RunecraftErrorResult extends RunecraftResult<String> {
    private final String error;
    private final List<String> stackTrace = new LinkedList<>();
    
    public RunecraftErrorResult(String error, String remainingTokens) {
        super("", remainingTokens);
        this.error = error;
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
        String toReturn = error + '\n';
        for (String error : stackTrace) {
            toReturn += "\tat " + error + '\n';
        }
        return toReturn;
    }
}
