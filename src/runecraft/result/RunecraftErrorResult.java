package runecraft.result;

import runecraft.error.RunecraftTypeException;

import java.util.LinkedList;
import java.util.List;

public class RunecraftErrorResult extends RunecraftResult<String> {
    private final List<String> errors = new LinkedList<>();
    
    public RunecraftErrorResult(String error, String context) {
        super("", "");
        errors.add(error + "\n");
        addStackTrace(context);
    }
    
    public void addStackTrace(String stackTrace) {
        errors.add(stackTrace);
    }
    
    @Override
    public String get() {
        String toReturn = errors.getFirst();
        for (String error : errors.subList(1, errors.size())) {
            toReturn += "\tat " + error + '\n';
        }
        return toReturn;
    }
}
