package runecraft.datastructure.pair;

public class Trio<T, U, V> extends Pair<T, U> {
    private final V third;
    
    public Trio(T first, U second, V third) {
        super(first, second);
        this.third = third;
    }
    
    public V getThird() {
        return third;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Trio<?, ?, ?> otherTrio) {
            return super.equals(other) && this.third.equals(otherTrio.third);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() + third.hashCode();
    }
}





