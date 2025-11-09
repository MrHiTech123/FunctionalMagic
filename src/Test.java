import runecraft.datastructure.Pair;
import runecraft.result.RunecraftEmptyResult;
import runecraft.result.RunecraftResult;
import runecraft.variables.RunecraftMemory;
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
        
        RunecraftMemory memory = new RunecraftMemory();
        
        System.out.println(memory.setVariable('ⲁ', new RunecraftResult<>("Small letter!", "")));
        System.out.println(memory.setVariable('Ⲁ', new RunecraftResult<>("Big letter!", "")));
        
        System.out.println(memory.getVariable('ⲁ'));
        System.out.println(memory.getVariable('Ⲁ'));
        
        System.out.println(memory.popVariable('ⲁ'));
        
        // System.out.println(memory.getVariable('ⲁ').get());
        System.out.println(memory.getVariable('Ⲁ'));
        
        System.out.println(memory.popVariable('Ⲁ'));
        
        // System.out.println(memory.getVariable('ⲁ').get());
        System.out.println(memory.getVariable('Ⲁ'));
        
        
        
        
        
        
        
    }
}
