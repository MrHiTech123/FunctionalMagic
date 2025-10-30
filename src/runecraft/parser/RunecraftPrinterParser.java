package runecraft.parser;

import runecraft.result.RunecraftResult;

public class RunecraftPrinterParser extends RunecraftParser {
    public void shoot(RunecraftResult<?> result) {
        System.out.println("(shoot " + result.get() + ")");
    }
    
    public static void main(String[] args) {
        RunecraftPrinterParser parser = new RunecraftPrinterParser();
        //
        // RunecraftResult<?> resultAddSuccess = parser.parse("⊢🝯🝰🝯⊢🝯🝰🝯⊢🝯🝰🝯|🜂🝰🝯");
        // //RunecraftResult<?> resultAddFailure = parser.parse("⊢🝯🝰🝯⊢🝯🝰🝯⊢🝯🝰🝯|🜂🝰🝯");
        // System.out.println(resultAddSuccess.get());
        // // System.out.println(resultAddFailure.get());
        // RunecraftResult<?> result = parser.runProgramRecursive("🝰🝯🝰🝯");
        // System.out.println(result.get());
        parser.runProgram("🝭🝏🜂");
        parser.runProgram("🝭🝏🜑🜄🜂");
        parser.runProgram("🝭🝏🜑🜂🜂");
        
        
    }
}
