package gameauthoring.test;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;

import gameauthoring.util.HashGenerator;


public class TestHashGenerator {

    private HashGenerator myHashGenerator = new HashGenerator();

    @org.junit.Test
    public void testnewCharacterHash () {

        Set<Integer> tileIDs = new HashSet<Integer>();
        tileIDs.add(123);
        int i = myHashGenerator.newCharacterHash(tileIDs);
        String id = Character.toString((char) i);

        assertTrue(id.length() == 1);
        assertFalse(tileIDs.contains(id));
    }

    @org.junit.Test
    public void testnewIntegerHash () {
        Set<Integer> objectIDs = new HashSet<Integer>();
        objectIDs.add(1);
        Integer id = myHashGenerator.newIntegerHash(objectIDs);

        assertTrue(id < 999999);
        assertFalse(objectIDs.contains(id));
    }

}
