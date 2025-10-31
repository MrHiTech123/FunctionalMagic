package runecraft.parser;

import runecraft.datastructure.functionalinterface.QuadFunction;
import runecraft.error.RunecraftErrorType;
import runecraft.result.*;
import runecraft.variables.Bolt;
import runecraft.variables.RunecraftObject;
import runecraft.variables.Substance;

import java.beans.PropertyEditorSupport;
import java.util.function.BiConsumer;
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
            return new RunecraftErrorResult(
                    RunecraftErrorType.TypeError,
                    "Expected " + nameFromClass(argumentClassClass) + ", got " + nameFromClass(result.get().getClass()),
                    result.remainingTokens()
            );
        }
        
    }
    
    protected <ArgumentClass> 
    RunecraftResult<?> doFunction(Class<ArgumentClass> argumentClassClass, Function<ArgumentClass, ?> function, String tokens) {
        RunecraftResult<?> argument = readArgument(argumentClassClass, tokens);
        if (argument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, argument.remainingTokens());
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
            error.addStackTrace(tokens, firstArgument.remainingTokens());
            return error;
        }
        FirstArgumentClass firstArgumentValue = firstArgumentClassClass.cast(firstArgument.get());
        
        RunecraftResult<?> secondArgument = readArgument(secondArgumentClassClass, firstArgument.remainingTokens());
        if (secondArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, secondArgument.remainingTokens());
            return error;
        }
        
        SecondArgumentClass secondArgumentValue = secondArgumentClassClass.cast(secondArgument.get());
        
        Object toReturn = function.apply(firstArgumentValue, secondArgumentValue);
        return new RunecraftResult<>(toReturn, secondArgument.remainingTokens());
        
    }
    
    protected <FirstArgumentClass, SecondArgumentClass, ThirdArgumentClass, FourthArgumentClass>
    RunecraftResult<?> doQuadFunction(
            Class<FirstArgumentClass> firstArgumentClassClass,
            Class<SecondArgumentClass> secondArgumentClassClass,
            Class<ThirdArgumentClass> thirdArgumentClassClass,
            Class<FourthArgumentClass> fourthArgumentClassClass,
            QuadFunction<FirstArgumentClass, SecondArgumentClass, ThirdArgumentClass, FourthArgumentClass, ?> function,
            String tokens
    ) {
        RunecraftResult<?> firstArgument = readArgument(firstArgumentClassClass, tokens);
        if (firstArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, firstArgument.remainingTokens());
            return error;
        }
        FirstArgumentClass firstArgumentValue = firstArgumentClassClass.cast(firstArgument.get());
        
        RunecraftResult<?> secondArgument = readArgument(secondArgumentClassClass, firstArgument.remainingTokens());
        if (secondArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, secondArgument.remainingTokens());
            return error;
        }
        
        SecondArgumentClass secondArgumentValue = secondArgumentClassClass.cast(secondArgument.get());
        
        RunecraftResult<?> thirdArgument = readArgument(thirdArgumentClassClass, secondArgument.remainingTokens());
        if (thirdArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, thirdArgument.remainingTokens());
            return error;
        }
        ThirdArgumentClass thirdArgumentValue = thirdArgumentClassClass.cast(thirdArgument.get());
        
        RunecraftResult<?> fourthArgument = readArgument(fourthArgumentClassClass, thirdArgument.remainingTokens());
        if (fourthArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, fourthArgument.remainingTokens());
            return error;
        }
        
        FourthArgumentClass fourthArgumentValue = fourthArgumentClassClass.cast(fourthArgument.get());
        
        Object toReturn = function.apply(firstArgumentValue, secondArgumentValue, thirdArgumentValue, fourthArgumentValue);
        return new RunecraftResult<>(toReturn, fourthArgument.remainingTokens());
        
    }
    
    protected  <ArgumentClass>
    RunecraftResult<?> doConsumer(
            Class<ArgumentClass> argumentClassClass, 
            Consumer<ArgumentClass> consumer, 
            String tokens
    ) {
        RunecraftResult<?> argument = readArgument(argumentClassClass, tokens);
        if (argument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, argument.remainingTokens());
            return error;
        }
        
        ArgumentClass argumentValue = argumentClassClass.cast(argument.get());
        consumer.accept(argumentValue);
        return new RunecraftEmptyResult(argument.remainingTokens());
    }
    
    protected <FirstArgumentClass, SecondArgumentClass>
    RunecraftResult<?> doBiConsumer(
            Class<FirstArgumentClass> firstArgumentClassClass,
            Class<SecondArgumentClass> secondArgumentClassClass,
            BiConsumer<FirstArgumentClass, SecondArgumentClass> consumer,
            String tokens
    ) {
        RunecraftResult<?> firstArgument = readArgument(firstArgumentClassClass, tokens);
        if (firstArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, firstArgument.remainingTokens());
            return error;
        }
        FirstArgumentClass firstArgumentValue = firstArgumentClassClass.cast(firstArgument.get());
        
        RunecraftResult<?> secondArgument = readArgument(secondArgumentClassClass, firstArgument.remainingTokens());
        if (secondArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, secondArgument.remainingTokens());
            return error;
        }
        
        SecondArgumentClass secondArgumentValue = secondArgumentClassClass.cast(secondArgument.get());
        
        consumer.accept(firstArgumentValue, secondArgumentValue);
        return new RunecraftEmptyResult(secondArgument.remainingTokens());
        
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
            return new RunecraftErrorResult(
                    RunecraftErrorType.SyntaxError,
                    "Expected expression, found nothing", 
                    ""
            );
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
                return new RunecraftErrorResult(
                        RunecraftErrorType.RecipeError,
                        firstArg.get() + " cannot be combined with " + secondArg.get(),
                        secondArg.remainingTokens());
            }
            return result;
            
        }
            
        else if (compareToken(tokens, "🝏")) {
            return doQuadFunction(
                    Substance.class,
                    Integer.class,
                    Integer.class,
                    Integer.class,
                    Bolt::new,
                    tokens.substring("🝏".length())
            );
        }
        
        else if (compareToken(tokens, "🝭")) {
            String leftoverTokens = tokens.substring("🝭".length());
            
            return doConsumer(RunecraftObject.class, this::shoot, leftoverTokens);
            
        }
        else if (compareToken(tokens, "🜼")) {
            String argumentTokens = tokens.substring("🜼".length());
            RunecraftResult<?> firstResult = runProgramRecursive(argumentTokens);
            if (firstResult instanceof RunecraftErrorResult error) {
                error.addStackTrace(argumentTokens, firstResult.remainingTokens());
                return error;
            }
            RunecraftResult<?> secondResult = runProgramRecursive(firstResult.remainingTokens());
            if (secondResult instanceof RunecraftErrorResult error) {
                error.addStackTrace(firstResult.remainingTokens(), secondResult.remainingTokens());
                return error;
            }
            return new RunecraftEmptyResult(secondResult.remainingTokens());
        }
        else if (compareToken(tokens, "🝰") || compareToken(tokens, "🝯")) {
            return parseNumber(tokens);
        }
        else if (compareToken(tokens, "⊢")) {
            return doBiFunction(Integer.class, Integer.class, Integer::sum, tokens.substring("⊢".length()));
        }
        else {
            return new RunecraftErrorResult(
                    RunecraftErrorType.SyntaxError, 
                    "Unknown character", 
                    tokens.substring(1)
            );
        }
    }
    
    public void runProgram(String tokens) {
        RunecraftResult<?> result = runProgramRecursive(tokens);
        if (result instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, "");
            System.out.println(error.get());
        }
        
    }
    
    
}
