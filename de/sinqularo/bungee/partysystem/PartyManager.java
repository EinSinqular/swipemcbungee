package de.sinqularo.bungee.partysystem;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class PartyManager {

    public static List<PartyData> partys = new ArrayList<>();

    public static PartyData getParty(ProxiedPlayer pp) {
        for(PartyData party : partys) {
            if(party.isInParty(pp)) {
                return party;
            }
        }
        return null;
    }
    public static boolean createParty(ProxiedPlayer pp) {
        if(getParty(pp) == null) {
            partys.add(new PartyData(pp));
            return true;
        }
        return false;
    }
    public static boolean deleteParty(ProxiedPlayer p) {
        if(getParty(p) != null) {
            if(getParty(p).isLeader(p)) {
                for(ProxiedPlayer pp : getParty(p).getPlayers()) {
                    getParty(p).removePlayer(pp);
                }
                partys.remove(getParty(p));
                return true;
            }
            return false;
        }
        return false;
    }

    public static List<PartyData> getPartys() {
        return partys;
    }
}
