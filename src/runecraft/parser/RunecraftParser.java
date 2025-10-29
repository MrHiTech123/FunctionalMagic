package runecraft.parser;

import runecraft.result.*;

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
    
    
    public RunecraftResult<?> parse(String tokens) {
        if (tokens.isEmpty()) {
            return new RunecraftErrorResult("Expected expression, found nothing", tokens);
        }
        RunecraftResult<?> result;
        if (compareToken(tokens, "🜂")) {
            result = new RunecraftResult<>("Fire", tokens.substring("🜂".length()));
        }
        
        else if (compareToken(tokens, "🝏")) {
            RunecraftResult<?> substanceOfBolt = parse(tokens.substring("🝏".length()));
            return new RunecraftResult<>("(boltOf " + substanceOfBolt.get() + ")", substanceOfBolt.remainingTokens());
        }
        
        else if (compareToken(tokens, "🝭")) {
            String leftoverTokens = tokens.substring("🝭".length());
            
            RunecraftResult<?> objectShot = parse(leftoverTokens);
            if (objectShot.get() instanceof String) {
                shoot(objectShot);
            }
            
            result = new RunecraftEmptyResult(objectShot.remainingTokens());
            
        }
        else if (compareToken(tokens, "🜼")) {
            String argumentTokens = tokens.substring("🜼".length());
            RunecraftResult<?> firstResult = parse(argumentTokens);
            RunecraftResult<?> secondResult = parse(firstResult.remainingTokens());
            result = new RunecraftEmptyResult(secondResult.remainingTokens());
        }
        else if (compareToken(tokens, "🝰") || compareToken(tokens, "🝯")) {
            result = parseNumber(tokens);
        }
        else if (compareToken(tokens, "⊢")) {
            System.out.println(tokens);
            RunecraftResult<?> firstAddend = parse(tokens.substring("⊢".length()));
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
            RunecraftResult<?> secondAddend = parse(firstAddend.remainingTokens());
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
