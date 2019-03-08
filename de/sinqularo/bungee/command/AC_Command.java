package de.sinqularo.bungee.command;

import de.sinqularo.bungee.BungeeCordSystem;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AC_Command extends Command {

    public AC_Command(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;
        if(pp.hasPermission("bungee.ac")) {
            if (strings.length == 0) {
                pp.sendMessage(BungeeCordSystem.AC_PREFIX + "§cBitte gebe eine Nachricht ein!");

            } else if (strings[0].equalsIgnoreCase(strings[0])) {
                for (ProxiedPlayer proxyPlayer : BungeeCord.getInstance().getPlayers()) {
                    if (proxyPlayer.hasPermission("bungee.ac")) {
                        String msg = "";
                        for (int i = 0; i < strings.length; i++) {
                            msg = msg + " " + strings[i];
                        }
                        proxyPlayer.sendMessage(BungeeCordSystem.AC_PREFIX + "§c" + pp.getDisplayName() + " §8» §7" + msg);
                    }
                }

            }
        } else {
            pp.sendMessage(BungeeCordSystem.AC_NOPERM);
        }
    }
}
