package Minfo.graphics;

import Minfo.MinfoVars;
import Minfo.util.MinfoDraw;
import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.input.KeyCode;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.gen.Entityc;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.gen.Unitc;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

import static arc.Core.input;

public class UnitsDraw {
    public static void draw(){
        if(MinfoVars.DetailMode || input.keyDown(KeyCode.altLeft)){
            Groups.player.each(player -> {
                Draw.z(MinfoLayer.detailRange);
                Draw.color(Pal.accent);
                //Drawf.dashLine(Pal.accent, player.x, player.y, player.unit().aimX, player.unit().aimY);
                Draw.reset();
            });
            Groups.unit.each(unit -> {
                if (unit.isGrounded() && MinfoVars.DetailGroundUnit){
                    drawHitbox(unit);
                }
                if (unit.isFlying() && MinfoVars.DetailAirUnit){
                    drawHitbox(unit);
                }

                Draw.z(MinfoLayer.detailRange);

                if (unit.aimX != 0 && unit.aimY != 0 && unit.isShooting()){
                    Drawf.dashLine(Pal.accent, unit.x, unit.y, unit.aimX, unit.aimY);
                }

                //Drawf.dashCircle(unit.x, unit.y, unit.range(), Pal.accent);

                Draw.reset();
            });
        }
    }

    public static void drawHitbox(Unit unit){
        Draw.z(MinfoLayer.detailRange + 0.01f);
        Draw.color(unit.team.color);
        Lines.stroke(Mathf.clamp(unit.hitSize * 0.05f, 0.75f, 2.5f));
        Lines.poly(unit.x, unit.y, 6, unit.hitSize / 2f);
        if (mouseEnable(unit)){
            Drawf.tri(unit.x, unit.y , unit.hitSize * 0.3f, unit.hitSize * 0.15f, unit.rotation);
            Lines.stroke(Mathf.clamp(unit.hitSize * 0.025f, 0.75f, 2.5f));
            Lines.square(unit.x, unit.y , unit.hitSize * 0.3f / Mathf.sqrt2, unit.rotation + 45);
        }
        Draw.z(MinfoLayer.detailRange);

        //todo clamp here
        float innerLength = unit.hitSize * 0.5f - Math.min(unit.hitSize * 0.05f, 2f);
        float innerWidth = Math.min(unit.hitSize * 0.1f, 5f);
        float x_innerOffset = Math.max(unit.hitSize * 0.5f - 2f, unit.hitSize * 0.45f);

        MinfoDraw.drawSideBar(
            unit.x - x_innerOffset, unit.y,
            innerLength, innerWidth, 1, Pal.gray, false
        );
        MinfoDraw.drawSideBar(
            unit.x - x_innerOffset, unit.y,
            innerLength, innerWidth, unit.health / unit.maxHealth, Pal.health, false
        );
        //over damage count
        MinfoDraw.drawSideBar(
            unit.x - x_innerOffset, unit.y,
            innerLength, innerWidth, -unit.health / unit.maxHealth, Pal.shield, false
        );


        float shieldCapacity = 0f;
        boolean hasShield = false;
        for(Ability ability: unit.abilities){
            if (ability instanceof ForceFieldAbility forceAbility){
                shieldCapacity = forceAbility.max;
                hasShield = true;
                break;
            }
        }
        MinfoDraw.drawSideBar(
            unit.x + x_innerOffset, unit.y,
            innerLength, innerWidth, 1, Pal.gray, true
        );
        if (hasShield){
            MinfoDraw.drawSideBar(
                unit.x + x_innerOffset, unit.y,
                innerLength, innerWidth, unit.shield / shieldCapacity, Pal.techBlue, true
            );
            MinfoDraw.drawSideBar(
                unit.x + x_innerOffset, unit.y,
                innerLength, innerWidth, Math.min(unit.shield / shieldCapacity, shieldCapacity / unit.shield), Pal.heal, true
            );
            MinfoDraw.drawSideBar(
                unit.x + x_innerOffset, unit.y,
                innerLength, innerWidth, -unit.shield / shieldCapacity, Pal.shield, true
            );
        }else {
            MinfoDraw.drawSideBar(
                unit.x + x_innerOffset, unit.y,
                innerLength, innerWidth, (unit.shield + unit.health) / unit.maxHealth, Pal.techBlue, true
            );
            MinfoDraw.drawSideBar(
                unit.x + x_innerOffset, unit.y,
                innerLength, innerWidth, Math.min(unit.health / unit.maxHealth,  unit.maxHealth / (unit.health + unit.shield)), Pal.health, true
            );

        }
    }

    public static boolean mouseEnable(Unit unit){
        Tmp.v1.set(Core.input.mouse());
        return unit == Units.closestOverlap(null, Core.input.mouseWorldX(), Core.input.mouseWorldY(), 5f, u -> !u.isLocal() && u.displayable());
    }
}
