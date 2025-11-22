package runecraft.datastructure.explicittypecaster;

import java.util.*;
import java.util.function.Function;

// Not me actually using invariants for once
public class ExplicitTypeCaster {
    private static final Map<Class<?>, Map<Class<?>, Function<?, ?>>> typeCastings = new HashMap<>();
    
    public ExplicitTypeCaster(ClassClassFunctionTrio<?, ?>... trios) {
        for (ClassClassFunctionTrio<?, ?> trio : trios) {
            put(trio);
        }
    }
    
    private <T, R> void put(ClassClassFunctionTrio<T, R> trio) {
        put(trio.getFirst(), trio.getSecond(), trio.getThird());
    }
    
    private <T, R> void put(Class<T> keyClass, Class<R> valueClass, Function<T, ? extends R> function) {
        if (!typeCastings.containsKey(keyClass)) {
            typeCastings.put(keyClass, new HashMap<>());
        }
        typeCastings.get(keyClass).put(valueClass, function);
        if (!valueClass.equals(Object.class)) {
            put(keyClass, valueClass.getSuperclass(), function);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    private <T, R> Function<T, R> getCastingFunctionOnce(Class<T> keyClass, Class<R> valueClass) {
        if (keyClass.equals(valueClass)) return (a -> (R)a);
        if (!typeCastings.containsKey(keyClass)) return null;
        if (!typeCastings.get(keyClass).containsKey(valueClass)) return null;
        return (Function<T, R>) typeCastings.get(keyClass).get(valueClass);
    }
    
    private <T, R> Function<? super T, R> getCastingFunction(Class<T> keyClass, Class<R> returnClass) {
        Class<? super T> currentClass = keyClass;
        while (true) {
            Function<? super T, R> currentCastings = getCastingFunctionOnce(currentClass, returnClass);
            if (currentCastings != null) {
                return currentCastings;
            }
            if (currentClass.equals(Object.class)) {
                return null;
            }
            currentClass = currentClass.getSuperclass();
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T, R> R cast(T toCast, Class<R> returnClass) {
        Class<T> tClass = (Class<T>)toCast.getClass();
        Function<? super T, R> castingFunction = getCastingFunction(tClass, returnClass);
        if (castingFunction == null) {
            return null;
        }
        return castingFunction.apply(toCast);
    }
}