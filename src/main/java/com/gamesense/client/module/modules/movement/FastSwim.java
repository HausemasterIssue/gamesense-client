package com.gamesense.client.module.modules.movement;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.util.math.MathHelper;

/*
* @author hausemasterissue
* @since 3/10/2021
* creds to cousinware
*/
// @todo cleanup and fix more
@Module.Declaration(name = "FastSwim", category = Category.Movement)
public class FastSwim extends Module {
	
	DoubleSetting speed = registerDouble("Speed", 0.75, 0.01, 2.00);
	DoubleSetting tickBoost = registerDouble("BoostSpeed", 0.80, 0.01, 2.00);
	IntegerSetting shiftTicks = registerInteger("ShiftTicks", 3, 1, 20);
	BooleanSetting strict = registerBoolean("Strict", false);
	
	int divider = 5;
    boolean only2b = false;
    boolean sprint = true;
    boolean water = false;
    boolean lava = true;
    boolean up = true;
    boolean down = true;
    int delay = 0;

    @Override
    public void onUpdate() {
        delay++;
            if (strict.getValue()) {
                if (!mc.isSingleplayer()) {
                    if (strict.getValue()) {

                        if (sprint) {
                            if (mc.player.isInLava() || mc.player.isInWater()) {
                                mc.player.setSprinting(true);
                            }
                        }

                        if (mc.player.isInWater() || mc.player.isInLava()) {
                            if (mc.gameSettings.keyBindJump.isKeyDown() && up) {
                                mc.player.motionY = .725 / divider;
                            }
                        }
                        if (mc.player.isInWater() && water && !mc.player.onGround) {
                            if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                                final float yaw = GetRotationYawForCalc();
                            }
                        }

                        if (mc.player.isInLava() && lava && !mc.player.onGround) {
                            if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                                final float yaw = GetRotationYawForCalc();
                                mc.player.motionX -= MathHelper.sin(yaw) * speed.getValue() / 10;
                                mc.player.motionZ += MathHelper.cos(yaw) * speed.getValue() / 10;
                            }
                        }


                        if (mc.player.isInWater() && down) {
                            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                                int divider2 = divider * -1;
                                mc.player.motionY = 2.2 / divider2;
                            }
                        }
                        if (mc.player.isInLava() && down) {
                            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                                int divider2 = divider * -1;
                                mc.player.motionY = .91 / divider2;
                            }

                        }
                        if (!mc.player.isInWater() && !mc.player.isInLava()) {
                        }
                    }
                }
            }
    }

    private float GetRotationYawForCalc() {
        float rotationYaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }

        float offset = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            offset = -0.5f;
        } else if (mc.player.moveForward > 0.0f) {
            offset = 0.5f;
        }

        if (mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * offset;
        } else if (mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * offset;
        }

        return rotationYaw * 0.017453292f;
    }
}
