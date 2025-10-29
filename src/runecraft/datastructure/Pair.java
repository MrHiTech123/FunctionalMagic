package runecraft.datastructure;

public class Pair<T, U> {
    private T first;
    private U second;
    
    
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
    
    public T getFirst() {
        return first;
    }
    
    public U getSecond() {
        return second;
    }
    
    public boolean equals(Object other) {
        if (other instanceof Pair<?, ?> otherPair) {
            return first.equals(otherPair.getFirst()) && second.equals(otherPair.getSecond());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }
}
