package runecraft.parser;

import runecraft.result.RunecraftErrorResult;
import runecraft.result.RunecraftObjectResult;
import runecraft.result.RunecraftResult;

public class RunecraftPrinterParser extends RunecraftParser {
    public void shoot(RunecraftObjectResult result) {
        System.out.println("(shoot " + result.resultString() + ")");
    }
    
    public static void main(String[] args) {
        RunecraftPrinterParser parser = new RunecraftPrinterParser();
        // parser.parse("🜼🜼🝭🜂🝭🝏🜂🝭🝏🝏🜂");
        RunecraftResult result = parser.parse("🝰🝯🝰🝯");
        System.out.println(result.resultInt());
    }
}
