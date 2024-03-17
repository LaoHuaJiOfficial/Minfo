package Minfo.struct;

import arc.graphics.g2d.TextureRegion;
import arc.math.geom.Vec2;
import arc.util.Nullable;

public class PosEvent {
    public Vec2 pos = new Vec2();
    public @Nullable TextureRegion icon;
    public @Nullable String description;
    public PosEvent (Vec2 pos, TextureRegion icon, String desc){
        this.pos = pos;
        this.icon = icon;
        this.description = desc;
    }
}
