package me.saiintbrisson.voidserver.world.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vector {

    private double x, y, z;

    public long toLong() {
        return (((int) x & 0x3FFFFFF) << 6) | (((int) z & 0x3FFFFFF) << 12) | ((int) y & 0xFFF);
    }

    @Override
    public String toString() {
        return String.format("x=%.2f, y=%.2f, z=%.2f", x, y, z);
    }

}
