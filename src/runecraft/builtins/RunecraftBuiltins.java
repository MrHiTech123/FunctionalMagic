package runecraft.builtins;

import runecraft.error.RunecraftError;
import runecraft.result.RunecraftEmptyResult;
import runecraft.result.RunecraftErrorResult;
import runecraft.result.RunecraftResult;
import runecraft.variables.RunecraftObject;
import runecraft.variables.Substance;

import java.util.Set;

public class RunecraftBuiltins {
    public RunecraftResult<?> combineSubstances(Substance first, Substance second) {
        Substance toReturn = Substance.combine(first, second);
        if (toReturn == null) {
            return new RunecraftErrorResult(
                    RunecraftError.RecipeError,
                    first + " cannot be combined with " + second, 
                    ""
            );
        }
        return new RunecraftResult<>(toReturn, "");
    }
    
    public RunecraftObject createObject(RunecraftObject object, int xOffsest, int yOffset) {
        object.instantiate(xOffsest, yOffset);
        return object;
    }
    
    public void assignPointer(RunecraftObject object) {
        System.out.println("(Turn Pointer into " + object + ")");
    }
    
    public RunecraftObject create(RunecraftObject object) {
        object.instantiate(0, 0);
        return object;
    }
    
    
    public RunecraftResult<?> yeet(RunecraftObject object, int speed, int angleRadians) {
        if (!object.exists()) return new RunecraftErrorResult(
                RunecraftError.NonExistanceError,
                "Failed to shoot object " + object + ", object does not exist",
                ""
        );
        return new RunecraftEmptyResult("");
    }
    
    public boolean isTruthy(int insertedInt) {
        return insertedInt > 0;
    }
    
    public int size(Object object) {
        if (object instanceof Set<?> set) {
            return set.size();
        }
        return -1;
    }
    
    
    
    
}
