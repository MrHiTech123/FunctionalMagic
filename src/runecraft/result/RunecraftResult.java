package runecraft.result;

import runecraft.error.RunecraftWarning;
import runecraft.error.RunecraftWarningType;

import java.util.LinkedList;
import java.util.List;

public class RunecraftResult<T> {
    protected final T result;
    protected final String remainingTokens;
    protected final List<RunecraftWarning> warnings;
    
    public RunecraftResult(T result, String remainingTokens, List<RunecraftWarning> warnings) {
        this.result = result;
        this.remainingTokens = remainingTokens;
        this.warnings = warnings;
    }
    
    public RunecraftResult(T result, String remainingTokens) {
        this(result, remainingTokens, new LinkedList<>());
    }
    
    public T get() {
        return result;
    }
    public String remainingTokens() {
        return remainingTokens;
    }
    
    public void addWarning(RunecraftWarningType type, String message) {
        RunecraftWarning toAdd = new RunecraftWarning(type, message);
        warnings.add(toAdd);
    }
    
    public String getWarnings() {
        String toReturn = "";
        for (RunecraftWarning warning : warnings) {
            toReturn += warning.toString() + '\n';
        }
        return toReturn;
    }
}
