package runecraft.parser;

import runecraft.datastructure.explicittypecaster.ClassClassFunctionTrio;
import runecraft.datastructure.explicittypecaster.ExplicitTypeCaster;
import runecraft.variables.SubstanceObject;
import runecraft.variables.RunecraftObject;
import runecraft.variables.Substance;

public class RunecraftTypeCaster extends ExplicitTypeCaster {
    public RunecraftTypeCaster() {
        super(
                new ClassClassFunctionTrio<>(Substance.class, SubstanceObject.class, SubstanceObject::new),
                new ClassClassFunctionTrio<>(RunecraftObject.class, Substance.class, RunecraftObject::getMaterial)
        );
    }
}
