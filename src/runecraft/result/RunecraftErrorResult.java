package runecraft.result;

import runecraft.error.RunecraftTypeException;

import java.util.LinkedList;
import java.util.List;

public class RunecraftErrorResult extends RunecraftResult<String> {
    private final List<String> errors = new LinkedList<>();
    
    public RunecraftErrorResult(String error) {
        super("", "");
        errors.add(error);
    }
    
    public void addStackTrace(String stackTrace) {
        errors.add(stackTrace);
    }
    
    @Override
    public String get() {
        String toReturn = "";
        for (String error : errors) {
            toReturn += error + '\n';
        }
        return toReturn;
    }
}
