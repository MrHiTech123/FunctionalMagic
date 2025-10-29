package runecraft.parser;

import runecraft.result.RunecraftResult;

public class RunecraftPrinterParser extends RunecraftParser {
    public void shoot(RunecraftResult<?> result) {
        System.out.println("(shoot " + result.get() + ")");
    }
    
    public static void main(String[] args) {
        RunecraftPrinterParser parser = new RunecraftPrinterParser();
        // parser.parse("🜼🜼🝭🜂🝭🝏🜂🝭🝏🝏🜂");
        // RunecraftResult<?> result = parser.parse("🝰🝯🝰🝯");
        // System.out.println(result.get());
        // RunecraftResult<?> testParsingMultipleNumsLineDivider = parser.parse("🝯🝰🝯|🝰🝯");
        // RunecraftResult<?> testParsingMultipleNumsDotDivider = parser.parse("🝯🝰🝯.🝰🝯");
        // System.out.println(testParsingMultipleNumsLineDivider.get());
        // System.out.println(testParsingMultipleNumsLineDivider.remainingTokens());
        // System.out.println(testParsingMultipleNumsDotDivider.get());
        // System.out.println(testParsingMultipleNumsDotDivider.remainingTokens());
        //
        // RunecraftResult<?> resultAddSuccess = parser.parse("⊢🝯🝰🝯⊢🝯🝰🝯⊢🝯🝰🝯|🜂🝰🝯");
        // //RunecraftResult<?> resultAddFailure = parser.parse("⊢🝯🝰🝯⊢🝯🝰🝯⊢🝯🝰🝯|🜂🝰🝯");
        // System.out.println(resultAddSuccess.get());
        // // System.out.println(resultAddFailure.get());
        parser.runProgram("🝭🝏🜑🜃♀");
        parser.runProgram("🝭🝏🜑🜂🜂");
        
        
    }
}
