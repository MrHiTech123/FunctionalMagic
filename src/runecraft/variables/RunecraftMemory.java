package runecraft.variables;

import runecraft.result.RunecraftResult;

import java.util.*;

public class RunecraftMemory {
    private static String[]
    
    private Map<String, RunecraftResult<?>> GLOBALS = new HashMap<>();
    private Map<String, Queue<?>> LOCALS = new HashMap<>();
    
    public static void main(String[] args) {
        Queue<Integer> objects = Collections.asLifoQueue(new LinkedList<>());
    }
    
}
