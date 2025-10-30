import runecraft.datastructure.Pair;
import runecraft.variables.Substance;

import java.util.function.BiFunction;

public class Test {
    public static void main(String[] args) {
        // for (Substance s : Substance.values()) {
        //     System.out.println(s.name());
        // }
        
        System.out.println(Substance.combine(Substance.FLESH, Substance.EARTH));
        System.out.println(Substance.combine(Substance.WATER, Substance.FIRE));
        BiFunction<Integer, Integer, String> concat = (i, j) -> i + " " + j;
        System.out.println((concat.getClass()));
    }
}
