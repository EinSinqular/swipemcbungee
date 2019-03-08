package de.sinqularo.bungee;

import de.sinqularo.bungee.command.*;
import de.sinqularo.bungee.listener.PlayerConnectionListener;
import de.sinqularo.bungee.listener.player.PlayerSwitchServerListener;
import de.sinqularo.bungee.manager.FreundeManager;
import de.sinqularo.bungee.manager.PlayerManager;
import de.sinqularo.bungee.mysql.MySQLManager;
import de.sinqularo.bungee.mysql.PreparedStatementBuilder;
import de.sinqularo.bungee.partysystem.PartyChat;
import de.sinqularo.bungee.partysystem.PartyCommand;
import de.sinqularo.bungee.partysystem.listener.PartyConnection;
import de.sinqularo.bungee.pointsystem.command.PointsCommand;
import de.sinqularo.bungee.runnables.BackendRunnable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BungeeCordSystem extends Plugin {


    private static BungeeCordSystem bungeeCordSystem;

    private MySQLManager mySQLManager;
    private FreundeManager freundeManager;
    private PlayerManager playerManager;

    private boolean BackendUpdating = false;

    public static String PREFIX = "§8» §4BungeeSystem §8• §7";
    public static String NO_PERMISSION = PREFIX + "§cDazu hast du keine Rechte§7.";

    public static String TC_PREFIX = "§8» §bTeamChat §8• §7";
    public static String TC_NOPERM = TC_PREFIX + "§cDazu hast du keine Rechte§7.";

    public static String SWIPEMC = "§8» §bSwipeMC §8• §7";

    public static String AC_PREFIX = "§8» §cAdminChat §8• §7";
    public static String AC_NOPERM = AC_PREFIX + "§cDazu hast du keine Rechte§7.";

    public static String J_PREFIX = "§8» §eJoinMe §8• §7";
    public static String J_NOPERM = J_PREFIX + "§cDazu hast du keine Rechte§7.";

    public static String REPORT_PREFIX = "§8» §4Report §8• §7";
    public static String REPORT_NOPERM = REPORT_PREFIX + "§cDazu hast du keine Rechte§7.";

    public static String FRIEND_PREFIX = "§8» §4Freunde §8• §7";
    public static String FRIEND_NOPERM = FRIEND_PREFIX + "§cDazu hast du keine Rechte§7.";

    public static ArrayList<ProxiedPlayer> reportedPlayers = new ArrayList<>();
    public static ArrayList<ProxiedPlayer> alreadyWatchDog = new ArrayList<>();

    public static ArrayList<ProxiedPlayer> login = new ArrayList<>();
    public static ArrayList<ProxiedPlayer> listed = new ArrayList<>();

    public static HashMap<ProxiedPlayer, Integer> joinCounter = new HashMap<ProxiedPlayer, Integer>();
    public static HashMap<ProxiedPlayer, Integer> globalPoints = new HashMap<ProxiedPlayer, Integer>();
    public static HashMap<ProxiedPlayer, String> playerIP = new HashMap<ProxiedPlayer, String>();
    public static HashMap<ProxiedPlayer, String> playerName = new HashMap<ProxiedPlayer, String>();



    public static HashMap<String, String> currentFriends = new HashMap<>();

    @Override
    public void onEnable() {
        bungeeCordSystem = this;
        this.mySQLManager = new MySQLManager("localhost", 3306, "BungeeCord", "admin", "ficken!!11");
        this.freundeManager = new FreundeManager();
        this.playerManager = new PlayerManager();
        System.out.println("BungeeCordSystem Aktiviert");
        mysqlRegister();
        configRegister();
        register();
        BackendRunnable.startTimer();
        super.onEnable();
    }

    @Override
    public void onDisable() {

        super.onDisable();
    }
    void register() {
        PluginManager pluginManager = BungeeCord.getInstance().getPluginManager();

        pluginManager.registerListener(this, new PlayerConnectionListener());
        pluginManager.registerListener(this, new PlayerSwitchServerListener());
        pluginManager.registerListener(this, new PartyConnection());


        pluginManager.registerCommand(this, new CMD_Report("report"));
        pluginManager.registerCommand(this, new RPTP_Command("rptp"));
        pluginManager.registerCommand(this, new CMD_Br("br"));
        pluginManager.registerCommand(this, new CMD_Friend("friend"));
        pluginManager.registerCommand(this, new TC_Command("tc"));
        pluginManager.registerCommand(this, new CMD_StopBungee("stopbungee"));
        pluginManager.registerCommand(this, new AC_Command("ac"));
        pluginManager.registerCommand(this, new CMD_Help("help"));
        pluginManager.registerCommand(this, new CMD_GoldPlus("gold"));
        pluginManager.registerCommand(this, new CMD_YouTuber("youtuber"));
        pluginManager.registerCommand(this, new CMD_Ping("ping"));
        pluginManager.registerCommand(this, new PartyCommand("party"));
        pluginManager.registerCommand(this, new PartyChat("p"));
        pluginManager.registerCommand(this, new PointsCommand("points"));

    }
    void mysqlRegister() {
        try {
            mySQLManager.openConnection(() ->{
                mySQLManager.update(new PreparedStatementBuilder("SET GLOBAL connect_timeout=28800", mySQLManager).build());
                mySQLManager.update(new PreparedStatementBuilder("SET GLOBAL wait_timeout=28800", mySQLManager).build());
                mySQLManager.update(new PreparedStatementBuilder("SET GLOBAL interactive_timeout=28800", mySQLManager).build());
                mySQLManager.update(new PreparedStatementBuilder("CREATE TABLE IF NOT EXISTS Freunde(uuid VARCHAR(64), name VARCHAR(64), friends TEXT, request TEXT, favorites TEXT, settings TEXT)", mySQLManager).build());
                mySQLManager.update(new PreparedStatementBuilder("CREATE TABLE IF NOT EXISTS Player(uuid VARCHAR(64), name VARCHAR(64), ipadress VARCHAR(64), globalPoints INT(64), joinCount INT(32))", mySQLManager).build());

            });
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    void configRegister() {

    }


    public static BungeeCordSystem getBungeeCordSystem() {
        return bungeeCordSystem;
    }

    public FreundeManager getFreundeManager() {
        return freundeManager;
    }

    public MySQLManager getMySQLManager() {
        return mySQLManager;
    }

    public boolean isBackendUpdating() {
        return BackendUpdating;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setBackendUpdating(boolean backendUpdating) {
        BackendUpdating = backendUpdating;
    }

    public static HashMap<ProxiedPlayer, Integer> getGlobalPoints() {
        return globalPoints;
    }

    public static HashMap<ProxiedPlayer, Integer> getJoinCounter() {
        return joinCounter;
    }

    public static HashMap<ProxiedPlayer, String> getPlayerName() {
        return playerName;
    }

    public static HashMap<ProxiedPlayer, String> getPlayerIP() {
        return playerIP;
    }
}
