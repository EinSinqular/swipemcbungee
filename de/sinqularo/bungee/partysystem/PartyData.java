package de.sinqularo.bungee.partysystem;

import de.sinqularo.bungee.BungeeCordSystem;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PartyData {

    public static String PARTY_PREFIX = "§8» §5PartySystem §8• §7";
    public static String YOU_ARE_NOT_IN_A_PARTY = PARTY_PREFIX + "§7Du bist in keiner Party!";

    private ProxiedPlayer leader;
    private List<ProxiedPlayer> invitations;
    private List<ProxiedPlayer> players;

    public PartyData(ProxiedPlayer leader) {
        this.leader = leader;
        this.players = new ArrayList<>();
        this.invitations = new ArrayList<>();
    }
    public boolean isLeader(ProxiedPlayer pp) {
        if(this.leader == pp) {
            return true;

        }
        return false;
    }

    public List<ProxiedPlayer> getPlayers() {
        return players;
    }

    public ProxiedPlayer getLeader() {
        return leader;
    }

    public List<ProxiedPlayer> getInvitations() {
        return invitations;
    }
    public boolean hasRequest(ProxiedPlayer pp) {
        if(this.invitations.contains(pp)) {
            return true;
        }
        return false;
    }
    public boolean isInParty(ProxiedPlayer pp) {
        if(isLeader(pp)) {
            return true;
        } if(this.players.contains(pp)) {
            return true;
        }
        return false;
    }
    public boolean addPlayer(ProxiedPlayer pp) {
        if((!this.players.contains(pp)) && (this.invitations.contains(pp))) {
            this.players.add(pp);
            this.invitations.remove(pp);
            return true;
        }
        return false;
    }
    public boolean removePlayer(ProxiedPlayer pp) {
        if(this.players.contains(pp)) {
            this.players.remove(pp);
            return true;
        }
        return false;
    }
    public void removeInvite(ProxiedPlayer pp) {
        if(this.invitations.contains(pp)) {
            this.invitations.remove(pp);
            return;
        }
    }
    public ServerInfo getServer() {
        return this.leader.getServer().getInfo();
    }
    public void invitePlayer(final ProxiedPlayer pp) {
        this.invitations.add(pp);
        pp.sendMessage(PARTY_PREFIX + "§e" + getLeader().getName() + " §7lädt dich in seine Party ein.");

        TextComponent accept = new TextComponent("§8» §5PartySystem §8• §7 §aAnnehmen §7mit: §e/party accept §c" + getLeader().getName());
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + getLeader().getName()));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aAnnehmen").create()));

        TextComponent deny = new TextComponent("§8» §5PartySystem §8• §7 §cAblehnen §7mit: §e/party deny §c" + getLeader().getName());
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party deny " + getLeader().getName()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cAblehnen").create()));

        pp.sendMessage(accept);
        pp.sendMessage(deny);
        BungeeCord.getInstance().getScheduler().schedule(BungeeCordSystem.getBungeeCordSystem(), ()->{
            if(PartyData.this.hasRequest(pp)) {
                PartyData.this.invitations.remove(pp);
                pp.sendMessage(PARTY_PREFIX + "§cDeine Einladung ist abgelaufen.");
                PartyData.this.getLeader().sendMessage(PARTY_PREFIX + "§cDie Einladung zu §e" + pp.getName() + " §cist abgelaufen§7.");
            }
            PartyData.this.start(pp);
        }, 5L, TimeUnit.MINUTES);
    }
    public void start(final ProxiedPlayer pp) {
        BungeeCord.getInstance().getScheduler().schedule(BungeeCordSystem.getBungeeCordSystem(), ()->{
            PartyData party = PartyManager.getParty(pp);
            if((party != null) && (party.getPlayers().size() == 0)) {
                PartyManager.deleteParty(pp);
                party.getLeader().sendMessage(PARTY_PREFIX + "§7Die Party wurde aufgrund zu weniger Mitglieder aufgelöst§7.");
                pp.sendMessage(PARTY_PREFIX + "§cDie Party wurde aufgelöst");
            }
        },5L, TimeUnit.MINUTES);
    }
}
