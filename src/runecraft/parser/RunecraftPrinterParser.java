package runecraft.parser;

import runecraft.result.RunecraftResult;

public class RunecraftPrinterParser extends RunecraftParser {
    public void shoot(RunecraftResult<?> result) {
        System.out.println("(shoot " + result.get() + ")");
    }
    
    public static void main(String[] args) {
        RunecraftPrinterParser parser = new RunecraftPrinterParser();
        parser.parse("🜼🜼🝭🜂🝭🝏🜂🝭🝏🝏🜂");
        RunecraftResult<?> result = parser.parse("🝰🝯🝰🝯");
        System.out.println(result.get());
    }
}
