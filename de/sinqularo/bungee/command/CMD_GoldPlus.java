package de.sinqularo.bungee.command;

import de.sinqularo.bungee.BungeeCordSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_GoldPlus extends Command {

    public CMD_GoldPlus(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;

        if(strings.length == 0) {
            pp.sendMessage(BungeeCordSystem.SWIPEMC + "§7Die Anforderungen für den Gold+ Rang findest du hier:");
            pp.sendMessage(BungeeCordSystem.SWIPEMC + "§7https://swipemc.net/goldplus");
        }
    }
}
