package runecraft.parser;

import runecraft.error.RunecraftError;
import runecraft.result.RunecraftErrorResult;
import runecraft.result.RunecraftResult;
import runecraft.variables.Substance;

public class RunecraftBuiltins {
    public static RunecraftResult<?> combineSubstances(Substance first, Substance second) {
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
}
