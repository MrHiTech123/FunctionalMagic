package runecraft.parser;

import runecraft.datastructure.functionalinterface.QuadFunction;
import runecraft.datastructure.functionalinterface.TriFunction;
import runecraft.error.RunecraftError;
import runecraft.error.RunecraftWarningType;
import runecraft.result.*;
import runecraft.variables.Bolt;
import runecraft.variables.RunecraftObject;
import runecraft.variables.Substance;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class RunecraftParser {
    private final FunctionCaller caller;
    public RunecraftParser() {
        this.caller = new FunctionCaller(this);
    }
    
    public boolean compareToken(String tokens, String target) {
        if (tokens.length() < target.length()) return false;
        String firstTokens = tokens.substring(0, target.length());
        return firstTokens.equals(target);
        
    }
    
    abstract void shoot(RunecraftObject objectShot);
    
    
    
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
        else if (compareToken(tokens, ".")) {
            numberParsed = new RunecraftResult<>(0, tokens.substring(".".length()));
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
                    RunecraftError.SyntaxError,
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
            return caller.biFunction(Substance.class, Substance.class, RunecraftBuiltins::combineSubstances, tokens.substring("🜑".length()));
            
        }
            
        else if (compareToken(tokens, "🝏")) {
            return caller.quadFunction(
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
            
            return caller.consumer(RunecraftObject.class, this::shoot, leftoverTokens);
            
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
            return caller.biFunction(Integer.class, Integer.class, Integer::sum, tokens.substring("⊢".length()));
        }
        else {
            return new RunecraftErrorResult(
                    RunecraftError.SyntaxError, 
                    "Unknown character", 
                    tokens.substring(1)
            );
        }
    }
    
    public void runProgram(String tokens) {
        RunecraftResult<?> result = runProgramRecursive(tokens);
        
        if (!result.remainingTokens().isEmpty()) {
            result.addWarning(RunecraftWarningType.TrailingTokensWarning, "Trailing tokens \"" + result.remainingTokens() + "\"");
        }
        System.err.println(result.getWarnings());
        if (result instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, "");
            System.err.println(error.get());
        }
        
    }
    
    
}
