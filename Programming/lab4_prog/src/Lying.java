public interface Lying {
    default void lie(String plate){
        System.out.printf("%s находится на %s\n", toString(), plate);
    }
}
