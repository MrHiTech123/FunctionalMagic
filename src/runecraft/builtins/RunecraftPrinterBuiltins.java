package runecraft.builtins;

import runecraft.builtins.RunecraftBuiltins;
import runecraft.variables.RunecraftObject;

public class RunecraftPrinterBuiltins extends RunecraftBuiltins {
    @Override
    public void shoot(RunecraftObject objectShot) {
        System.out.println("(shoot " + objectShot + ")");
    }
}
