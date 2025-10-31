package runecraft.parser;

import runecraft.result.*;
import runecraft.variables.Bolt;
import runecraft.variables.RunecraftObject;
import runecraft.variables.Substance;

import java.beans.PropertyEditorSupport;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class RunecraftParser {
    
    
    public boolean compareToken(String tokens, String target) {
        if (tokens.length() < target.length()) return false;
        String firstTokens = tokens.substring(0, target.length());
        return firstTokens.equals(target);
        
    }
    
    abstract void shoot(RunecraftObject objectShot);
    
    
    private <T> String nameFromClass(Class<T> clazz) {
        String fullName = clazz.getName();
        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }
    
    protected <ArgumentClass>
    RunecraftResult<?> readArgument(Class<ArgumentClass> argumentClassClass, String tokens) {
        RunecraftResult<?> result = runProgramRecursive(tokens);
        if (result instanceof RunecraftErrorResult error) {
            return error;
        }
        if (argumentClassClass.isInstance(result.get())) {
            return result;
        }
        else {
            return new RunecraftErrorResult("Error: Expected " + nameFromClass(argumentClassClass) + ", got " + nameFromClass(result.get().getClass()));
        }
        
    }
    
    protected <ArgumentClass> 
    RunecraftResult<?> doFunction(Class<ArgumentClass> argumentClassClass, Function<ArgumentClass, ?> function, String tokens) {
        RunecraftResult<?> argument = readArgument(argumentClassClass, tokens);
        if (argument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens);
            return error;
        }
        Object result = function.apply(argumentClassClass.cast(argument.get()));
        
        return new RunecraftResult<>(result, argument.remainingTokens());
        
    }
    
    protected <FirstArgumentClass, SecondArgumentClass>
    RunecraftResult<?> doBiFunction(
            Class<FirstArgumentClass> firstArgumentClassClass,
            Class<SecondArgumentClass> secondArgumentClassClass,
            BiFunction<FirstArgumentClass, SecondArgumentClass, ?> function,
            String tokens
    ) {
        RunecraftResult<?> firstArgument = readArgument(firstArgumentClassClass, tokens);
        if (firstArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens);
            return error;
        }
        FirstArgumentClass firstArgumentValue = firstArgumentClassClass.cast(firstArgument.get());
        
        RunecraftResult<?> secondArgument = readArgument(secondArgumentClassClass, firstArgument.remainingTokens());
        if (secondArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens);
            return error;
        }
        
        SecondArgumentClass secondArgumentValue = secondArgumentClassClass.cast(secondArgument.get());
        
        Object toReturn = function.apply(firstArgumentValue, secondArgumentValue);
        return new RunecraftResult<>(toReturn, secondArgument.remainingTokens());
        
    }
    
    protected  <ArgumentClass>
    RunecraftResult<?> doConsumer(
            Class<ArgumentClass> argumentClassClass, 
            Consumer<ArgumentClass> consumer, 
            String tokens
    ) {
        RunecraftResult<?> argument = readArgument(argumentClassClass, tokens);
        if (argument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens);
            return error;
        }
        
        ArgumentClass argumentValue = argumentClassClass.cast(argument.get());
        consumer.accept(argumentValue);
        return new RunecraftEmptyResult(argument.remainingTokens());
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
            RunecraftResult<?> result = doBiFunction(Substance.class, Substance.class, Substance::combine, tokens.substring("🜑".length()));
            if (result.get() == null) {
                RunecraftResult<?> firstArg = runProgramRecursive(tokens.substring("🜑".length()));
                RunecraftResult<?> secondArg = runProgramRecursive(firstArg.remainingTokens());
                return new RunecraftErrorResult("Error: " + firstArg.get() + " cannot be combined with " + secondArg.get());
            }
            return result;
            
        }
            
        else if (compareToken(tokens, "🝏")) {
            RunecraftResult<?> result = doFunction(Substance.class, Bolt::new, tokens.substring("🝏".length()));
            return result;
        }
        
        else if (compareToken(tokens, "🝭")) {
            String leftoverTokens = tokens.substring("🝭".length());
            
            RunecraftResult<?> result = doConsumer(RunecraftObject.class, this::shoot, leftoverTokens);
            if (result instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens);
                return error;
            }
            
            return result;
            
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
            RunecraftResult<?> result = doBiFunction(Integer.class, Integer.class, Integer::sum, tokens.substring("⊢".length()));
            if (result instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens);
                return error;
            }
            return result;
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
