package com.gamesense.api.event.events;

import com.gamesense.api.event.GameSenseEvent;
import net.minecraft.entity.MoverType;

public class PlayerMoveEvent extends GameSenseEvent {

	private MoverType type;
    public double x;
    public double y;
    public double z;
    protected boolean onGround;

    public PlayerMoveEvent(int stage, MoverType type, double x, double y, double z, final boolean pOnGround) {
        super();
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = pOnGround;
    }

    public MoverType getType() {
        return this.type;
    }

    public void setType(MoverType type) {
        this.type = type;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

	 public void setOnGround(final boolean b) {
        this.onGround = b;
    }

}
