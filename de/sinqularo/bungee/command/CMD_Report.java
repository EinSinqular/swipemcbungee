package de.sinqularo.bungee.command;

import de.sinqularo.bungee.BungeeCordSystem;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Report extends Command {


    public CMD_Report(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) commandSender;
            try {
                if(strings.length == 0) {
                    pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Benutze /Report <Spieler> <Grund>");
                } else if(strings[1].equalsIgnoreCase("Hacking")) {
                    ProxiedPlayer target = BungeeCord.getInstance().getPlayer(strings[0]);
                    if(target != null) {
                        if(pp == target) {
                           pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du kannst dich nicht selber reporten§7.");
                        } else {
                            if(target.hasPermission("bungee.report.bypass")) {
                                pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du kannst diesen Spieler nicht reporten§7.");
                            } else {
                                if(BungeeCordSystem.reportedPlayers.contains(target)) {
                                    pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Dieser Spieler wurde bereits reportet§7.");
                                } else {
                                    pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du hast §a" + target.getDisplayName() + " §7erfolgreich §creportet§7.");
                                    BungeeCordSystem.reportedPlayers.add(target);

                                    TextComponent tc = new TextComponent();
                                    tc.setText(BungeeCordSystem.REPORT_PREFIX + "§c" + target.getDisplayName() + " §7wurde wegen §eHACKING §cReportet§7.");
                                    TextComponent join = new TextComponent();
                                    join.setText(" §7[§aJOIN§7]");
                                    join.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rptp " + target.getName()));
                                    join.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aZum Spieler Teleportieren").create()));
                                    tc.addExtra(join);

                                    for(ProxiedPlayer team : BungeeCord.getInstance().getPlayers()) {
                                        if(team.hasPermission("report.see")) {
                                            try {
                                                team.sendMessage(tc);
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Der Spieler ist offline!");
                    }
                } else if(strings[1].equalsIgnoreCase("Teaming")) {
                    ProxiedPlayer target = BungeeCord.getInstance().getPlayer(strings[0]);
                    if(target != null) {
                        if(pp == target) {
                            pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du kannst dich nicht selber reporten§7.");
                        } else {
                            if(target.hasPermission("bungee.report.bypass")) {
                                pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du kannst diesen Spieler nicht reporten§7.");
                            } else {
                                if(BungeeCordSystem.reportedPlayers.contains(target)) {
                                    pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Dieser Spieler wurde bereits reportet§7.");
                                } else {
                                    pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du hast §a" + target.getDisplayName() + " §7erfolgreich §creportet§7.");
                                    BungeeCordSystem.reportedPlayers.add(target);

                                    TextComponent tc = new TextComponent();
                                    tc.setText(BungeeCordSystem.REPORT_PREFIX + "§c" + target.getDisplayName() + " §7wurde wegen §eTEAMING §cReportet§7.");
                                    TextComponent join = new TextComponent();
                                    join.setText(" §7[§aJOIN§7]");
                                    join.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rptp " + target.getName()));
                                    join.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aZum Spieler Teleportieren").create()));
                                    tc.addExtra(join);

                                    for(ProxiedPlayer team : BungeeCord.getInstance().getPlayers()) {
                                        if(team.hasPermission("report.see")) {
                                            try {
                                                team.sendMessage(tc);
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Der Spieler ist offline!");
                    }
                } else if(strings[1].equalsIgnoreCase("Bugusing")) {
                    ProxiedPlayer target = BungeeCord.getInstance().getPlayer(strings[0]);
                    if(target != null) {
                        if(pp == target) {
                            pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du kannst dich nicht selber reporten§7.");
                        } else {
                            if(target.hasPermission("bungee.report.bypass")) {
                                pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du kannst diesen Spieler nicht reporten§7.");
                            } else {
                                if(BungeeCordSystem.reportedPlayers.contains(target)) {
                                    pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Dieser Spieler wurde bereits reportet§7.");
                                } else {
                                    pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du hast §a" + target.getDisplayName() + " §7erfolgreich §creportet§7.");
                                    BungeeCordSystem.reportedPlayers.add(target);

                                    TextComponent tc = new TextComponent();
                                    tc.setText(BungeeCordSystem.REPORT_PREFIX + "§c" + target.getDisplayName() + " §7wurde wegen §eBUGUSING §cReportet§7.");
                                    TextComponent join = new TextComponent();
                                    join.setText(" §7[§aJOIN§7]");
                                    join.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rptp " + target.getName()));
                                    join.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aZum Spieler Teleportieren").create()));
                                    tc.addExtra(join);

                                    for(ProxiedPlayer team : BungeeCord.getInstance().getPlayers()) {
                                        if(team.hasPermission("report.see")) {
                                            try {
                                                team.sendMessage(tc);
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Der Spieler ist offline!");
                    }
                } else if(strings[1].equalsIgnoreCase("Beleidigung")) {
                    ProxiedPlayer target = BungeeCord.getInstance().getPlayer(strings[0]);
                    if(target != null) {
                        if(pp == target) {
                            pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du kannst dich nicht selber reporten§7.");
                        } else {
                            if(target.hasPermission("bungee.report.bypass")) {
                                pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du kannst diesen Spieler nicht reporten§7.");
                            } else {
                                if(BungeeCordSystem.reportedPlayers.contains(target)) {
                                    pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Dieser Spieler wurde bereits reportet§7.");
                                } else {
                                    pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du hast §a" + target.getDisplayName() + " §7erfolgreich §creportet§7.");
                                    BungeeCordSystem.reportedPlayers.add(target);

                                    TextComponent tc = new TextComponent();
                                    tc.setText(BungeeCordSystem.REPORT_PREFIX + "§c" + target.getDisplayName() + " §7wurde wegen §eBELEIDIGUNG §cReportet§7.");
                                    TextComponent join = new TextComponent();
                                    join.setText(" §7[§aJOIN§7]");
                                    join.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rptp " + target.getName()));
                                    join.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aZum Spieler Teleportieren").create()));
                                    tc.addExtra(join);

                                    for(ProxiedPlayer team : BungeeCord.getInstance().getPlayers()) {
                                        if(team.hasPermission("report.see")) {
                                            try {
                                                team.sendMessage(tc);
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Der Spieler ist offline!");
                    }
                } else if(strings[1].equalsIgnoreCase("Werbung")) {
                    ProxiedPlayer target = BungeeCord.getInstance().getPlayer(strings[0]);
                    if(target != null) {
                        if(pp == target) {
                            pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du kannst dich nicht selber reporten§7.");
                        } else {
                            if(target.hasPermission("bungee.report.bypass")) {
                                pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du kannst diesen Spieler nicht reporten§7.");
                            } else {
                                if(BungeeCordSystem.reportedPlayers.contains(target)) {
                                    pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Dieser Spieler wurde bereits reportet§7.");
                                } else {
                                    pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Du hast §a" + target.getDisplayName() + " §7erfolgreich §creportet§7.");
                                    BungeeCordSystem.reportedPlayers.add(target);

                                    TextComponent tc = new TextComponent();
                                    tc.setText(BungeeCordSystem.REPORT_PREFIX + "§c" + target.getDisplayName() + " §7wurde wegen §eWERBUNG §cReportet§7.");
                                    TextComponent join = new TextComponent();
                                    join.setText(" §7[§aJOIN§7]");
                                    join.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rptp " + target.getName()));
                                    join.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aZum Spieler Teleportieren").create()));
                                    tc.addExtra(join);

                                    for(ProxiedPlayer team : BungeeCord.getInstance().getPlayers()) {
                                        if(team.hasPermission("report.see")) {
                                            try {
                                                team.sendMessage(tc);
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        pp.sendMessage(BungeeCordSystem.REPORT_PREFIX + "§7Der Spieler ist offline!");
                    }
                }
            } catch (Exception ex) {

            }
        }
    }
}
