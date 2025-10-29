package runecraft.datastructure;

import runecraft.variables.Substance;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class DataHelpers {
    public static <K extends Enum<K>, V> Map<K, V> mapOfKeys(Class<K> keys, Predicate<K> predicate, Function<K, V> valueFunction) {
        Map<K, V> toReturn = new HashMap<>();
        for (K currentEnumValue : keys.getEnumConstants()) {
            if (predicate.test(currentEnumValue)) {
                toReturn.put(currentEnumValue, valueFunction.apply(currentEnumValue));
            }
        }
        return toReturn;
    }
    
    public static <K, V extends Enum<V>> Map<K, V> mapOfValues(Class<V> keys, Predicate<V> predicate, Function<V, K> keyFunction) {
        Map<K, V> toReturn = new HashMap<>();
        for (V currentEnumValue : keys.getEnumConstants()) {
            if (predicate.test(currentEnumValue)) {
                toReturn.put(keyFunction.apply(currentEnumValue), currentEnumValue);
            }
        }
        return toReturn;
    }

}
