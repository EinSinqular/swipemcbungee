package de.sinqularo.bungee.command;

import de.sinqularo.bungee.BungeeCordSystem;
import de.sinqularo.bungee.mysql.entity.FriendEntity;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Friend extends Command {

    public CMD_Friend(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;
        FriendEntity mysqlPlayer = BungeeCordSystem.getBungeeCordSystem().getFreundeManager().getFriends(pp.getUniqueId().toString());

        if(strings.length == 0) {
            pp.sendMessage("§7----- §4FreundeSystem §7-----");
            pp.sendMessage("§7/Friend add <Spieler> - §aadde einen Freund");
            pp.sendMessage("§7/Friend remove <Spieler> - §aentferne einen Freund");
            pp.sendMessage("§7/Friend list <Spieler> - §aliste alle deine Freunde auf");
            pp.sendMessage("§7/Friend jump <Spieler> - §aSpringe zu deinem Freund");
            pp.sendMessage("§7/msg <Spieler> - §aSende deinem Freund eine Nachricht");
            pp.sendMessage("§7/Friend accept <Spieler> - §aNehme einen Spieler an");
            pp.sendMessage("§7/Friend deny <Spieler> - §aLehne einen Spieler ab");

        } else if(strings[0].equalsIgnoreCase("add")) {

        }

    }
}
