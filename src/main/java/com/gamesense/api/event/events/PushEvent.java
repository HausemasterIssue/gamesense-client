package com.gamesense.api.event.events;

import com.gamesense.api.event.GameSenseEvent;

public class PushEvent extends GameSenseEvent {
  private final Type type;

    public PushEvent(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        BLOCKS, ENTITY, LIQUID
    }
}
