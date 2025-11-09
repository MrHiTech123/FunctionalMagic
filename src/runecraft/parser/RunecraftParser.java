package runecraft.parser;

import runecraft.builtins.RunecraftBuiltins;
import runecraft.builtins.RunecraftPrinterBuiltins;
import runecraft.error.RunecraftError;
import runecraft.error.RunecraftWarningType;
import runecraft.result.*;
import runecraft.variables.*;

public class RunecraftParser {
    private final FunctionCaller caller;
    private final RunecraftBuiltins builtins;
    public RunecraftParser(RunecraftBuiltins builtins) {
        this.caller = new FunctionCaller(this);
        this.builtins = builtins;
    }
    
    public boolean compareToken(String tokens, String target) {
        if (tokens.length() < target.length()) return false;
        String firstTokens = tokens.substring(0, target.length());
        return firstTokens.equals(target);
        
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
    
    public RunecraftResult<?> runProgramRecursive(String tokens, RunecraftMemory memory) {
        
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
            return caller.biFunction(
                    Substance.class,
                    Substance.class, 
                    builtins::combineSubstances, 
                    tokens.substring("🜑".length()),
                    memory
            );
        }
        else if (compareToken(tokens, "🝏")) {
            return caller.quadFunction(
                    Substance.class,
                    Integer.class,
                    Integer.class,
                    Integer.class,
                    Bolt::new,
                    tokens.substring("🝏".length()),
                    memory
            );
        }
        else if (compareToken(tokens, "🜎")) {
            return caller.quadFunction(
                    Substance.class,
                    Integer.class,
                    Integer.class,
                    Integer.class,
                    Cone::new,
                    tokens.substring("🜎".length()),
                    memory
            );
        }
        else if (compareToken(tokens, "🝧")) {
            return caller.function(
                    RunecraftObject.class,
                    builtins::create,
                    tokens.substring("🝧".length()),
                    memory
            );
        }
        
        else if (compareToken(tokens, "🝭")) {
            String leftoverTokens = tokens.substring("🝭".length());
            return caller.function(
                    RunecraftObject.class, 
                    builtins::shoot, 
                    leftoverTokens, 
                    memory
            );
            
        }
        else if (compareToken(tokens, "🜼")) {
            String argumentTokens = tokens.substring("🜼".length());
            RunecraftResult<?> firstResult = runProgramRecursive(argumentTokens, memory);
            if (firstResult instanceof RunecraftErrorResult error) {
                error.addStackTrace(argumentTokens, firstResult.remainingTokens());
                return error;
            }
            RunecraftResult<?> secondResult = runProgramRecursive(firstResult.remainingTokens(), memory);
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
            return caller.biFunction(
                    Integer.class, 
                    Integer.class, 
                    Integer::sum, 
                    tokens.substring("⊢".length()),
                    memory
            );
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
        RunecraftResult<?> result = runProgramRecursive(tokens, new RunecraftMemory());
        
        if (!result.remainingTokens().isEmpty()) {
            result.addWarning(RunecraftWarningType.TrailingTokensWarning, "Trailing tokens \"" + result.remainingTokens() + "\"");
        }
        System.err.print(result.getWarnings());
        if (result instanceof RunecraftErrorResult error) {
            error.addStackTrace(tokens, "");
            System.err.println(error.get());
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        RunecraftParser parser = new RunecraftParser(new RunecraftPrinterBuiltins());
        // parser.runProgram("🝭🝏🜂");
        // parser.runProgram("🝏🜂🝯.🝯.🝯");
        // parser.runProgram("🝭🝏🜑🜂🜂");
        // parser.runProgram("🝭🝏🜑🝯🝰🝯🜂");
        // parser.runProgram("⊢🝯🝰🝯🝰🝯🜂");
        // parser.runProgram("🜼🝭🝏🜑🜂🜄🝭🝏🜑🜄🜂");
        
        parser.runProgram("🝭🝧🝏🜑🜄🜂🝯.🝯🝰🝯.🝰");
        parser.runProgram("🝧🜎🜑🜄♀🝯🝰🝯.🝯🝯🝯🝯.🝰");
        
        parser.runProgram("🝭🝏🜂🝯.🝰🝯🝰🝯.🝰");
        
        
    }
    
    
}
