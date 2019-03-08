package de.sinqularo.bungee.pointsystem.command;

import de.sinqularo.bungee.BungeeCordSystem;
import de.sinqularo.bungee.mysql.entity.BungeePlayer;
import de.sinqularo.bungee.pointsystem.Points;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PointsCommand extends Command {

    public PointsCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;

        if(strings.length == 0) {
            BungeePlayer bungeePlayer = BungeeCordSystem.getBungeeCordSystem().getPlayerManager().getPlayers(pp.getUniqueId().toString());
            pp.sendMessage(Points.PREFIX + "§7Du hast derzeitig §e" + bungeePlayer.getGlobalPoints() + " Points§7.");
        }

    }
}
