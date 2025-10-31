package runecraft.parser;

import runecraft.result.*;
import runecraft.variables.Substance;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class RunecraftParser {
    
    
    public boolean compareToken(String tokens, String target) {
        if (tokens.length() < target.length()) return false;
        String firstTokens = tokens.substring(0, target.length());
        return firstTokens.equals(target);
        
    }
    
    abstract void shoot(RunecraftResult<?> objectShot);
    
    
    protected <ArgumentClass, ReturnValueClass> 
      RunecraftResult<?> doFunction(Function<ArgumentClass, ReturnValueClass> function, String tokens) {
        RunecraftResult<?> argument = runProgramRecursive(tokens);
        if (argument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens);
            return error;
        }
        ArgumentClass argumentValue;
        try {
            argumentValue = (ArgumentClass) argument.get();
        }
        catch (ClassCastException e) {
            return new RunecraftErrorResult("Error: Expected other type, got " + argument.get().getClass().getName(), tokens);
        }
        
        ReturnValueClass returnValue = function.apply(argumentValue);
        
        return new RunecraftResult<>(returnValue, argument.remainingTokens());
        
    }
    
    protected <FirstArgumentClass, SecondArgumentClass, ReturnValueClass>
    RunecraftResult<?> doBiFunction(BiFunction<FirstArgumentClass, SecondArgumentClass, ReturnValueClass> function, String tokens) {
        RunecraftResult<?> firstArgument = runProgramRecursive(tokens);
        if (firstArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens);
            return error;
        }
        FirstArgumentClass firstArgumentValue;
        try {
            firstArgumentValue = (FirstArgumentClass) firstArgument.get();
        }
        catch (ClassCastException e) {
            return new RunecraftErrorResult("Error: Expected other type, got " + firstArgument.get().getClass().getName(), tokens);
        }
        
        RunecraftResult<?> secondArgument = runProgramRecursive(firstArgument.remainingTokens());
        if (secondArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens);
            return error;
        }
        
        SecondArgumentClass secondArgumentValue;
        try {
            secondArgumentValue = (SecondArgumentClass) secondArgument.get();
        }
        catch (ClassCastException e) {
            return new RunecraftErrorResult("Error: Expected other type, got " + secondArgument.get().getClass().getName(), tokens);
        }
        
        ReturnValueClass toReturn = function.apply(firstArgumentValue, secondArgumentValue);
        return new RunecraftResult<>(toReturn, secondArgument.remainingTokens());
        
    }
    
    public RunecraftResult<Integer> parseNumber(String tokens) {
        RunecraftResult<Integer> numberParsed;
        if (tokens.isEmpty()) {
            return new RunecraftResult<>(0, "");
        }
        if (compareToken(tokens, "🝰")) {
            numberParsed = parseNumber(tokens.substring("🝰".length()));
        }
        else if (compareToken(tokens, "🝯")) {
            numberParsed = parseNumber(tokens.substring("🝯".length()));
        }
        else if (compareToken(tokens, "|")) {
            numberParsed = new RunecraftResult<>(0, tokens.substring("|".length()));
        }
        else {
            numberParsed = new RunecraftResult<>(0, tokens);
        }
        
        int resultNum = (numberParsed.get() * 2);
        if (compareToken(tokens,"🝯")) {
            ++resultNum;
        }
        return new RunecraftResult<>(resultNum, numberParsed.remainingTokens());
    }
    
    public RunecraftResult<?> runProgramRecursive(String tokens) {
        
        if (tokens.isEmpty()) {
            return new RunecraftErrorResult("Expected expression, found nothing", tokens);
        }
        
        else if (compareToken(tokens, "🜂")) {
            return new RunecraftResult<>(Substance.FIRE, tokens.substring("🜂".length()));
        }
            else if (compareToken(tokens, "🜄")) {
                return new RunecraftResult<>(Substance.WATER, tokens.substring("🜄".length()));
            }
            else if (compareToken(tokens, "🜁")) {
                return new RunecraftResult<>(Substance.AIR, tokens.substring("🜁".length()));
            }
            else if (compareToken(tokens, "🜃")) {
                return new RunecraftResult<>(Substance.EARTH, tokens.substring("🜃".length()));
            }
            else if (compareToken(tokens, "🜍")) {
                return new RunecraftResult<>(Substance.MIND, tokens.substring("🜍".length()));
            }
            else if (compareToken(tokens, "♀")) {
                return new RunecraftResult<>(Substance.FLESH, tokens.substring("♀".length()));
            }
            
        else if (compareToken(tokens, "🜑")) {
            return doBiFunction(Substance::combine, tokens.substring("🜑".length()));
        }
            
        else if (compareToken(tokens, "🝏")) {
            RunecraftResult<?> substanceOfBolt = runProgramRecursive(tokens.substring("🝏".length()));
            if (substanceOfBolt instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens);
                return error;
            }
            return new RunecraftResult<>("(boltOf " + substanceOfBolt.get() + ")", substanceOfBolt.remainingTokens());
        }
        
        else if (compareToken(tokens, "🝭")) {
            String leftoverTokens = tokens.substring("🝭".length());
            
            RunecraftResult<?> objectShot = runProgramRecursive(leftoverTokens);
            if (objectShot instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens);
                return error;
            }
            if (objectShot.get() instanceof String) {
                shoot(objectShot);
            }
            
            return new RunecraftEmptyResult(objectShot.remainingTokens());
            
        }
        else if (compareToken(tokens, "🜼")) {
            String argumentTokens = tokens.substring("🜼".length());
            RunecraftResult<?> firstResult = runProgramRecursive(argumentTokens);
            RunecraftResult<?> secondResult = runProgramRecursive(firstResult.remainingTokens());
            return new RunecraftEmptyResult(secondResult.remainingTokens());
        }
        else if (compareToken(tokens, "🝰") || compareToken(tokens, "🝯")) {
            return parseNumber(tokens);
        }
        else if (compareToken(tokens, "⊢")) {
            System.out.println(tokens);
            RunecraftResult<?> firstAddend = runProgramRecursive(tokens.substring("⊢".length()));
            if (firstAddend instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens);
                return error;
            }
            else if (!(firstAddend.get() instanceof Integer)) {
                return new RunecraftErrorResult(
                        "Error: Expected integer, got " + firstAddend.get().getClass().getName(), 
                        tokens
                );
            }
            RunecraftResult<?> secondAddend = runProgramRecursive(firstAddend.remainingTokens());
            if (secondAddend instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens);
                return error;
            }
            else if (!(secondAddend.get() instanceof Integer)) {
                return new RunecraftErrorResult(
                        "Error: Expected integer, got " + secondAddend.get().getClass().getName(), 
                        tokens
                );
            }
            return new RunecraftResult<>((int)firstAddend.get() + (int)secondAddend.get(), secondAddend.remainingTokens());
        }
        else {
            return new RunecraftErrorResult("Error: Unknown character", tokens);
        }
    }
    
    public void runProgram(String tokens) {
        RunecraftResult<?> result = runProgramRecursive(tokens);
        if (result instanceof RunecraftErrorResult error) {
            System.out.println(error.get());
        }
        
    }
    
    
}
