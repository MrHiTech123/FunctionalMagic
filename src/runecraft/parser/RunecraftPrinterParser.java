package runecraft.parser;

import runecraft.result.RunecraftResult;
import runecraft.variables.RunecraftObject;

public class RunecraftPrinterParser extends RunecraftParser {
    
    @Override
    public void shoot(RunecraftObject objectShot) {
        System.out.println("(shoot " + objectShot + ")");
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
        parser.runProgram("🝭🝏🜂🝰.🝰.🝰");
        parser.runProgram("🝭🝏🜑🜄🜂🝯.🝯🝰🝯.🝯🝯");
        parser.runProgram("🝭🝏🜑🜂🜂");
        parser.runProgram("🝭🝏🜑🝯🝰🝯🜂");
        parser.runProgram("⊢🝯🝰🝯🝰🝯🜂");
        parser.runProgram("🜼🝭🝏🜑🜂🜄🝭🝏🜑🜄🜂");
        
        
    }
}
