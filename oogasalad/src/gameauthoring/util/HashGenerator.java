package gameauthoring.util;

import java.util.Random;
import java.util.Set;



public class HashGenerator {

    private static final int CHARACTER_LIMIT = 255;

    public static Integer newCharacterHash (Set<Integer> tileIDs) {
        int i = getUniqueRandomInteger(tileIDs, CHARACTER_LIMIT);
        return i;
    }

    public static Integer newIntegerHash (Set<Integer> objectIDs) {
        Integer i = getUniqueRandomInteger(objectIDs, Integer.MAX_VALUE);
        return i;
    }

    private static Integer getUniqueRandomInteger (Set<Integer> mySet, Integer upperBound) {
        Random random = new Random();
        int i = random.nextInt(upperBound);
        while (mySet.contains(i)) {
            i = random.nextInt(upperBound);
        }
        return i;
    }

}
