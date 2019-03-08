package de.sinqularo.bungee.listener;

import de.sinqularo.bungee.BungeeCordSystem;
import de.sinqularo.bungee.mysql.entity.BungeePlayer;
import de.sinqularo.bungee.mysql.entity.FriendEntity;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onConnect(PostLoginEvent e) {
        ProxiedPlayer pp = e.getPlayer();

        BungeeCord.getInstance().getScheduler().schedule(BungeeCordSystem.getBungeeCordSystem(), ()->{
            BungeePlayer bungeePlayer = BungeeCordSystem.getBungeeCordSystem().getPlayerManager().getPlayers(pp.getUniqueId().toString());
            int joinCounter = bungeePlayer.getJoinCount() + 1;
            bungeePlayer.setJoinCount(joinCounter);
            bungeePlayer.setUuid(pp.getUniqueId().toString());
            bungeePlayer.setName(pp.getName());
            bungeePlayer.setIpAdress(pp.getAddress().toString());
            BungeeCordSystem.getBungeeCordSystem().getPlayerManager().updatePlayer(bungeePlayer);

        }, 25, TimeUnit.SECONDS);


        for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
            FriendEntity friends = BungeeCordSystem.getBungeeCordSystem().getFreundeManager().getFriends(all.getUniqueId().toString());
            FriendEntity player = BungeeCordSystem.getBungeeCordSystem().getFreundeManager().getFriends(pp.getUniqueId().toString());
            if(player.getFriends().contains(friends.getUuid())) {
                all.sendMessage(BungeeCordSystem.FRIEND_PREFIX + "§a" + pp.getName() + " §7ist jetzt §aonline§7.");
            }
        }

        if(pp.hasPermission("bungee.tc")) {
            BungeeCordSystem.login.add(pp);
            pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§7Du wurdest automatisch in den TeamChat eingeloggt§7.");
            for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                if(BungeeCordSystem.login.contains(all)) {
                    if(pp.hasPermission("display.serverinhaber")) {
                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§4" + pp.getDisplayName() + " §7hat sich in den Teamchat §aeingeloggt§7.");
                    } else if(pp.hasPermission("display.admin")) {
                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§c" + pp.getDisplayName() + " §7hat sich in den Teamchat §aeingeloggt§7.");
                    } else if(pp.hasPermission("display.dev")) {
                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§b" + pp.getDisplayName() + " §7hat sich in den Teamchat §aeingeloggt§7.");
                    } else if(pp.hasPermission("display.srmod")) {
                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§9" + pp.getDisplayName() + " §7hat sich in den Teamchat §aeingeloggt§7.");
                    } else if(pp.hasPermission("display.mod")) {
                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§9" + pp.getDisplayName() + " §7hat sich in den Teamchat §aeingeloggt§7.");
                    } else if(pp.hasPermission("display.srsupporter")) {
                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§e" + pp.getDisplayName() + " §7hat sich in den Teamchat §aeingeloggt§7.");
                    } else if(pp.hasPermission("display.supporter")) {
                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§e" + pp.getDisplayName() + " §7hat sich in den Teamchat §aeingeloggt§7.");
                    } else if(pp.hasPermission("display.bauleitung")) {
                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§2" + pp.getDisplayName() + " §7hat sich in den Teamchat §aeingeloggt§7.");
                    } else if(pp.hasPermission("display.builder")) {
                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§2" + pp.getDisplayName() + " §7hat sich in den Teamchat §aeingeloggt§7.");
                    }
                }
            }


        }
    }
    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {
        ProxiedPlayer pp = e.getPlayer();
        if(BungeeCordSystem.reportedPlayers.contains(pp)) {
            BungeeCordSystem.reportedPlayers.remove(pp);
        }

        for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
            FriendEntity friends = BungeeCordSystem.getBungeeCordSystem().getFreundeManager().getFriends(all.getUniqueId().toString());
            FriendEntity player = BungeeCordSystem.getBungeeCordSystem().getFreundeManager().getFriends(pp.getUniqueId().toString());
            if(player.getFriends().contains(friends.getUuid())) {
                all.sendMessage(BungeeCordSystem.FRIEND_PREFIX + "§a" + pp.getName() + " §7ist jetzt §coffline§7.");
            }
        }


        if(pp.hasPermission("bungee.tc")) {
           if(BungeeCordSystem.login.contains(pp)) {
               BungeeCordSystem.login.remove(pp);
               for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                   if (BungeeCordSystem.login.contains(all)) {
                       if(pp.hasPermission("display.serverinhaber")) {
                           all.sendMessage(BungeeCordSystem.TC_PREFIX + "§4" + pp.getDisplayName() + " §7hat sich aus dem Teamchat §causgeloggt§7.");
                       } else if(pp.hasPermission("display.admin")) {
                           all.sendMessage(BungeeCordSystem.TC_PREFIX + "§c" + pp.getDisplayName() + " §7hat sich aus dem Teamchat §causgeloggt§7.");
                       } else if(pp.hasPermission("display.dev")) {
                           all.sendMessage(BungeeCordSystem.TC_PREFIX + "§b" + pp.getDisplayName() + " §7hat sich aus dem Teamchat §causgeloggt§7.");
                       } else if(pp.hasPermission("display.srmod")) {
                           all.sendMessage(BungeeCordSystem.TC_PREFIX + "§9" + pp.getDisplayName() + " §7hat sich aus dem Teamchat §causgeloggt§7.");
                       } else if(pp.hasPermission("display.mod")) {
                           all.sendMessage(BungeeCordSystem.TC_PREFIX + "§9" + pp.getDisplayName() + " §7hat sich aus dem Teamchat §causgeloggt§7.");
                       } else if(pp.hasPermission("display.srsupporter")) {
                           all.sendMessage(BungeeCordSystem.TC_PREFIX + "§e" + pp.getDisplayName() + " §7hat sich aus dem Teamchat §causgeloggt§7.");
                       } else if(pp.hasPermission("display.supporter")) {
                           all.sendMessage(BungeeCordSystem.TC_PREFIX + "§e" + pp.getDisplayName() + " §7hat sich aus dem Teamchat §causgeloggt§7.");
                       } else if(pp.hasPermission("display.bauleitung")) {
                           all.sendMessage(BungeeCordSystem.TC_PREFIX + "§2" + pp.getDisplayName() + " §7hat sich aus dem Teamchat §causgeloggt§7.");
                       } else if(pp.hasPermission("display.builder")) {
                           all.sendMessage(BungeeCordSystem.TC_PREFIX + "§2" + pp.getDisplayName() + " §7hat sich aus dem Teamchat §causgeloggt§7.");
                       }
                   }
               }
            }
        }
    }
}
