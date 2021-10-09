package com.gamesense.client.module.modules.misc;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.util.misc.Timer;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/*
* @authors hausemasterissue, aestheticall
* @since 3/10/2021
* HUGE HUGE HUGE HUGE thanks to aestheticall and his super based client inferno for the code :))))
*/

@Module.Declaration(name = "AntiAFK", category = Category.Misc)
public class AntiAFK extends Module {
	private static final Random RNG = new Random();
	
	public BooleanSetting rotate = registerBoolean("Rotate", true);
	public BooleanSetting punch = registerBoolean("Punch", true);
	public BooleanSetting jump = registerBoolean("Jump", true);
	public BooleanSetting sneak = registerBoolean("Sneak", true);
	public BooleanSetting message = registerBoolean("Message", true);
	public IntegerSetting delay = registerInteger("Delay", 1, 2, 30);
	public IntegerSetting randomDelay = registerInteger("RandomDelay", 5, 1, 30);
	
	private final Timer timer = new Timer();
    private int sneakTicks = 0;
    private int requiredSneakTicks = 0;
    
    @Override
    protected void onDisable() {
        timer.reset();
        if (sneakTicks != 0) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }

        sneakTicks = 0;
        requiredSneakTicks = 0;
    }
    
    @SubscribeEvent
    public void onUpdate() {
        if (sneak.getValue()) {
            ++sneakTicks;
            if (sneakTicks >= requiredSneakTicks) {
                sneakTicks = 0;
                requiredSneakTicks = 0;
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
        }

        if (timer.passedS(delay.getValue() * 1000 + RNG.nextInt(randomDelay.getValue()) * 1000)) {
            timer.reset();

            int action = random(1, 5);
            for (int i = 0; i < 6; ++i) {
                if (!check(action)) {
                    action = action + 1 > 5 ? 1 : action + 1;
                } else {
                    doAction(action);
                    break;
                }
            }
        }
    }
    
    private boolean check(int action) {
        switch (action) {
            case 1: return rotate.getValue();
            case 2: return punch.getValue();
            case 3: return jump.getValue();
            case 4: return sneak.getValue();
            case 5: return message.getValue();
        }

        return true;
    }
    
    private void doAction(int action) {
        switch (action) {
            case 1: {
                mc.player.rotationYaw = (float) random(1, 360);

                int pitch = random(1, 90);
                mc.player.rotationPitch = RNG.nextBoolean() ? -pitch : pitch;
                break;
            }

            case 2: {
                mc.player.swingArm(RNG.nextBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                break;
            }

            case 3: {
                mc.player.jump();
                break;
            }

            case 4: {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));

                sneakTicks = 0;
                requiredSneakTicks = random(1, 3);
                break;
            }

            case 5: {
                mc.player.sendChatMessage(weDoABitOfTrolling("I am sending this message to not get AFK kicked thanks to SpiderSense!" + random(2489, 92472)));
                break;
            }
        }

    }
    
    private String weDoABitOfTrolling(String text) {
        return Arrays.stream(text.split("")).map((str) -> {
            String s = RNG.nextBoolean() ? str.toUpperCase() : str.toLowerCase();

            if (RNG.nextBoolean()) {
                s = s.replaceAll("o", "0");
            }

            if (RNG.nextBoolean()) {
                s = s.replaceAll("i", "1");
            }

            return s;
        }).collect(Collectors.joining(""));
    }
    
    private int random(int min, int max) {
        return RNG.nextInt(max + min) - min;
    }



}
