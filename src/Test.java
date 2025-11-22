import runecraft.parser.RunecraftTypeCaster;
import runecraft.result.RunecraftResult;
import runecraft.variables.EverySubstance;
import runecraft.variables.RunecraftMemory;
import runecraft.variables.RunecraftObject;
import runecraft.variables.Substance;

import java.util.function.BiFunction;

class A {
    private int a;
    public A(int a) {
        this.a = a;
    }
    
    @Override
    public String toString() {
        return String.valueOf(a);
    }
}

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
        
        RunecraftTypeCaster caster = new RunecraftTypeCaster();
        
        System.out.println(caster.cast(10, Integer.class));
        System.out.println(caster.cast("Hello world", String.class));
        System.out.println(caster.cast("Hello world", Integer.class));
        System.out.println(caster.cast(10, Number.class));
        System.out.println(caster.cast(Substance.ACID, Substance.class));
        System.out.println(caster.cast(Substance.ACID, EverySubstance.class));
        System.out.println(caster.cast(Substance.ACID, RunecraftObject.class));
        System.out.println(caster.cast(Substance.ACID, Object.class));
        
        // A myA = (A)3;
        // System.out.println(myA);
        
        
        
        
        
        
    }
}
