package runecraft.parser;

import runecraft.builtins.RunecraftBuiltins;
import runecraft.builtins.RunecraftPrinterBuiltins;
import runecraft.datastructure.DataHelpers;
import runecraft.error.RunecraftError;
import runecraft.error.RunecraftWarningType;
import runecraft.result.*;
import runecraft.variables.*;

import java.util.*;

public class RunecraftParser {
    private final FunctionCaller call;
    private final RunecraftBuiltins builtins;
    public RunecraftParser(RunecraftBuiltins builtins) {
        this.call = new FunctionCaller(this, new RunecraftTypeCaster());
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
    
    public RunecraftResult<?> parseSet(String tokens, RunecraftMemory memory) {
        Set<Object> setToReturn = new HashSet<>();
        String currentTokens = tokens;
        
        while (true) {
            if (currentTokens.isEmpty()) {
                return new RunecraftErrorResult(
                        RunecraftError.SyntaxError, 
                        "End of set not found", 
                        currentTokens
                );
            }
            if (compareToken(currentTokens, "⳻")) {
                return new RunecraftResult<>(setToReturn, currentTokens.substring("⳻".length()));
            }
            RunecraftResult<?> result = runProgramRecursive(currentTokens, memory);
            if (result instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens, error.remainingTokens());
                return error;
            }
            
            setToReturn.add(result.get());
            currentTokens = result.remainingTokens();
        }
    }
    
    private static RunecraftResult<?> solutionBoxed(String tokens, RunecraftMemory memory) {
        return new RunecraftParser(new RunecraftBuiltins()).runProgramRecursive(tokens, memory.copy());
    }
    
    private static String remainingTokensAfterParsed(String expressionTokens, RunecraftMemory memory) {
        RunecraftResult<?> answer = solutionBoxed(expressionTokens, memory);
        
        if (answer instanceof RunecraftErrorResult) {
            return "";
        }
        return answer.remainingTokens();
        
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
            return call.biFunction(
                    Substance.class,
                    Substance.class, 
                    builtins::combineSubstances, 
                    tokens.substring("🜑".length()),
                    memory
            );
        }
        else if (compareToken(tokens, "🝏")) {
            return call.biFunction(
                    Substance.class,
                    Integer.class,
                    Bolt::new,
                    tokens.substring("🝏".length()),
                    memory
            );
        }
        else if (compareToken(tokens, "🜎")) {
            return call.quadFunction(
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
            return call.function(
                    RunecraftObject.class,
                    builtins::create,
                    tokens.substring("🝧".length()),
                    memory
            );
        }
        
        else if (compareToken(tokens, "🜳")) {
            String leftoverTokens = tokens.substring("🜳".length());
            return call.triFunction(
                    RunecraftObject.class,
                    Integer.class,
                    Integer.class,
                    builtins::yeet, 
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
        else if (compareToken(tokens, "🝰") || compareToken(tokens, "🝯") || compareToken(tokens, ".")) {
            return parseNumber(tokens);
        }
        else if (compareToken(tokens, "⳺")) {
            return parseSet(tokens.substring("⳺".length()), memory);
        }
        else if (compareToken(tokens, "⊢")) {
            return call.biFunction(
                    Integer.class, 
                    Integer.class, 
                    Integer::sum, 
                    tokens.substring("⊢".length()),
                    memory
            );
        }
        else if (compareToken(tokens, "⊣")) {
            return call.biFunction(
                    Integer.class, 
                    Integer.class,
                    (a, b) -> a - b, 
                    tokens.substring("⊣".length()),
                    memory
            );
        }
        else if (compareToken(tokens, "⊤")) {
            return call.biFunction(
                    Integer.class, 
                    Integer.class,
                    (a, b) -> a * b, 
                    tokens.substring("⊤".length()),
                    memory
            );
        }
        else if (compareToken(tokens, "⊥")) {
            return call.biFunction(
                    Integer.class, 
                    Integer.class, 
                    (a, b) -> a / b, 
                    tokens.substring("⊥".length()),
                    memory
            );
        }
        else if (compareToken(tokens, "🝁")) {
            return call.function(
                    Integer.class,
                    builtins::not,
                    tokens.substring("🝁".length()),
                    memory
            );
        }
        else if (compareToken(tokens, ">")) {
            RunecraftResult<?> toAssign = runProgramRecursive(tokens.substring(">".length()), memory);
            
            
            String tokensAfterVarName = "";
            if (toAssign.remainingTokens().length() > 1) {
                tokensAfterVarName = toAssign.remainingTokens().substring(1);
            }
            
            if (toAssign instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens, tokensAfterVarName);
                return error;
            }
            if (toAssign instanceof RunecraftEmptyResult) {
                return new RunecraftErrorResult(
                        RunecraftError.TypeError,
                        "Tried to assign empty result to variable",
                        tokensAfterVarName
                );
            }
            
            if (compareToken(toAssign.remainingTokens(), "🝊")) {
                String remainingTokens = toAssign.remainingTokens().substring("🝊".length());
                if (toAssign.get() instanceof RunecraftObject object) {
                    builtins.assignPointer(object);
                    memory.setPointer(object);
                }
                else {
                    return new RunecraftErrorResult(
                            RunecraftError.TypeError, 
                            "Expected RunecraftObject, got " + RunecraftError.nameFromClass(toAssign.get().getClass()),
                            remainingTokens
                    );
                }
                
                return new RunecraftEmptyResult(remainingTokens);
            }
            
            char varName = toAssign.remainingTokens().charAt(0);
            boolean successfullySet = memory.setVariable(varName, toAssign.get());
            if (!successfullySet) {
                return new RunecraftErrorResult(
                        RunecraftError.VarNameError,
                        "Failed to assign " + toAssign.get() + " to variable " + varName,
                        tokensAfterVarName
                );
            }
            RunecraftResult<?> result = runProgramRecursive(tokensAfterVarName, memory);
            memory.popVariable(varName);
            return result;
            
        }
        else if (compareToken(tokens, "🝊")) {
            return new RunecraftResult<>(memory.getPointer(), tokens.substring("🝊".length()));
        }
        else if (RunecraftMemory.isVarName(tokens.charAt(0))) {
            Object result = memory.getVariable(tokens.charAt(0));
            if (result == null) {
                return new RunecraftErrorResult(
                        RunecraftError.UndefinedVariableError,
                        "Undefined variable " + tokens.charAt(0),
                        tokens.substring(1)
                );
            }
            
            return new RunecraftResult<>(result, tokens.substring(1));
        }
        else if (compareToken(tokens, "🜾")) {
            String remainingTokens = tokens.substring("🜾".length());
            RunecraftResult<?> condition = call.readArgument(Integer.class, remainingTokens, memory);
            if (condition instanceof RunecraftErrorResult error) {
                error.addStackTrace(remainingTokens, error.remainingTokens());
                return error;
            }
            boolean conditionIsTruthy = false;
            if (condition.get() instanceof Integer conditionValue) {
               conditionIsTruthy = builtins.isTruthy(conditionValue);
            }
            
            if (conditionIsTruthy) {
                RunecraftResult<?> result = runProgramRecursive(condition.remainingTokens(), memory);
                String remainingTokensAfterElse = remainingTokensAfterParsed(result.remainingTokens(), memory);
                if (result instanceof RunecraftErrorResult error) {
                    error.addStackTrace(remainingTokens, error.remainingTokens());
                    return error;
                }
                if (result instanceof RunecraftEmptyResult) {
                    return new RunecraftEmptyResult(remainingTokensAfterElse);
                }
                
                if (remainingTokensAfterElse.isEmpty()) {
                    runProgramRecursive(result.remainingTokens(), memory);
                }
                
                return new RunecraftResult<>(result.get(), remainingTokensAfterElse); // TODO ALSO THAT COPY METHOD
            }
            else {
                String remainingTokensAfterIf = remainingTokensAfterParsed(condition.remainingTokens(), memory);
                if (remainingTokensAfterIf.isEmpty()) {
                    return runProgramRecursive(condition.remainingTokens(), memory);
                }
                return runProgramRecursive(remainingTokensAfterIf, memory);
            }
            
        }
        else if (compareToken(tokens, "🝓⧰")) {
            String remainingTokens = tokens.substring("🝓⧰".length());
            RunecraftResult<?> iterable = runProgramRecursive(remainingTokens, memory);
            if (iterable instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens, error.remainingTokens());
            }
            if (!(iterable.get() instanceof Set<?>)) {
                return new RunecraftErrorResult(
                        RunecraftError.TypeError, 
                        "Expected Set, got " + RunecraftError.nameFromClass(iterable.get().getClass()), 
                        remainingTokens
                    );
            }
            
            char varName = iterable.remainingTokens().charAt(0);
            if (!RunecraftMemory.isVarName(varName)) {
                return new RunecraftErrorResult(
                        RunecraftError.TypeError, 
                        "Expected variable name, got " + varName, 
                        iterable.remainingTokens()
                );
            }
            
            Set<?> loopSet = (Set<?>) iterable.get();
            ArrayList<?> loopArrayList = new ArrayList<>(loopSet);
            DataHelpers.shuffle(loopArrayList);
            
            String loopTokens = iterable.remainingTokens().substring(1);
            
            RunecraftResult<?> result = null;
            for (Object loopVariable : loopArrayList) {
                memory.setVariable(varName, loopVariable);
                result = runProgramRecursive(loopTokens, memory);
                if (result instanceof RunecraftErrorResult error) {
                    error.addStackTrace(loopTokens, result.remainingTokens());
                    return error;
                }
                memory.popVariable(varName);
            }
            if (result == null) {
                return new RunecraftErrorResult(
                        RunecraftError.ForLoopNotRunError, 
                        "For loop body was never run",
                        loopTokens
                );
            }
            return new RunecraftEmptyResult(result.remainingTokens());
        }
        else if (compareToken(tokens, "🝓")) {
            String remainingTokens = tokens.substring("🝓".length());
            RunecraftResult<?> startResult = call.readArgument(Integer.class, remainingTokens, memory);
            if (startResult instanceof RunecraftErrorResult error) {
                error.addStackTrace(tokens, error.remainingTokens());
                return error;
            }
            
            char varName = startResult.remainingTokens().charAt(0);
            if (!RunecraftMemory.isVarName(varName)) {
                return new RunecraftErrorResult(
                        RunecraftError.TypeError, 
                        "Expected variable name, got " + varName, 
                        startResult.remainingTokens()
                );
            }
            
            
            RunecraftResult<?> endResult = call.readArgument(
                    Integer.class,
                    startResult.remainingTokens().substring(1),
                    memory
            );
            
            int start = (int)startResult.get();
            int end = (int)endResult.get();
            String internalTokens = endResult.remainingTokens();
            boolean anythingWasRun = false;
            RunecraftResult<?> result = null;
            
            for (int i = start; i <= end; ++i) {
                anythingWasRun = true;
                memory.setVariable(varName, i);
                result = runProgramRecursive(internalTokens, memory);
                memory.popVariable(varName);
                if (result instanceof RunecraftErrorResult error) {
                    error.addStackTrace(internalTokens, error.remainingTokens());
                    return error;
                }
            }
            
            if (anythingWasRun) {
                assert result != null;
                return new RunecraftEmptyResult(result.remainingTokens());
            }
            else {
                return new RunecraftErrorResult(RunecraftError.ForLoopNotRunError, "For loop body was never run", endResult.remainingTokens());
            }
        }
        else {
            return new RunecraftErrorResult(
                    RunecraftError.SyntaxError, 
                    "Unknown character", 
                    ""
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
        // parser.runProgram("🜳🝧🝏🜁🝯🝯🝯.🝰🝯🝰🝯..");
        
        
        // parser.runProgram("🝓🝯ⲙ🝰🝯🝰🝯🜳🝧🝏🜑🜃🜃🝰.🝯.ⲙ");
        // parser.runProgram("🜳🝊");
        // parser.runProgram(">🝏🜂.🝊");
        //
        // parser.runProgram("🜼🜼🜳🝊..>🝏🜂.🝊🜳🝊..");
        // parser.runProgram("🜳🜂..");
        // parser.runProgram("🜳🝧🝏🝊...");
        // RunecraftResult<?> setResult = parser.runProgramRecursive("⳺🜂🜑🜄🜂🝏🜂.🜄🜍♀⳻", new RunecraftMemory());
        // if (setResult.get() instanceof Set<?> set) {
        //     for (Object object : set) {
        //         System.out.println(object);
        //     }
        // }
        
        parser.runProgram("🝓⧰⳺🜂🜄🝧🝏🜑♀🜂🝯🜑🜄🜁⳻Ⲙ🜳Ⲙ🝯..");
        parser.runProgram(">.Ⲁ🝓⧰⳺🜑🜂🜄🜑🜄🜄🜑🜃🜁⳻ⲙ🜼🝧🜎ⲙ🝰🝯🝰🝰🝯🝯.🝯🝯🝯🝯Ⲁ>⊢Ⲁ🝯Ⲁ.");
        parser.runProgram("🜾🝁⊣🝰.🝰🝯🜳🝧🝏🜂...🜳🝧🝏🜄...");
        
        
        // parser.runProgram("🝓🝰🝯ⲓ🝯🝰🝯🜳🝏🜂.🝰🝰🝰🝯.🝰🝯");
        // // parser.runProgram(">⊣🝊🜑🜑♀🜃🜑🜍🜑🜄♀🝊");
        // parser.runProgram("🜳🝧🝏🜑🜑♀🜃🜑🜍🜑🜄♀...");
        
        
        
        
    }
    
    
}
