package runecraft.parser;

import runecraft.datastructure.functionalinterface.QuadFunction;
import runecraft.datastructure.functionalinterface.TriFunction;
import runecraft.error.RunecraftError;
import runecraft.result.RunecraftEmptyResult;
import runecraft.result.RunecraftErrorResult;
import runecraft.result.RunecraftResult;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionCaller {
    private final RunecraftParser parser;
    
    public FunctionCaller(RunecraftParser parser) {
        this.parser = parser;
    }
    
        private <T> String nameFromClass(Class<T> clazz) {
        String fullName = clazz.getName();
        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }
    
    protected <ArgumentClass>
    RunecraftResult<?> readArgument(Class<ArgumentClass> argumentClassClass, String tokens) {
        RunecraftResult<?> result = parser.runProgramRecursive(tokens);
        if (result instanceof RunecraftErrorResult error) {
            return error;
        }
        if (argumentClassClass.isInstance(result.get())) {
            return result;
        }
        else {
            return new RunecraftErrorResult(
                    RunecraftError.TypeError,
                    "Expected " + nameFromClass(argumentClassClass) + ", got " + nameFromClass(result.get().getClass()),
                    result.remainingTokens()
            );
        }
        
    }
    
    protected <ArgumentClass> 
    RunecraftResult<?> function(
            Class<ArgumentClass> argumentClassClass, 
            Function<ArgumentClass, ?> function, 
            String tokens,
            boolean addTokensToInternalErrorStacktrace
    ) {
        RunecraftResult<?> argument = readArgument(argumentClassClass, tokens);
        if (argument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, argument.remainingTokens());
            return error;
        }
        Object result = function.apply(argumentClassClass.cast(argument.get()));
        
        if (result instanceof RunecraftErrorResult error) {
            if (addTokensToInternalErrorStacktrace) {
                error.addStackTrace(tokens, error.remainingTokens());
            }
            return error;
        }
        else if (result instanceof RunecraftResult<?> runecraftResult) {
            return new RunecraftResult<>(runecraftResult.get(), argument.remainingTokens());
        }
        
        return new RunecraftResult<>(result, argument.remainingTokens());
        
    }
    
    protected <ArgumentClass> RunecraftResult<?> function(
            Class<ArgumentClass> argumentClassClass, 
            Function<ArgumentClass, ?> function, 
            String tokens
    ) {
        return function(argumentClassClass, function, tokens, true);
    }
    
    protected <FirstArgumentClass, SecondArgumentClass>
    RunecraftResult<?> biFunction(
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
        
        return function(
                secondArgumentClassClass,
                (secondArgument) -> 
                        function.apply(firstArgumentClassClass.cast(firstArgument.get()), secondArgument),
                firstArgument.remainingTokens(),
                false
        );
        
    }
    
    protected <FirstArgumentClass, SecondArgumentClass, ThirdArgumentClass>
    RunecraftResult<?> triFunction(
            Class<FirstArgumentClass> firstArgumentClassClass,
            Class<SecondArgumentClass> secondArgumentClassClass,
            Class<ThirdArgumentClass> thirdArgumentClassClass,
            TriFunction<FirstArgumentClass, SecondArgumentClass, ThirdArgumentClass, ?> function,
            String tokens
    ) {
        RunecraftResult<?> firstArgument = readArgument(firstArgumentClassClass, tokens);
        if (firstArgument instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, firstArgument.remainingTokens());
            return error;
        }
        
        return biFunction(
                secondArgumentClassClass,
                thirdArgumentClassClass,
                (secondArgument, thirdArgument) -> 
                        function.apply(firstArgumentClassClass.cast(firstArgument.get()), secondArgument, thirdArgument),
                firstArgument.remainingTokens()
        );
    }
    
    protected <FirstArgumentClass, SecondArgumentClass, ThirdArgumentClass, FourthArgumentClass>
    RunecraftResult<?> quadFunction(
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
        
        return triFunction(
                secondArgumentClassClass,
                thirdArgumentClassClass,
                fourthArgumentClassClass,
                (secondArgument, thirdArgument, fourthArgument) ->
                        function.apply(firstArgumentClassClass.cast(firstArgument.get()), secondArgument, thirdArgument, fourthArgument),
                firstArgument.remainingTokens()
        );
        
    }
    
    protected  <ArgumentClass>
    RunecraftResult<?> consumer(
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
    RunecraftResult<?> biConsumer(
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
        
        
        return consumer(
                secondArgumentClassClass,
                (secondArgument) ->
                        consumer.accept(firstArgumentClassClass.cast(firstArgument), secondArgument),
                firstArgument.remainingTokens()
        );
        
    }
}
