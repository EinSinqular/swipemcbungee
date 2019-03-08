package de.sinqularo.bungee.partysystem;

import de.sinqularo.bungee.BungeeCordSystem;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.TimeUnit;

public class PartyCommand extends Command {

    public PartyCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;
        if(strings.length == 0) {
            pp.sendMessage("§8| §5PartySystem Befehle §8| ");
            pp.sendMessage("§a/Party invite §7- Lade Leute in deine Party ein!");
            pp.sendMessage("§a/Party accept §7- Aktzeptiere eine Anfrage");
            pp.sendMessage("§a/Party deny §7- Lehne eine Anfrage ab");
            pp.sendMessage("§a/Party kick §7- Kicke einen Spieler aus der Party");
            pp.sendMessage("§a/Party leave §7- Verlasse eine Party");
            pp.sendMessage("§a/Party list §7- Liste alle Party-Mitglieder auf");
            pp.sendMessage("§a/p §7- Schreibe in dem Party Chat");
            pp.sendMessage("§8| §5PartySystem Befehle §8| ");
        } else if(strings[0].equalsIgnoreCase("list")) {
            if(PartyManager.getParty(pp) == null) {
                pp.sendMessage(PartyData.YOU_ARE_NOT_IN_A_PARTY);
                return;
            }
            PartyData party = PartyManager.getParty(pp);
            String leader = "§c" + party.getLeader();
            String player = "§7";

            int partySize = party.getPlayers().size() + 1;
            if(!party.getPlayers().isEmpty()) {
                for(ProxiedPlayer all : party.getPlayers()) {
                    player = String.valueOf(player) + all.getName() + "§7, §7";
                }
                player = player.substring(1, player.lastIndexOf("§7, §7"));
            } else {
                player = String.valueOf(player) + " §8Keine";
            }
            pp.sendMessage("§7------§8[§5Deine Party §8» §7 " + partySize + "§8«§8]§7------");
            pp.sendMessage("§7Party Leader: §c" + party.getLeader().getName());
            pp.sendMessage("§7Online auf §a" + pp.getServer().getInfo().getName());
            pp.sendMessage("§7------§8[§5Deine Party §8» §7 " + partySize + "§8«§8]§7------");
        } else if(strings[0].equalsIgnoreCase("leave")) {
            if (PartyManager.getParty(pp) == null) {
                pp.sendMessage(PartyData.YOU_ARE_NOT_IN_A_PARTY);
                return;
            }
            PartyData party = PartyManager.getParty(pp);
            if (party.isLeader(pp)) {
                for(ProxiedPlayer all : party.getPlayers()) {
                    all.sendMessage(PartyData.PARTY_PREFIX + "§7Der Partybesitzer hat die Party verlassen, die Party wurde aufgelöst§7.");
                }
                PartyManager.deleteParty(pp);
            } else {
                party.removePlayer(pp);
                for(ProxiedPlayer all : party.getPlayers()) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§a" + pp.getName() + " §7hat die Party verlassen§7.");
                }
                party.getLeader().sendMessage(PartyData.PARTY_PREFIX + "§a" + pp.getName() + " §7hat die Party verlassen§7.");
            }
        } else if(strings[0].equalsIgnoreCase("kick")) {
            try {
                if(PartyManager.getParty(pp) == null) {
                    pp.sendMessage(PartyData.YOU_ARE_NOT_IN_A_PARTY);
                    return;
                }
                PartyData party = PartyManager.getParty(pp);
                if(!party.isLeader(pp)) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§7Du bist nicht der Besitzer der Party§7.");
                    return;
                }
                ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(strings[1]);
                if(pp == pl) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§7Du kannst dich nicht selber kicken!");
                    return;
                }
                if(pl == null) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§7Der Spieler ist offline§7.");
                    return;
                }
                if(!party.isInParty(pl)) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§7Der Spieler ist nicht in der Party§7.");
                    return;
                }
                if(party.removePlayer(pl)) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§a" + strings[1] + " §7hat die Party verlassen§7.");
                    for(ProxiedPlayer all : party.getPlayers()) {
                        all.sendMessage(PartyData.PARTY_PREFIX + "§7Der Spieler ist nicht in der Party§7.");
                    }
                    start(pp);
                }
            } catch (Exception ex) {
                pp.sendMessage(PartyData.PARTY_PREFIX + "§7Bitte gebe einen Spieler an§7.");
            }
        } else if(strings[0].equalsIgnoreCase("invite")) {
               try {
                   ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(strings[1]);
                   if(pp == pl) {
                       pp.sendMessage(PartyData.PARTY_PREFIX + "§7Du kannst dich nicht selber einladen!");
                       return;
                   }
                   if(pl == null) {
                       pp.sendMessage(PartyData.PARTY_PREFIX + "§7Der Spieler ist offline§7.");
                       return;
                   }
                   if(pl.equals(pp.getName())) {
                       pp.sendMessage(PartyData.PARTY_PREFIX + "§7Du kannst dich nicht selber einladen§7.");
                       return;
                   }
                   PartyData party;
                   if((PartyManager.getParty(pp) != null) && (!(party = PartyManager.getParty(pp)).isLeader(pp))) {
                       pp.sendMessage(PartyData.PARTY_PREFIX + "§7Du bist nicht der Partybesitzer§7.");
                   }
                   if((PartyManager.getParty(pp) != null) && ((party = PartyManager.getParty(pp)).hasRequest(pl))) {
                       pp.equals(PartyData.PARTY_PREFIX + "§7Du hast diesen Spieler bereits in deine Party eingeladen§7.");
                       return;
                   }
                   if(PartyManager.getParty(pl) != null) {
                       pp.sendMessage(PartyData.PARTY_PREFIX + "§7Der Spieler ist bereits in einer anderen Party§7.");
                       return;
                   }
                   if(PartyManager.getParty(pp) != null) {
                       PartyData partyData = PartyManager.getParty(pp);
                       if(partyData.isInParty(pl)) {
                           pp.sendMessage(PartyData.PARTY_PREFIX + "§7Der Spieler ist bereits in deiner Party§7.");
                           return;
                       }
                       pp.sendMessage(PartyData.PARTY_PREFIX + "§7Du hast §a" + strings[1] + " §7in deine Party eingeladen§7.");
                       partyData.invitePlayer(pl);
                   } else {
                       PartyManager.createParty(pp);
                       pp.sendMessage(PartyData.PARTY_PREFIX + "§7Du hast §a" + strings[1] + " §7in deine Party eingeladen§7.");
                       PartyData partyData = PartyManager.getParty(pp);
                       partyData.invitePlayer(pl);
                   }
               } catch (Exception ex) {
                   pp.sendMessage(PartyData.PARTY_PREFIX + "§7Bitte gebe einen Spieler an§7.");
               }
        } else if(strings[0].equalsIgnoreCase("deny")) {
            try {
                if(PartyManager.getParty(pp) != null) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§cDu bist bereits in in einer Party§7.");
                    return;
                }
                ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(strings[1]);
                if(pl == null) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§7Der Spieler ist offline§7.");
                    return;
                }
                if(PartyManager.getParty(pl) == null) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§a" + strings[1] + " §7hat keine Party§7.");
                    return;
                }
                PartyData party = PartyManager.getParty(pl);
                if(!party.hasRequest(pp)) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§7Du wurdest nicht in diese Party eingeladen§7.");
                    return;
                }
                party.removeInvite(pp);
                pp.sendMessage(PartyData.PARTY_PREFIX + "§7Du hast die Anfrage von §a" + party.getLeader() + " §cabgelehnt§7.");
                pl.sendMessage(PartyData.PARTY_PREFIX + "§a" + pp.getName() + " §7hat die Anfrage §cabgelehnt§7.");
            } catch (Exception ex) {
                pp.sendMessage(PartyData.PARTY_PREFIX + "§7Bitte gebe einen Spieler an§7.");
            }
        } else if(strings[0].equalsIgnoreCase("accept")) {
            try {
                if(PartyManager.getParty(pp) != null) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§cDu bist bereits in in einer Party§7.");
                    return;
                }
                ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(strings[1]);

                if(pl == null) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§7Der Spieler ist offline§7.");
                    return;
                }
                if(PartyManager.getParty(pl) == null) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§a" + strings[1] + " §7hat keine Party§7.");
                    return;
                }
                PartyData party = PartyManager.getParty(pl);
                if(!party.hasRequest(pp)) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§7Du wurdest nicht in diese Party eingeladen§7.");
                    return;
                }
                if(party.isInParty(pp)) {
                    pp.sendMessage(PartyData.PARTY_PREFIX + "§7Du bist bereits in der Party.");
                    return;
                }
                if(party.addPlayer(pp)) {
                    for(ProxiedPlayer all : party.getPlayers()) {
                        all.sendMessage(PartyData.PARTY_PREFIX + "§a" + pp.getName() + " §7ist der Party beigetreten§7.");
                    }
                    party.getLeader().sendMessage(PartyData.PARTY_PREFIX + "§a" + pp.getName() + " §7ist der Party beigetreten§7.");
                    party.removeInvite(pp);
                }
            } catch (Exception ex) {
                pp.sendMessage(PartyData.PARTY_PREFIX + "§7Bitte gebe einen Spieler an§7.");
            }
        }
    }
    public void start(final ProxiedPlayer pp) {
        BungeeCord.getInstance().getScheduler().schedule(BungeeCordSystem.getBungeeCordSystem(), ()->{
            PartyData party = PartyManager.getParty(pp);
            if((party != null) && (party.getPlayers().size() == 0)) {
                PartyManager.deleteParty(pp);
                party.getLeader().sendMessage(PartyData.PARTY_PREFIX + "§7Die Party wurde aufgrund zu weniger Mitglieder aufgelöst§7.");
                pp.sendMessage(PartyData.PARTY_PREFIX + "§cDie Party wurde aufgelöst§7.");
            }
        },5L, TimeUnit.MINUTES);
    }
}
