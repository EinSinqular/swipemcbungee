package de.sinqularo.bungee.partysystem.listener;

import de.sinqularo.bungee.BungeeCordSystem;
import de.sinqularo.bungee.partysystem.PartyData;
import de.sinqularo.bungee.partysystem.PartyManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class PartyConnection implements Listener {


    @EventHandler
    public void onSwitch(ServerSwitchEvent e) {
        ProxiedPlayer pp = e.getPlayer();
        final PartyData party;
        if((PartyManager.getParty(pp) != null) && ((party = PartyManager.getParty(pp)).isLeader(pp))) {
            if((party.getLeader().getServer().getInfo().getName().contains("Lobby")) || (party.getLeader().getServer().getInfo().getName().contains("SilentHub")) || (party.getLeader().getServer().getInfo().getName().contains("PremiumLobby"))) {
                return;
            }
            party.getLeader().sendMessage(PartyData.PARTY_PREFIX + "§7Die Party betritt den Server §e§l" + party.getLeader().getServer().getInfo().getName());
            for(final ProxiedPlayer partyplayer : party.getPlayers()) {
                partyplayer.sendMessage(PartyData.PARTY_PREFIX + "§7Die Party betritt den Server §e§l" + party.getLeader().getServer().getInfo().getName());
                BungeeCord.getInstance().getScheduler().schedule(BungeeCordSystem.getBungeeCordSystem(), ()->{
                    partyplayer.connect(party.getServer());
                }, 2L, TimeUnit.SECONDS);
            }
        }
    }
    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {
        ProxiedPlayer pp = e.getPlayer();
        if(PartyManager.getParty(pp) != null) {
            PartyData party = PartyManager.getParty(pp);
            if(party.isLeader(pp)) {
                for(ProxiedPlayer partyplayer : party.getPlayers()) {
                    partyplayer.sendMessage(PartyData.PARTY_PREFIX + "§7Der Partybesitzer hat die Party verlassen, die Party wurde aufgelöst§7.");
                }
                PartyManager.deleteParty(pp);
            } else {
                party.removePlayer(pp);
                for(ProxiedPlayer partyplayer : party.getPlayers()) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§c" + pp.getName() + "§7hat die Party §cverlassen§7.");
                }
                start(pp);
            }
        }
    }
    public void start(final ProxiedPlayer pp) {
        BungeeCord.getInstance().getScheduler().schedule(BungeeCordSystem.getBungeeCordSystem(), ()->{
            PartyData party = PartyManager.getParty(pp);
            if((party != null) && (party.getPlayers().size() == 0)) {
                PartyManager.deleteParty(pp);
                party.getLeader().sendMessage(PartyData.PARTY_PREFIX + "§7Die Party wurde aufgrund zu weniger Mitglieder aufgelöst§7.");
                pp.sendMessage(PartyData.PARTY_PREFIX + "§cDie Party wurde aufgelöst");
            }
        },5L, TimeUnit.MINUTES);
    }

}
