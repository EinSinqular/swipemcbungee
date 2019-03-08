package de.sinqularo.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Ping extends Command {

    public CMD_Ping(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;
        if(strings.length == 0) {
            pp.sendMessage("§8» §ePing §8• §7Dein §ePing §7beträgt §e§l" + pp.getPing() + "ms");
        }

    }
}
