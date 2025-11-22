package runecraft.datastructure.explicittypecaster;

import java.util.*;
import java.util.function.Function;

// Not me actually using invariants for once
public class ExplicitTypeCaster {
    private static final Map<Class<?>, Map<Class<?>, Function<?, ?>>> typeCastings = new HashMap<>();
    
    /**
     * @param castings: A series of trios. Each trio takes the following form:
     *                 (Class A, Class B, (Function A -> B) F)
     *                Each trio indicates that when an object of class A (or a subclass of A)
     *                is cast to type B (or a superclass of B), this should be done by running the function F
     *                on the object. <b>Order matters.</b> If you want to specify a superclass to be cast to
     *                differently from one of its subclasses, put that superclass' trio <b>after</b> the subclass'
     */
    public ExplicitTypeCaster(ClassClassFunctionTrio<?, ?>... castings) {
        for (ClassClassFunctionTrio<?, ?> trio : castings) {
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
    
    private static <T> List<Class<? super T>> lineage(Class<T> clazz) {
        List<Class<? super T>> toReturn = new LinkedList<>();
        Class<? super T> currentSuper = clazz;
        while (true) {
            toReturn.add(currentSuper);
            if (currentSuper.equals(Object.class)) {
                return toReturn;
            }
            currentSuper = currentSuper.getSuperclass();
        }
    }
    
    @SuppressWarnings("unchecked")
    private <T, R> Function<T, R> getCastingFunctionOnce(Class<T> keyClass, Class<R> valueClass) {
        if (lineage(keyClass).contains(valueClass)) return (a -> (R)a);
        if (!typeCastings.containsKey(keyClass)) return null;
        if (!typeCastings.get(keyClass).containsKey(valueClass)) return null;
        return (Function<T, R>) typeCastings.get(keyClass).get(valueClass);
    }
    
    private <T, R> Function<? super T, R> getCastingFunction(Class<T> keyClass, Class<R> returnClass) {
        for (Class<? super T> currentClass : lineage(keyClass)) {
            Function<? super T, R> currentCastings = getCastingFunctionOnce(currentClass, returnClass);
            if (currentCastings != null) {
                return currentCastings;
            }
        }
        return null;
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