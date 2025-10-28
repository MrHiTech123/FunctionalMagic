package runecraft.parser;

import runecraft.result.*;

public abstract class RunecraftParser {
    
    
    public boolean compareToken(String tokens, String target) {
        String firstTokens = tokens.substring(0, target.length());
        return firstTokens.equals(target);
        
    }
    
    abstract void shoot(RunecraftResult<?> objectShot);
    
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
        // else if (compareToken(tokens, ".")) {
        //     numberParsed = new RunecraftIntResult(0, tokens.substring(".".length()));
        // }
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
            return new RunecraftErrorResult("Expected expression, found nothing");
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
            RunecraftResult firstResult = parse(argumentTokens);
            RunecraftResult secondResult = parse(firstResult.remainingTokens());
            result = new RunecraftEmptyResult(secondResult.remainingTokens());
        }
        else if (compareToken(tokens, "🝰") || compareToken(tokens, "🝯")) {
            result = parseNumber(tokens);
        }
        else {
            result = new RunecraftErrorResult("Error: Unknown character");
        }
        if (result instanceof RunecraftErrorResult) {
            System.out.println("Error");
        }
        return result;
    }
    
    
}
