package de.sinqularo.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Help extends Command {

    public CMD_Help(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;

        if(strings.length == 0) {
            pp.sendMessage("§7» §7Hilfe Menü");
            pp.sendMessage("§e/Lobby §7- Kehre zur Lobby zurück");
            pp.sendMessage("§e/Party §7- Erstelle eine Party mit deinen Freunden");
            pp.sendMessage("§e/Friend §7- Verwalte deine Freunde");
            pp.sendMessage("§e/Report §7- Melde einen Spieler der gegen die Regeln verstößt");
            pp.sendMessage("§e/Gold §7- Anfordergungen zum §6Gold§a+ §7Rang");
            pp.sendMessage("§e/YouTuber §7- Anfordergungen zum §5YouTuber §7Rang");
            pp.sendMessage("§bTwitter §7- §b@SwipeMCNET");
        }

    }
}
