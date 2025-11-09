package runecraft.variables;

import runecraft.result.RunecraftResult;

import java.util.*;

public class RunecraftMemory {
    private static String GLOBAL_LETTERS = "ⳎⲰⲈⲢⲦⲨⳘⲒⲞⲠⲀⲊⲆⲜⲄⲎⳢⲔⲖⲌⲬⲤⳔⲂⲚⲘ";
    private static String LOCAL_LETTERS = "ⳏⲱⲉⲣⲧⲩⳙⲓⲟⲡⲁⲋⲇⲝⲅⲏⳣⲕⲗⲍⲭⲥⳕⲃⲛⲙ";
    
    
    private Map<String, RunecraftResult<?>> GLOBALS = new HashMap<>();
    private Map<String, Queue<?>> LOCALS = new HashMap<>();
    
    public static void main(String[] args) {
        Queue<Integer> objects = Collections.asLifoQueue(new LinkedList<>());
        System.out.println(GLOBAL_LETTERS.length());
    }
    
}
