package runecraft.parser;

import runecraft.datastructure.explicittypecaster.ClassClassFunctionTrio;
import runecraft.datastructure.explicittypecaster.ExplicitTypeCaster;
import runecraft.variables.EverySubstance;
import runecraft.variables.Substance;

public class RunecraftTypeCaster extends ExplicitTypeCaster {
    public RunecraftTypeCaster() {
        super(
                new ClassClassFunctionTrio<>(Substance.class, EverySubstance.class, EverySubstance::new)
        );
    }
}
