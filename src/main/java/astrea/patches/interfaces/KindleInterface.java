package astrea.patches.interfaces;

public interface KindleInterface {
    default boolean canKindle(){
        return false;
    }
}
