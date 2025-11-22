package runecraft.datastructure.explicittypecaster;

import runecraft.datastructure.pair.Trio;

import java.util.function.Function;

public class ClassClassFunctionTrio<T, U> extends Trio<Class<T>, Class<U>, Function<T, U>> {
    
    public ClassClassFunctionTrio(Class<T> first, Class<U> second, Function<T, U> third) {
        super(first, second, third);
    }
}
