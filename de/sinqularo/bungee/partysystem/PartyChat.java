package de.sinqularo.bungee.partysystem;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PartyChat extends Command {

    public PartyChat(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings.length == 0) {
            ProxiedPlayer pp = (ProxiedPlayer) commandSender;
            pp.sendMessage(PartyData.PARTY_PREFIX + "§7/p <Nachticht>");
            return;
        }
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;
        if(PartyManager.getParty(pp) == null) {
            pp.sendMessage(PartyData.YOU_ARE_NOT_IN_A_PARTY);
            return;
        }
        PartyData party = PartyManager.getParty(pp);
        String msg = "";
        String[] arrayOfString = strings;
        int j = arrayOfString.length;
        int i = 0;
        while (i < j) {
            String s = arrayOfString[i];
            msg = String.valueOf(msg) + "§7 " + s;
            i++;
        }
        for(ProxiedPlayer members : party.getPlayers()) {
            members.sendMessage(PartyData.PARTY_PREFIX + "§a" + pp.getName() + "§8» §7" + msg);
        }
        party.getLeader().sendMessage(PartyData.PARTY_PREFIX + "§a" + pp.getName() + "§8» §7" + msg);
    }
}
