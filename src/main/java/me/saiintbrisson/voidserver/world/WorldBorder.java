package me.saiintbrisson.voidserver.world;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WorldBorder {

    private double x, z;

    private double originalDiameter, newDiameter;

    private long speed;

    private int portalTeleportBoundary;

    private int warningTime, warningBlocks;

}
