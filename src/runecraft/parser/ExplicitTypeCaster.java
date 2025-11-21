package runecraft.parser;

import runecraft.datastructure.Pair;
import runecraft.variables.EverySubstance;
import runecraft.variables.Substance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class TypeCasting<T, R> extends Pair<Class<R>, Function<T, R>> {
    
    public TypeCasting(Class<R> first, Function<T, R> second) {
        super(first, second);
    }
}

public class ExplicitTypeCaster {
    private static final Map<Class<?>, List<TypeCasting<?, ?>>> typeCastings = new HashMap<>();
    static {
        typeCastings.put(Substance.class, List.of(
                new TypeCasting<>(EverySubstance.class, EverySubstance::new))
        );
        
    }
    
    public static boolean canCast(Class<?> toCast, Class<?> castResult) {
        if (!typeCastings.containsKey(toCast)) {
            return false;
        }
        return typeCastings.get(toCast).stream().anyMatch(typeCasting -> typeCasting.getFirst().equals(castResult));
    }
    
    public static <T, R> R cast(Class<T> toCastClass, Class<R> resultClass, )
    
    public static <T> T cast(Class<T> castResultClass, Object toCast) {
        Class<?> currentClass = toCast.getClass();
        while (!currentClass.equals(Object.class)) {
            if (canCast(currentClass, castResultClass)) {
                return 
            }
            
            currentClass = currentClass.getSuperclass();
        }
        
        throw new RuntimeException("Error: Can't cast");
    }
    
}
