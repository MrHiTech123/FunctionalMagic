package runecraft.result;

import java.util.LinkedList;
import java.util.List;

public class RunecraftErrorResult extends RunecraftResult<String> {
    private final String error;
    private final List<String> stackTrace = new LinkedList<>();
    
    public RunecraftErrorResult(String error) {
        super("", "");
        this.error = error;
    }
    public RunecraftErrorResult(String error, String context) {
        this(error);
        addStackTrace(context);
    }
    
    public void addStackTrace(String stackTrace) {
        this.stackTrace.add(stackTrace);
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
