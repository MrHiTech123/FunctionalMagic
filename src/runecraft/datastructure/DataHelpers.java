package runecraft.datastructure;

import runecraft.variables.Substance;

import java.util.Arrays;
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
    
    public static <K, V extends Enum<V>> Map<K, V> mapOfValues(Class<V> values, Predicate<V> predicate, Function<V, K> keyFunction) {
        Map<K, V> toReturn = new HashMap<>();
        for (V currentEnumValue : values.getEnumConstants()) {
            if (predicate.test(currentEnumValue)) {
                toReturn.put(keyFunction.apply(currentEnumValue), currentEnumValue);
            }
        }
        return toReturn;
    }
    
    public static <T> boolean linearSearch(Iterable<T> haystack, T needle) {
        for (T elem : haystack) {
            if (elem.equals(needle)) {
                return true;
            }
        }
        return false;
    }
    
    public static <T> boolean linearSearch(T[] haystack, T needle) {
        return linearSearch(Arrays.asList(haystack), needle);
    }
    
    
    public static boolean linearSearch(char[] haystack, char needle) {
        for (char c : haystack) {
            if (c == needle) {
                return true;
            }
        }
        return false;
    }
}
