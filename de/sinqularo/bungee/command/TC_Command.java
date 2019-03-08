package de.sinqularo.bungee.command;

import de.sinqularo.bungee.BungeeCordSystem;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TC_Command extends Command {

    public TC_Command(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) commandSender;
            if(pp.hasPermission("tc.tc")) {
                if(strings.length > 0) {
                    if(strings[0].equalsIgnoreCase("login")) {
                        if(!BungeeCordSystem.login.contains(pp)) {
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

                                } else {
                                    BungeeCordSystem.login.contains(all);
                                }
                            }
                            BungeeCordSystem.login.add(pp);
                            pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§aDu hast dich eingeloggt§7.");
                        } else if(BungeeCordSystem.login.contains(pp)) {
                            pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§cDu bist bereits eingeloggt§7.");
                        }
                    } else if(strings[0].equalsIgnoreCase("logout")) {
                        if(BungeeCordSystem.login.contains(pp)) {
                            BungeeCordSystem.login.remove(pp);
                            pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§cDu hast dich ausgeloggt§7.");
                            if(!BungeeCordSystem.login.contains(pp)) {
                                for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                                    if(BungeeCordSystem.login.contains(all)) {
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
                                    } else {
                                        BungeeCordSystem.login.contains(all);
                                    }
                                }
                            }
                        } else if(!BungeeCordSystem.login.contains(pp)) {
                            pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§cDu bist bereits ausgeloggt§7.");
                        }
                    } else if(strings[0].equalsIgnoreCase("help")) {
                        pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§7Nutze : <§aLogin §7| §cLogout §7| §eList §7| §bNachricht §7>");
                    } else if(strings[0].equalsIgnoreCase("list")) {
                        if(BungeeCordSystem.login.contains(pp)) {
                            pp.sendMessage("");
                            pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§8----------= §bTeamChat §8=----------");
                            for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                                if(BungeeCordSystem.login.contains(all)) {
                                    if(!BungeeCordSystem.listed.contains(all)) {

                                        pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§e" + all.getDisplayName() + " §7ist §aeingeloggt §7auf §e" + all.getServer().getInfo().getName());


                                    } else {
                                        pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§e" + all.getDisplayName() + " §7ist §aeingeloggt §7auf §8: §eVersteckt");
                                    }
                                }
                            }
                            pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§8----------= §bTeamChat §8=----------");
                        } else {
                            pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§cDu musst eingeloggt sein§7.");
                        }
                    } else if(strings[0].equalsIgnoreCase("add")) {
                        if(strings[1].equalsIgnoreCase("list")) {
                            if(BungeeCordSystem.login.contains(pp)) {
                                if(!BungeeCordSystem.listed.contains(pp)) {
                                    BungeeCordSystem.listed.add(pp);
                                    pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§7Dein §aAktueller Server §7wird nun im TeamChat versteckt.");
                                } else {
                                    pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§cDein Server ist bereits unkenntlich§7.");
                                }
                            } else {
                                pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§cDu musst eingeloggt sein§7.");
                            }
                        }
                    } else if(strings[0].equalsIgnoreCase("remove")) {
                        if(strings[1].equalsIgnoreCase("list")) {
                            if(BungeeCordSystem.login.contains(pp)) {
                                if(BungeeCordSystem.listed.contains(pp)) {
                                    BungeeCordSystem.listed.remove(pp);
                                    pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§7Dein §aAktueller Server §7ist nun wieder sichtbar§7.");
                                } else {
                                    pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§7Dein Server ist bereits zu sehen.");
                                }
                            } else {
                                pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§cDu musst eingeloggt sein§7.");
                            }
                        }
                    } else if(BungeeCordSystem.login.contains(pp)) {
                        for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                            if(all.hasPermission("tc.tc")) {
                                if(BungeeCordSystem.login.contains(all)) {
                                    String msg = "";
                                    for(int i = 0; i < strings.length; i++) {
                                        msg = msg + " " + strings[i];
                                    }
                                    if(pp.hasPermission("display.serverinhaber")) {
                                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§4" + pp.getDisplayName() + " §8» §7" + msg);
                                    } else if(pp.hasPermission("display.admin")) {
                                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§c" + pp.getDisplayName() + " §8» §7" + msg);
                                    } else if(pp.hasPermission("display.dev")) {
                                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§b" + pp.getDisplayName() + " §8» §7" + msg);
                                    } else if(pp.hasPermission("display.srmod")) {
                                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§9" + pp.getDisplayName() + " §8» §7" + msg);
                                    } else if(pp.hasPermission("display.mod")) {
                                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§9" + pp.getDisplayName() + " §8» §7" + msg);
                                    } else if(pp.hasPermission("display.srsupporter")) {
                                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§e" + pp.getDisplayName() + " §8» §7" + msg);
                                    } else if(pp.hasPermission("display.supporter")) {
                                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§e" + pp.getDisplayName() + " §8» §7" + msg);
                                    } else if(pp.hasPermission("display.bauleitung")) {
                                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§2" + pp.getDisplayName() + " §8» §7" + msg);
                                    } else if(pp.hasPermission("display.builder")) {
                                        all.sendMessage(BungeeCordSystem.TC_PREFIX + "§2" + pp.getDisplayName() + " §8» §7" + msg);
                                    }
                                } else {
                                    BungeeCordSystem.login.contains(all);
                                }
                            }
                        }
                    } else if(!BungeeCordSystem.login.contains(pp)) {
                        pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§cDu musst eingeloggt sein§7.");
                    }
                } else if(strings.length == 0) {
                    pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§7Nutze : <§aLogin §7| §cLogout §7| §eList §7| §bNachricht §7>");
                }
            } else {
                pp.sendMessage(BungeeCordSystem.TC_PREFIX + "§cDazu hast du keine Rechte§7.");
            }
        } else {
            commandSender.sendMessage("null. Du bist kein Spieler, du schlingel ;)");
        }
    }
}
