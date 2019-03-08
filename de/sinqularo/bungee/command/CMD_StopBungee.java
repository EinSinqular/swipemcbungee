package de.sinqularo.bungee.command;

import de.sinqularo.bungee.BungeeCordSystem;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.TimeUnit;

public class CMD_StopBungee extends Command {

    public CMD_StopBungee(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;

        if(pp.hasPermission("Bungee.stopbungee")) {
         for(ProxiedPlayer bungeeCordPlayers : BungeeCord.getInstance().getPlayers()) {
             bungeeCordPlayers.disconnect("§cDer BungeeCord Server auf dem du dich befunden hast wurde gestoppt!");
         }
            BungeeCord.getInstance().getScheduler().schedule(BungeeCordSystem.getBungeeCordSystem(), ()->{
                BungeeCord.getInstance().stop();
            }, 1, TimeUnit.SECONDS);
        } else {
            pp.sendMessage(BungeeCordSystem.NO_PERMISSION);
        }
    }
}
