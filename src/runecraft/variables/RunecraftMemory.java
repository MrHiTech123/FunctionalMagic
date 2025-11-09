package runecraft.variables;

import runecraft.datastructure.DataHelpers;
import runecraft.result.RunecraftResult;

import java.util.*;

public class RunecraftMemory {
    private static final char[] GLOBAL_LETTERS = "ⳎⲰⲈⲢⲦⲨⳘⲒⲞⲠⲀⲊⲆⲜⲄⲎⳢⲔⲖⲌⲬⲤⳔⲂⲚⲘ".toCharArray();
    private static final char[] LOCAL_LETTERS = "ⳏⲱⲉⲣⲧⲩⳙⲓⲟⲡⲁⲋⲇⲝⲅⲏⳣⲕⲗⲍⲭⲥⳕⲃⲛⲙ".toCharArray();
    
    private Map<Character, Object> GLOBALS = new HashMap<>();
    private Map<Character, Queue<Object>> LOCALS = new HashMap<>();
    
    private static Queue<Object> lifoQueue() {
        return Collections.asLifoQueue(new LinkedList<>());
    }
    
    public RunecraftMemory() {
        for (char c : LOCAL_LETTERS) {
            LOCALS.put(c, lifoQueue());
        }
    }
    
    private static boolean isGlobalVar(char var) {
        return DataHelpers.linearSearch(GLOBAL_LETTERS, var);
    }
    
    private static boolean isLocalVar(char var) {
        return DataHelpers.linearSearch(LOCAL_LETTERS, var);
    }
    
    public static boolean isVarName(char name) {
        return isGlobalVar(name) || isLocalVar(name);
    }
    
    private void setGlobalVar(char variable, Object value) {
        GLOBALS.put(variable, value);
    }
    
    private void setLocalVar(char variable, Object value) {
        LOCALS.get(variable).add(value);
    }
    
    public boolean setVariable(char variable, Object value) {
        if (isGlobalVar(variable)) {
            setGlobalVar(variable, value);
            return true;
        }
        if (isLocalVar(variable)) {
            setLocalVar(variable, value);
            return true;
        }
        return false;
    }
    
    
    public Object getVariable(char variable) {
        if (isGlobalVar(variable)) {
            return GLOBALS.getOrDefault(variable, null);
        }
        else if (isLocalVar(variable)) {
            Queue<?> variableStack = LOCALS.get(variable);
            if (variableStack.isEmpty()) return null;
            return variableStack.peek();
        }
        return null;
    }
    
    public boolean popVariable(char variable) {
        if (!isLocalVar(variable)) return false;
        
        Queue<Object> variableStack = LOCALS.get(variable);
        if (variableStack.isEmpty()) return false;
        
        variableStack.remove();
        return true;
        
    }
    
    
    public static void main(String[] args) {
        Queue<Integer> objects = Collections.asLifoQueue(new LinkedList<>());
        System.out.println(GLOBAL_LETTERS);
        System.out.println(isGlobalVar('Ⲁ'));
        System.out.println(isGlobalVar('ⲁ'));
        System.out.println(isLocalVar('Ⲁ'));
        System.out.println(isLocalVar('ⲁ'));
        
    }
    
}
