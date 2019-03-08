package de.sinqularo.bungee.command;

import de.sinqularo.bungee.BungeeCordSystem;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Br extends Command {

    public CMD_Br(String  name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;

        if(pp.hasPermission("bungee.br")) {
            if(strings.length == 0) {
                pp.sendMessage(BungeeCordSystem.PREFIX+ "§c/br <Nachricht>");
            } else {
                String broadcast = "";

                for(int i = 0; i < strings.length; i++) {
                    broadcast = (broadcast + strings[i] + " ");
                }

                for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                    all.sendMessage("§bSwipeMC.net §8» §7" + broadcast.replace("&", "§"));
                }

            }
        } else {
            pp.sendMessage(BungeeCordSystem.NO_PERMISSION);
        }
    }
}
