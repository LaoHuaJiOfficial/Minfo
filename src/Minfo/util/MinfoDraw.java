package Minfo.util;

import arc.func.Boolp;
import arc.func.Floatp;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.graphics.Pal;

public class MinfoDraw {
    public static void drawSideBar(float x, float y, float sideLength, float sideWidth, float percent, Color color, boolean flip){
        Draw.color(color);

        float length_0 = (percent > 0.5f ? 1f: (percent > 0f? percent * 2f: 0f)) * sideLength;
        float length_1 = (percent > 1f ? 1f: (percent > 0.5f? percent * 2f - 1f: 0f)) * sideLength;

        //the bottom corner
        Tmp.v1.set(
            flip? x - sideLength / 2: x + sideLength / 2,
            y - sideLength * Mathf.sqrt3 / 2
        );

        float x_offset = (flip? -sideWidth: sideWidth);

        float x_shift_0 = flip ? length_0 / 2f : -length_0 / 2f;
        float y_shift_0 = flip ? -x_shift_0 * Mathf.sqrt3: x_shift_0 * Mathf.sqrt3;

        float x_shift_1 = flip ? length_1 / 2f : -length_1 / 2f;
        float y_shift_1 = flip ? -x_shift_1 * Mathf.sqrt3: x_shift_1 * Mathf.sqrt3;
        if (length_0 > 0){
            Fill.quad(
                Tmp.v1.x,
                Tmp.v1.y,
                Tmp.v1.x + x_offset,
                Tmp.v1.y,
                Tmp.v1.x + x_offset + x_shift_0,
                Tmp.v1.y - y_shift_0,
                Tmp.v1.x + x_shift_0,
                Tmp.v1.y - y_shift_0
            );
        }
        if (length_1 > 0){
            Fill.quad(
                x,
                y,
                x + x_offset,
                y,
                x + x_offset - x_shift_1,
                y - y_shift_1,
                x - x_shift_1,
                y - y_shift_1
            );
        }
        Draw.reset();
    }

}
