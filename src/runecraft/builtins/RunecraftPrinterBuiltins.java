package runecraft.builtins;

import runecraft.builtins.RunecraftBuiltins;
import runecraft.result.RunecraftResult;
import runecraft.variables.RunecraftObject;

public class RunecraftPrinterBuiltins extends RunecraftBuiltins {
    
    @Override
    public RunecraftObject create(RunecraftObject object) {
        System.out.println("(create " + object + ")");
        return super.create(object);
    }
    
    @Override
    public RunecraftResult<?> shoot(RunecraftObject objectShot) {
        if (objectShot.exists()) {
            System.out.println("(shoot " + objectShot + ")");
        }
        return super.shoot(objectShot);
        
    }
}
