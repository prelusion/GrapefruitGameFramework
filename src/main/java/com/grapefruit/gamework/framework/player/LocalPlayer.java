package com.grapefruit.gamework.framework.player;

import com.grapefruit.gamework.framework.Colors;

public class LocalPlayer extends Player {
    public LocalPlayer(String name, Colors color, boolean localPlayer) {
        super(name, color, true);
    }
}
