package runecraft.datastructure.pair;

public class Quartet<T, U, V, W> extends Trio<T, U, V> {
    private final W fourth;
    
    public Quartet(T first, U second, V third, W fourth) {
        super(first, second, third);
        this.fourth = fourth;
    }
    
    public W getFourth() {
        return fourth;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Quartet<?, ?, ?, ?> otherQuartet) {
            return super.equals(other) && this.fourth.equals(otherQuartet.fourth);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() + fourth.hashCode();
    }
}

