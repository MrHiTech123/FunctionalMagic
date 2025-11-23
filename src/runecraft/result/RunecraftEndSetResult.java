package runecraft.result;

import runecraft.error.RunecraftWarning;

import java.util.List;

public class RunecraftEndSetResult extends RunecraftResult<String> {
    public RunecraftEndSetResult(String result, String remainingTokens, List<RunecraftWarning> warnings) {
        super(result, remainingTokens, warnings);
    }
}
