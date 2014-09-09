package globalforces;

import java.util.HashMap;

public class GlobalForce {
    private HashMap<String, GlobalForce> myGlobalForceMap = new HashMap<String, GlobalForce>();

    public HashMap<String, GlobalForce> getMap() {
        return myGlobalForceMap;
    }
    
    public void addForce(String type, GlobalForce force){
        myGlobalForceMap.put(type, force);
    }
    
    public GlobalForce getForce(String type) {
        return myGlobalForceMap.get(type);
    }
}
