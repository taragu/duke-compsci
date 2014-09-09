package gameengine.tests;


import gameengine.gamedata.MapsAndTilesManager;

public class TestParsingMap {

    @org.junit.Test
    public void test_createPropertyObjects () {
        
        
//        MapsAndTilesFactory.init(("src/gameengine/tests/TestParsingMap.json"));//!!! IMPORTANT:
//        MapsAndTilesFactory.createMaps();
        MapsAndTilesManager manager = new MapsAndTilesManager("src/gameengine/tests/TestParsingMap.json");
        manager.createMaps();
        
        
    }
    
    
//    @org.junit.Test
//    public void test_toSymbolRep() {
//        String uniqueID = "531739002";
//        String name = "map 1";
//        String line = "2068566921 2068566921 2068566921 2068566921 2068566921 \n"+
//                "1474357177 1474357177 1474357177 1474357177 1474357177";
//        List<GameTile> tiles = new ArrayList<GameTile>();
//        tiles.add(new GameTile("1474357177", "testMedia", true));
//        tiles.add(new GameTile("2068566921", "testMedia", true));
//        GameMap map = new GameMap(uniqueID, name, line);
//        map.addTile(tiles.get(0));
//        map.addTile(tiles.get(1));
//        String[] symbolRep = map.toSymbolRepresentation();
//        ErrorLogger.writeToLog("TEST: symbolRep line 1 is " +symbolRep[0], "info");
//        
//        
//    }
    
    
}
