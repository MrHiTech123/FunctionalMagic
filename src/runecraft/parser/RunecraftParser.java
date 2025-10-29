package runecraft.parser;

import runecraft.result.*;
import runecraft.variables.Substance;

public abstract class RunecraftParser {
    
    
    public boolean compareToken(String tokens, String target) {
        String firstTokens = tokens.substring(0, target.length());
        return firstTokens.equals(target);
        
    }
    
    abstract void shoot(RunecraftResult<?> objectShot);
    
    // protected <FirstArgumentClass, SecondArgumentClass, ReturnValueClass> 
    //   RunecraftResult<?> doBiFunction(String tokens) {
    //     RunecraftResult<?> firstArgument = parse(tokens);
    //     if (firstArgument instanceof RunecraftErrorResult) {
    //         return firstArgument;
    //     }
    //     FirstArgumentClass firstArgumentInner;
    //     try {
    //         firstArgumentInner = (FirstArgumentClass) firstArgument.get();
    //     }
    //     catch (ClassCastException e) {
    //         return new RunecraftErrorResult("Error: Expected " + );
    //     }
    //    
    //    
    // }
    
    
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
    
    public RunecraftResult<?> runProgram(String tokens) {
        if (tokens.isEmpty()) {
            return new RunecraftErrorResult("Expected expression, found nothing", tokens);
        }
        RunecraftResult<?> result;
        
        if (compareToken(tokens, "🜂")) {
            result = new RunecraftResult<>(Substance.FIRE, tokens.substring("🜂".length()));
        }
            else if (compareToken(tokens, "🜄")) {
                result = new RunecraftResult<>(Substance.WATER, tokens.substring("🜂".length()));
            }
            else if (compareToken(tokens, "🜁")) {
                result = new RunecraftResult<>(Substance.AIR, tokens.substring("🜂".length()));
            }
            else if (compareToken(tokens, "🜃")) {
                result = new RunecraftResult<>(Substance.EARTH, tokens.substring("🜂".length()));
            }
            else if (compareToken(tokens, "🜍")) {
                result = new RunecraftResult<>(Substance.MIND, tokens.substring("🜂".length()));
            }
            else if (compareToken(tokens, "♀")) {
                result = new RunecraftResult<>(Substance.FLESH, tokens.substring("🜂".length()));
            }
            
        else if (compareToken(tokens, "🜑")) {
            RunecraftResult<?> firstResultParsed = runProgram(tokens.substring("🜑".length()));
            if (firstResultParsed instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens);
                return error;
            }
            else if (!(firstResultParsed.get() instanceof Substance)) {
                return new RunecraftErrorResult(
                        "Error: Expected substance, found " + firstResultParsed.get().getClass().getName(), 
                        tokens
                );
            }
            RunecraftResult<Substance> firstResult = (RunecraftResult<Substance>)firstResultParsed;
            
            RunecraftResult<?> secondResultParsed = runProgram(firstResult.remainingTokens());
            if (secondResultParsed instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens);
                return error;
            }
            else if (!(secondResultParsed.get() instanceof Substance)) {
                return new RunecraftErrorResult(
                        "Error: Expected Substance, got " + secondResultParsed.get().getClass().getName(),
                        tokens
                );
            }
            RunecraftResult<Substance> secondResult = (RunecraftResult<Substance>) secondResultParsed;
            
            Substance resultSubstance = Substance.combine(firstResult.get(), secondResult.get());
            return new RunecraftResult<>(resultSubstance, secondResult.remainingTokens());
        }
            
        else if (compareToken(tokens, "🝏")) {
            RunecraftResult<?> substanceOfBolt = runProgram(tokens.substring("🝏".length()));
            return new RunecraftResult<>("(boltOf " + substanceOfBolt.get() + ")", substanceOfBolt.remainingTokens());
        }
        
        else if (compareToken(tokens, "🝭")) {
            String leftoverTokens = tokens.substring("🝭".length());
            
            RunecraftResult<?> objectShot = runProgram(leftoverTokens);
            if (objectShot.get() instanceof String) {
                shoot(objectShot);
            }
            
            result = new RunecraftEmptyResult(objectShot.remainingTokens());
            
        }
        else if (compareToken(tokens, "🜼")) {
            String argumentTokens = tokens.substring("🜼".length());
            RunecraftResult<?> firstResult = runProgram(argumentTokens);
            RunecraftResult<?> secondResult = runProgram(firstResult.remainingTokens());
            result = new RunecraftEmptyResult(secondResult.remainingTokens());
        }
        else if (compareToken(tokens, "🝰") || compareToken(tokens, "🝯")) {
            result = parseNumber(tokens);
        }
        else if (compareToken(tokens, "⊢")) {
            System.out.println(tokens);
            RunecraftResult<?> firstAddend = runProgram(tokens.substring("⊢".length()));
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
            RunecraftResult<?> secondAddend = runProgram(firstAddend.remainingTokens());
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
            result = new RunecraftResult<>((int)firstAddend.get() + (int)secondAddend.get(), secondAddend.remainingTokens());
        }
        else {
            return new RunecraftErrorResult("Error: Unknown character", tokens);
        }
        return result;
    }
    
    
}
