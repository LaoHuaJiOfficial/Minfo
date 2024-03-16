package Minfo.graphics;

import arc.Core;
import arc.graphics.g2d.TextureRegion;

public class MinfoMark {
    public static TextureRegion mark;

    public static void load() {
        mark = Core.atlas.find("minfo-mark-pin");
    }
}
