package Minfo.graphics;

import Minfo.MinfoVars;
import Minfo.util.ReflectionUtils;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.blocks.defense.OverdriveProjector;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.Turret;

import static mindustry.Vars.*;

public class BuildsDraw {
    public static Seq<Tile> tiles = new Seq<>();

    public static void init(){
        for(Block block: content.blocks()){
            if (block instanceof MendProjector b){
                b.clipSize += b.range + b.phaseRangeBoost;
                Log.info("Minfo Change: " + b.name + "'s ClipSize change: " + (b.clipSize - b.range - b.phaseRangeBoost) + " -> " + b.clipSize);
            }
            if (block instanceof OverdriveProjector b){
                b.clipSize += b.range + b.phaseRangeBoost;
                Log.info("Minfo Change: " + b.name + "'s ClipSize change: " + (b.clipSize - b.range - b.phaseRangeBoost) + " -> " + b.clipSize);
            }
            if (block instanceof OverdriveProjector b){
                b.clipSize += b.range + b.phaseRangeBoost;
                Log.info("Minfo Change: " + b.name + "'s ClipSize change: " + (b.clipSize - b.range - b.phaseRangeBoost) + " -> " + b.clipSize);
            }
            if (block instanceof Turret b){
                if (b instanceof ItemTurret ib){
                    float extraRange = 0f;
                    for (var ammoTypes: ib.ammoTypes){
                        extraRange = Math.max(ammoTypes.value.rangeChange, extraRange);
                    }
                    b.clipSize += (b.range + extraRange) * 2;
                    //todo add range override
                    Log.info("Minfo Change: " + b.name + "'s ClipSize change: " + (b.clipSize - b.range) + " -> " + b.clipSize);
                }else {
                    b.clipSize += b.range * 2;
                    Log.info("Minfo Change: " + b.name + "'s ClipSize change: " + (b.clipSize - b.range) + " -> " + b.clipSize);
                }
            }
        }
    }

    public static void load(){

    }

    public static void draw(){
        //load shader
        MinfoShader.load();

        tiles = ReflectionUtils.getValue(ReflectionUtils.getField(renderer.blocks.getClass(), "tileview"), renderer.blocks);
        for (Tile tile: tiles){
            if (tile.build instanceof MendProjector.MendBuild b){drawMender(b);}
            if (tile.build instanceof OverdriveProjector.OverdriveBuild b){drawOverdriver(b);}
            if (tile.build instanceof Turret.TurretBuild b){drawTurret(b);}
        }
        //todo may add a Fx like ForceShrink?
    }

    public static void drawMender(MendProjector.MendBuild b){
        if (!MinfoVars.ShowMendRange) return;
        //use the clipSize to get the boost range bruh
        float clipSize;
        clipSize = Math.max(b.block.size * (tilesize + 2f), b.block.size * tilesize);
        clipSize = Math.max(clipSize, b.block.size * tilesize);
        if(b.block.hasLiquids && b.block.drawLiquidLight){clipSize = Math.max(b.block.size * 30f * 2f, clipSize);}
        if(b.block.emitLight){clipSize = Math.max(clipSize, b.block.lightRadius * 2f);}

        float boostRange = b.block().clipSize - b.range() - clipSize;
        Color color = Pal.heal.cpy();
        Color phaseColor = Color.valueOf("ffd59e");

        if(renderer.animateShields && MinfoShader.mendShader != null){
            Draw.z(MinfoLayer.mendRange + b.phaseHeat * 0.001f);
            Draw.color(color.lerp(phaseColor, b.phaseHeat * 0.45f));
            Fill.circle(b.x, b.y, b.range() * b.heat + boostRange * b.phaseHeat);
            Draw.reset();
        }else {
            Draw.z(Layer.block + 20);
            Draw.color(color.lerp(phaseColor, b.phaseHeat * 0.45f));
            Lines.stroke(1f);
            Lines.circle(b.x, b.y, b.range() * b.heat + boostRange * b.phaseHeat);
            Draw.reset();
        }
    }

    public static void drawOverdriver(OverdriveProjector.OverdriveBuild b){
        if (!MinfoVars.ShowOverdriveRange) return;
        //use the clipSize to get the boost range bruh
        float clipSize;
        clipSize = Math.max(b.block.size * (tilesize + 2f), b.block.size * tilesize);
        clipSize = Math.max(clipSize, b.block.size * tilesize);
        if(b.block.hasLiquids && b.block.drawLiquidLight){clipSize = Math.max(b.block.size * 30f * 2f, clipSize);}
        if(b.block.emitLight){clipSize = Math.max(clipSize, b.block.lightRadius * 2f);}

        float boostRange = b.block().clipSize - b.range() - clipSize;
        Color color = Color.valueOf("feb380");
        Color phaseColor = Color.valueOf("ffd59e");

        if(renderer.animateShields && MinfoShader.overdriveShader != null){
            Draw.z(MinfoLayer.overdriveRange + b.phaseHeat * 0.001f);
            Draw.color(color.lerp(phaseColor, b.phaseHeat * 0.8f));
            Fill.circle(b.x, b.y, b.range() * b.heat + boostRange * b.phaseHeat);
            Draw.reset();
        }else {
            Draw.z(Layer.block + 20);
            Draw.color(color.lerp(phaseColor, b.phaseHeat * 0.8f));
            Lines.stroke(1f);
            Lines.circle(b.x, b.y, b.range() * b.heat + boostRange * b.phaseHeat);
            Draw.reset();
        }
    }

    public static void drawTurret(Turret.TurretBuild b){
        Turret t = (Turret) b.block;
        if(MinfoVars.ShowTurretRange){
            if((t.targetAir && MinfoVars.ShowTurretAirRange) || (t.targetGround && MinfoVars.ShowTurretGroundRange)){
                Draw.z(MinfoLayer.turretRange);
                Draw.color(Pal.accent);
                Fill.circle(b.x, b.y, b.range());
                Draw.reset();
            }
        }
    }
}
