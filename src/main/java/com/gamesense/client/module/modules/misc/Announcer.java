package com.gamesense.client.module.modules.misc;

import com.gamesense.api.event.events.DestroyBlockEvent;
import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.api.event.events.PlayerJumpEvent;
import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.util.misc.MessageBus;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Module.Declaration(name = "Announcer", category = Category.Misc)
public class Announcer extends Module {

    public BooleanSetting clientSide = registerBoolean("Client Side", false);
    BooleanSetting walk = registerBoolean("Walk", true);
    BooleanSetting place = registerBoolean("Place", true);
    BooleanSetting jump = registerBoolean("Jump", true);
    BooleanSetting breaking = registerBoolean("Breaking", true);
    BooleanSetting attack = registerBoolean("Attack", true);
    BooleanSetting eat = registerBoolean("Eat", true);
    public BooleanSetting clickGui = registerBoolean("GUI", true);
    IntegerSetting delay = registerInteger("Delay", 1, 1, 20);

    public static int blockBrokeDelay = 0;
    static int blockPlacedDelay = 0;
    static int jumpDelay = 0;
    static int attackDelay = 0;
    static int eattingDelay = 0;
    static long lastPositionUpdate;
    static double lastPositionX;
    static double lastPositionY;
    static double lastPositionZ;
    private static double speed;
    String heldItem = "";
    int blocksPlaced = 0;
    int blocksBroken = 0;
    int eaten = 0;

    public static String walkMessage = "I just walked{blocks} meters thanks to SpiderSense!";
    public static String placeMessage = "I just put {amount} of the shit called {name} into the your mom thanks to SpiderSense!";
    public static String jumpMessage = "I just hopped all the way to your moms hause in the air thanks to SpiderSense!";
    public static String breakMessage = "I just snapped{amount}{name} out of existance thanks to SpiderSense!";
    public static String attackMessage = "I just smacked{name} with a{item} thanks to SpiderSense!";
    public static String eatMessage = "I just choked on {amount}{name}s thanks to SpiderSense!";
    public static String guiMessage = "I just opened my advanced hacking console thanks to GameSense!";

    //English, Arabic, Spanish, French, Hebrew, German, Japanese, Dutch, Greek, Turkish, Polish, Russian, Danish, Albanian, Chinese and Finnish.
    public static String[] walkMessages = {"Ich bin dank SpiderSense gerade{blocks} Meter gelaufen!"};
    public static String[] jumpMessages = {"Dank SpiderSense bin ich gerade bis zum Haus deiner Mutter in die Luft gesprungen!"};
    public static String[] breakMessages = {"Ich habe {amount}{name} dank SpiderSense gerade aus der Existenz gerissen!"};
    public static String[] eatMessages = {"I just choked on {amount}{name}s thanks to SpiderSense!"};

    public void onUpdate() {
        blockBrokeDelay++;
        blockPlacedDelay++;
        jumpDelay++;
        attackDelay++;
        eattingDelay++;
        heldItem = mc.player.getHeldItemMainhand().getDisplayName();

        if (walk.getValue()) {
            if (lastPositionUpdate + (5000L * delay.getValue()) < System.currentTimeMillis()) {

                double d0 = lastPositionX - mc.player.lastTickPosX;
                double d2 = lastPositionY - mc.player.lastTickPosY;
                double d3 = lastPositionZ - mc.player.lastTickPosZ;


                speed = Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);

                if (!(speed <= 1) && !(speed > 5000)) {
                    String walkAmount = new DecimalFormat("0.00").format(speed);

                    Random random = new Random();
                    if (clientSide.getValue()) {
                        MessageBus.sendClientPrefixMessage(walkMessage.replace("{blocks}", " " + walkAmount));
                    } else {
                        MessageBus.sendServerMessage(walkMessages[random.nextInt(walkMessages.length)].replace("{blocks}", " " + walkAmount));
                    }
                    lastPositionUpdate = System.currentTimeMillis();
                    lastPositionX = mc.player.lastTickPosX;
                    lastPositionY = mc.player.lastTickPosY;
                    lastPositionZ = mc.player.lastTickPosZ;
                }
            }
        }

    }

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<LivingEntityUseItemEvent.Finish> eatListener = new Listener<>(event -> {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if (event.getEntity() == mc.player) {
            if (event.getItem().getItem() instanceof ItemFood || event.getItem().getItem() instanceof ItemAppleGold) {
                eaten++;
                if (eattingDelay >= 300 * delay.getValue()) {
                    if (eat.getValue() && eaten > randomNum) {
                        Random random = new Random();
                        if (clientSide.getValue()) {
                            MessageBus.sendClientPrefixMessage(eatMessages[random.nextInt(eatMessages.length)].replace("{amount}", " " + eaten).replace("{name}", " " + mc.player.getHeldItemMainhand().getDisplayName()));
                        } else {
                            MessageBus.sendServerMessage(eatMessages[random.nextInt(eatMessages.length)].replace("{amount}", " " + eaten).replace("{name}", " " + mc.player.getHeldItemMainhand().getDisplayName()));
                        }
                        eaten = 0;
                        eattingDelay = 0;
                    }
                }
            }
        }
    });

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
            blocksPlaced++;
            int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
            if (blockPlacedDelay >= 150 * delay.getValue()) {
                if (place.getValue() && blocksPlaced > randomNum) {
                    Random random = new Random();
                    String msg = placeMessages[random.nextInt(placeMessages.length)].replace("{amount}", " " + blocksPlaced).replace("{name}", " " + mc.player.getHeldItemMainhand().getDisplayName());
                    if (clientSide.getValue()) {
                        MessageBus.sendClientPrefixMessage(msg);
                    } else {
                        MessageBus.sendServerMessage(msg);
                    }
                    blocksPlaced = 0;
                    blockPlacedDelay = 0;
                }
            }
        }
    });

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<DestroyBlockEvent> destroyListener = new Listener<>(event -> {
        blocksBroken++;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if (blockBrokeDelay >= 300 * delay.getValue()) {
            if (breaking.getValue() && blocksBroken > randomNum) {
                Random random = new Random();
                String msg = breakMessages[random.nextInt(breakMessages.length)]
                        .replace("{amount}", " " + blocksBroken)
                        .replace("{name}", " " + mc.world.getBlockState(event.getBlockPos()).getBlock().getLocalizedName());
                if (clientSide.getValue()) {
                    MessageBus.sendClientPrefixMessage(msg);
                } else {
                    MessageBus.sendServerMessage(msg);
                }
                blocksBroken = 0;
                blockBrokeDelay = 0;
            }
        }
    });

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<AttackEntityEvent> attackListener = new Listener<>(event -> {
        if (attack.getValue() && !(event.getTarget() instanceof EntityEnderCrystal)) {
            if (attackDelay >= 300 * delay.getValue()) {
                String msg = attackMessage.replace("{name}", " " + event.getTarget().getName()).replace("{item}", " " + mc.player.getHeldItemMainhand().getDisplayName());
                if (clientSide.getValue()) {
                    MessageBus.sendClientPrefixMessage(msg);
                } else {
                    MessageBus.sendServerMessage(msg);
                }
                attackDelay = 0;
            }
        }
    });

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<PlayerJumpEvent> jumpListener = new Listener<>(event -> {
        if (jump.getValue()) {
            if (jumpDelay >= 300 * delay.getValue()) {
                if (clientSide.getValue()) {
                    Random random = new Random();
                    MessageBus.sendClientPrefixMessage(jumpMessages[random.nextInt(jumpMessages.length)]);
                } else {
                    Random random = new Random();
                    MessageBus.sendServerMessage(jumpMessages[random.nextInt(jumpMessages.length)]);
                }
                jumpDelay = 0;
            }
        }
    });

    public void onEnable() {
        blocksPlaced = 0;
        blocksBroken = 0;
        eaten = 0;
        speed = 0;
        blockBrokeDelay = 0;
        blockPlacedDelay = 0;
        jumpDelay = 0;
        attackDelay = 0;
        eattingDelay = 0;
    }
}
