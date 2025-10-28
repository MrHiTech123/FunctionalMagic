package runecraft.result;

public class RunecraftResult<T> {
    protected final T result;
    protected final String remainingTokens;
    
    public RunecraftResult(T result, String remainingTokens) {
        this.result = result;
        this.remainingTokens = remainingTokens;
    }
    public T get() {
        return result;
    }
    public String remainingTokens() {
        return remainingTokens;
    }
}
