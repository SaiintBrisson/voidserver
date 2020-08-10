package me.saiintbrisson.voidserver.world.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location extends Vector {

    private String world;
    private float yaw, pitch;

    public Location(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector toVector() {
        return new Vector(getX(), getY(), getZ());
    }

    @Override
    public String toString() {
        return String.format(
          "world=%s, x=%.2f, y=%.2f, z=%.2f, yaw=%.2f, pitch=%.2f",
          world,
          getX(), getY(), getX(),
          yaw, pitch
        );
    }

}
