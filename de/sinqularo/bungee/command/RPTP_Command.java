package de.sinqularo.bungee.command;

import de.sinqularo.bungee.BungeeCordSystem;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RPTP_Command extends Command {

    public RPTP_Command(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) commandSender;
            if(pp.hasPermission("rp.rp")) {
                if(strings.length == 0) {

                } else {
                    ProxiedPlayer target = BungeeCord.getInstance().getPlayer(strings[0]);
                    if(target != null) {
                        if(!BungeeCordSystem.reportedPlayers.contains(target)) {
                            pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§cDer Report ist abgelaufen.");
                        } else {
                            if(BungeeCordSystem.alreadyWatchDog.contains(target)) {
                                pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§cDieser Report wird bereits bearbeitet!");
                            } else {
                                pp.connect(target.getServer().getInfo());
                                pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du kümmerst dich nun um §e" + target.getName());
                                BungeeCordSystem.alreadyWatchDog.add(target);
                            }

                        }
                    } else {
                        pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§cDieser Spieler ist offline!");
                    }
                }
            } else {
                pp.sendMessage(BungeeCordSystem.REPORT_PREFIX);
            }
        }
    }
}
