package runecraft.parser;

import runecraft.result.*;

public abstract class RunecraftParser {
    
    
    public boolean compareToken(String tokens, String target) {
        String firstTokens = tokens.substring(0, target.length());
        return firstTokens.equals(target);
        
    }
    
    abstract void shoot(RunecraftObjectResult objectShot);
    
    public RunecraftIntResult parseNumber(String tokens) {
        RunecraftResult numberParsed;
        if (tokens.isEmpty()) {
            return new RunecraftIntResult(0, "");
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
            numberParsed = new RunecraftIntResult(0, tokens);
        }
        
        int resultNum = (numberParsed.resultInt() * 2);
        if (compareToken(tokens,"🝯")) {
            ++resultNum;
        }
        return new RunecraftIntResult(resultNum, numberParsed.remainingTokens());
    }
    
    
    public RunecraftResult parse(String tokens) {
        if (tokens.isEmpty()) {
            return new RunecraftErrorResult("Expected expression, found nothing");
        }
        RunecraftResult result;
        if (compareToken(tokens, "🜂")) {
            result = new RunecraftObjectResult("Fire", tokens.substring("🜂".length()));
        }
        
        else if (compareToken(tokens, "🝏")) {
            RunecraftResult substanceOfBolt = parse(tokens.substring("🝏".length()));
            return new RunecraftObjectResult("(boltOf " + substanceOfBolt.resultString() + ")", substanceOfBolt.remainingTokens());
        }
        
        else if (compareToken(tokens, "🝭")) {
            String leftoverTokens = tokens.substring("🝭".length());
            
            RunecraftResult objectShot = parse(leftoverTokens);
            if (objectShot instanceof RunecraftObjectResult runecraftObjectResult) {
                shoot(runecraftObjectResult);
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
