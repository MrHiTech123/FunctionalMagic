package runecraft;

import org.w3c.dom.ls.LSOutput;
import runecraft.encoding.EncodingSetter;
import runecraft.result.RunecraftEmptyResult;
import runecraft.result.RunecraftErrorResult;
import runecraft.result.RunecraftObjectResult;
import runecraft.result.RunecraftResult;
import runecraft.string.UnicodeString;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RunecraftParser {
    
    
    public static boolean compareToken(String tokens, String target) {
        String firstTokens = tokens.substring(0, target.length());
        return firstTokens.equals(target);
        
    }
    
    public static void shoot(String object) {
        System.out.println("(shoot " + object + ")");
    }
    
    
    
    public static RunecraftResult parse(String tokens) {
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

            if (leftoverTokens.isEmpty()) {
                return new RunecraftErrorResult("Error: Expected Object, found nothing");
            }
            RunecraftResult objectShot = parse(leftoverTokens);
            if (objectShot instanceof RunecraftObjectResult) {
                shoot(objectShot.resultString());
            }
            
            result = new RunecraftEmptyResult(objectShot.remainingTokens());
            
        }
        else if (compareToken(tokens, "🜼")) {
            String argumentTokens = tokens.substring("🜼".length());
            RunecraftResult firstResult = parse(argumentTokens);
            RunecraftResult secondResult = parse(firstResult.remainingTokens());
            result = new RunecraftEmptyResult(secondResult.remainingTokens());
        }
        else {
            result = new RunecraftErrorResult("Error: Unknown character");
        }
        if (result instanceof RunecraftErrorResult) {
            System.out.println("Error");
        }
        return result;
    }
    
    
    
    public static void main(String[] args) {
        parse("🜼🜼🝭🜂🝭🝏🜂🝭🝏🝏🜂");
    }
}
