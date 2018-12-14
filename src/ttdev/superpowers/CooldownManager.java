package ttdev.superpowers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ttdev.superpowers.api.power.Power;

public class CooldownManager {

    private static Map<UUID, Map<Power, Map<Long, Long>>> cooldownMap = new HashMap<>();
    
    public static boolean contains(UUID playerId, Power power) {
    	
    	Map<Power, Map<Long, Long>> waitingPowers = cooldownMap.get(playerId);
    	Map<Long, Long> delay = waitingPowers.get(power);
    	
    	for (Long l : delay.keySet()) {
    		Long time = l;
    		Long seconds = delay.get(time);
    		if (System.currentTimeMillis() > (time + (seconds * 1000))) {
    			cooldownMap.remove(playerId);
    			return false;
    		}
    	}
    	return true;
    }

    public static void add(UUID playerId, Power power, long seconds) {
    	Map<Long, Long> delay = new HashMap<>();
    	delay.put(System.currentTimeMillis(), seconds);
    	Map<Power, Map<Long, Long>> waitingPowers = new HashMap<>();
    	waitingPowers.put(power, delay);
    	cooldownMap.put(playerId, waitingPowers);
    }

    public static Map<Power, Map<Long, Long>> remove(UUID playerId) {
        return cooldownMap.remove(playerId);
    }

}
