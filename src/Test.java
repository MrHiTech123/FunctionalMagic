import runecraft.datastructure.Pair;
import runecraft.variables.Substance;

public class Test {
    public static void main(String[] args) {
        // for (Substance s : Substance.values()) {
        //     System.out.println(s.name());
        // }
        
        System.out.println(Substance.combine(Substance.WATER, Substance.FLESH));
        System.out.println(Substance.combine(Substance.WATER, Substance.FIRE));
        
    }
}
