package runecraft.builtins;

import runecraft.result.RunecraftResult;
import runecraft.variables.RunecraftObject;

public class RunecraftPrinterBuiltins extends RunecraftBuiltins {
    
    @Override
    public RunecraftObject create(RunecraftObject object) {
        System.out.println("(create " + object + ")");
        return super.create(object);
    }
    
    @Override
    public RunecraftResult<?> yeet(RunecraftObject objectShot, int speed, int angleRadians) {
        if (objectShot.exists()) {
            System.out.println("(yeet " + objectShot + ", Speed=" + speed + ", Angle=" + angleRadians + ")");
        }
        return super.yeet(objectShot, speed, angleRadians);
        
    }
}
