package runecraft.datastructure.pair;

public class Quintet<T, U, V, W, X> extends Quartet<T, U, V, W> {
    private final X fifth;
    
    public Quintet(T first, U second, V third, W fourth, X fifth) {
        super(first, second, third, fourth);
        this.fifth = fifth;
    }
    
    public X getFifth() {
        return fifth;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Quintet<?,?,?,?,?> otherQuintet) {
            return super.equals(other) && fifth.equals(otherQuintet.fifth);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() + fifth.hashCode();
    }
}
