package runecraft.builtins;

import runecraft.error.RunecraftError;
import runecraft.result.RunecraftErrorResult;
import runecraft.result.RunecraftResult;
import runecraft.variables.RunecraftObject;
import runecraft.variables.Substance;

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
    
    public void shoot(RunecraftObject object) {
        
    }
}
