package eu.dieytberlegion.nick;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import eu.dieytberlegion.OneVsOne.OneVsOne;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by HerrPrefix on 22.02.2017.
 */
public class Nick {

    public static ArrayList<String> nicklist = new ArrayList<>();
    public static ArrayList<String> list = new ArrayList<>();

    public static ArrayList<Player> nicked = new ArrayList<>();
    public static HashMap<UUID, String> nicks = new HashMap<>();
    public static HashMap<UUID, String> names = new HashMap<>();
    public static HashMap<UUID, String> realname = new HashMap<>();

    private static void setNickToPlayer(Player p, Player toset, String nick) {

        nick = ChatColor.translateAlternateColorCodes('&', nick);

        CraftPlayer cp = (CraftPlayer) p;

        try {
            Field field = cp.getProfile().getClass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(cp.getProfile(), nick);
            field.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException ignored) {
        }

        setSkin(cp, ChatColor.stripColor(nick));

        if (p != toset) {
            PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(cp.getEntityId());
            sendPacketToPlayer(destroy, toset);
        }

        PacketPlayOutPlayerInfo rvmtab = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, cp.getHandle());
        sendPacketToPlayer(rvmtab, toset);

        new BukkitRunnable() {

            @Override
            public void run() {

                PacketPlayOutPlayerInfo addtab = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, cp.getHandle());
                sendPacketToPlayer(addtab, toset);

                if (p != toset) {
                    PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(cp.getHandle());
                    sendPacketToPlayer(spawn, toset);
                }
            }
        }.runTaskLater(OneVsOne.getInstance(), 2);
    }

    private static void nickForAll(Player p, String nick) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            setNickToPlayer(p, all, nick);
        }
    }

    public static void setSkin(CraftPlayer cp, String nick) {

        UUID uuid = UUIDFetcher.getUUID(nick);
        GameProfile skin = cp.getProfile();

        if (uuid != null) {
            try {
                skin = GameProfileBuilder.fetch(uuid);
            } catch (IOException e) {
            }
        } else {
            try {
                skin = GameProfileBuilder.fetch(UUIDFetcher.getUUID("Steve"));
            } catch (IOException e) {
            }
        }

        Collection<Property> prop = skin.getProperties().get("textures");

        cp.getProfile().getProperties().removeAll("textures");
        cp.getProfile().getProperties().putAll("textures", prop);

    }

    public static void sendPacketToPlayer(Packet<?> packet, Player p) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public static boolean isNicked(Player p) {
        return names.containsKey(p.getUniqueId());
    }

    public static void nick(Player p, String nick) {

        if (nick.length() > 14) {
            nick = nick.substring(0, 14);
        }

        nicked.add(p);
        names.put(p.getUniqueId(), p.getDisplayName());
        nicks.put(p.getUniqueId(), nick);

        nickForAll(p, nick);
        p.setDisplayName("§7" + nick);

    }

    public static void unnick(Player p) {

        nickForAll(p, names.get(p.getUniqueId()));
        p.setDisplayName(names.get(p.getUniqueId()));
        list.add(nicks.get(p.getUniqueId()));

        nicked.remove(p);
        names.remove(p.getUniqueId());
        nicks.remove(p.getUniqueId());

    }

    public static void remove(Player p) {
        if (nicked.contains(p)) {
            nicked.remove(p);
        }
        if (names.containsKey(p)) {
            names.remove(p);
        }
        if (nicks.containsKey(p)) {
            nicks.remove(p);
        }
        if (realname.containsKey(p)) {
            realname.remove(p);
        }
    }

    public static String getRandomNameOld() {

        ArrayList<String> names = new ArrayList<>();

        names.add("BabyKillaruh");
        names.add("Skinwalkers");
        names.add("RedYosi");
        names.add("SaltyTim");
        names.add("Wqsp");
        names.add("SxImon");
        names.add("ColdCrawL");
        names.add("hiomg");
        names.add("38k");
        names.add("LSuh");
        names.add("CuteMary");
        names.add("1664");
        names.add("Winop");
        names.add("BigHearted");
        names.add("Sogghyys");
        names.add("Vyt");
        names.add("Phimp");
        names.add("Kaawaaii");
        names.add("WEEE");
        names.add("Kompact");
        names.add("Slangs");
        names.add("solreZ");
        names.add("OmehaToggledv3");
        names.add("Niwok");
        names.add("MarcoPlayzHD");
        names.add("nflboy717 ");
        names.add("michelle ");
        names.add("Orepros ");
        names.add("Luvitus ");
        names.add("jtmf26");
        names.add("slam1000bob");
        names.add("WilliamBoo");
        names.add("KohdWing");
        names.add("Hawkb");
        names.add("cheese202");
        names.add("Perpetual");
        names.add("flyboy7");
        names.add("Kyletiv7");
        names.add("LlamasATA");
        names.add("georgeyves");
        names.add("cookie10");
        names.add("3agle");
        names.add("DirtDog");
        names.add("raringen1023");
        names.add("MinecraftDotNet");
        names.add("Zephhyre");
        names.add("Rubysown");
        names.add("killero");
        names.add("pie0017");
        names.add("adamj333");
        names.add("BrenyBeast");
        names.add("BlueMoonParkour");
        names.add("MessyTurkey");
        names.add("RGAMinecraft");
        names.add("Bakerbelle");
        names.add("YamiJerry");
        names.add("spider5");
        names.add("VanRyderLP");
        names.add("AceCraftGaming");
        names.add("drpoonhammer");
        names.add("bodil40wolfie");
        names.add("crawler600");
        names.add("LokiDarkfire");
        names.add("sakul1000");
        names.add("meerca8");
        names.add("cj1124");
        names.add("matzehaft");
        names.add("Endergeek1023");
        names.add("Headshot10105MC");
        names.add("hjerrild1023");
        names.add("goblinking20");
        names.add("ThinkNoodles");
        names.add("Cutekaos");
        names.add("PieguyGames");
        names.add("Wazdorf");
        names.add("comete99");
        names.add("gamerAJ3103");
        names.add("Glis6");
        names.add("cgcrouse822");
        names.add("kevinbo");
        names.add("ry22an");
        names.add("Castle");
        names.add("thethrill999");
        names.add("MartysGames");
        names.add("HubbaGaming");
        names.add("weas90");
        names.add("Juicetra");
        names.add("calvinatorz");
        names.add("DefaultAsAwesome");
        names.add("BeatatoCraft");
        names.add("TheMiningCake");
        names.add("GotGoose");
        names.add("myethan1");
        names.add("Jarid");
        names.add("CrysisDoomX");
        names.add("Peanut");

        ArrayList<String> temp = new ArrayList<>();

        for (String name : names) {
            Bukkit.getOnlinePlayers().stream().filter(all -> ChatColor.stripColor(all.getName()).equalsIgnoreCase(name)).forEach(all -> temp.add(name));
        }
        for (String name : temp) {
            names.remove(name);
        }

        return names.get(new Random().nextInt(names.size()));
    }


    public static String getRealName(Player p) {
        if (isNicked(p)) {
            return org.bukkit.ChatColor.stripColor(realname.get(p.getUniqueId()));
        } else {
            return org.bukkit.ChatColor.stripColor(p.getName());
        }
    }

    public static Player getPlayerByName(String name) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (org.bukkit.ChatColor.stripColor(all.getName()).equalsIgnoreCase(ChatColor.stripColor(name))) {
                return all;
            }
        }
        return null;
    }
}