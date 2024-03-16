package Minfo;

import Minfo.graphics.BuildsDraw;
import Minfo.graphics.MinfoMark;
import Minfo.graphics.MinfoShader;
import Minfo.graphics.UnitsDraw;
import Minfo.ui.MinfoFragment;
import Minfo.util.MinfoVars;
import Minfo.util.ReflectionUtils;
import arc.Core;
import arc.Events;
import arc.func.Boolf;
import arc.graphics.g2d.Draw;
import arc.input.KeyCode;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.core.World;
import mindustry.game.EventType;
import mindustry.game.FogControl;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Shaders;
import mindustry.mod.Mod;
import mindustry.mod.Mods;
import mindustry.world.blocks.defense.turrets.Turret;

import static mindustry.Vars.*;

public class ModMain extends Mod{
    public static Mods.LoadedMod MOD;

    public ModMain(){
        Events.run(EventType.ClientLoadEvent.class, () -> {
            Core.app.post(MinfoFragment::build);
            Core.app.post(BuildsDraw::init);
            MinfoShader.init();
        });

        Events.run(EventType.Trigger.update, () -> {
            //Log.info(BuildsDraw.tiles.size);
            Core.app.post(BuildsDraw::load);
        });

        Events.run(EventType.Trigger.update, () -> {
            Core.app.post(() -> {
                if (Core.input.keyTap(KeyCode.l)){
                    control.input.logicCutscene = true;
                    control.input.logicCamPan.lerpDelta(new Vec2(World.unconv(250), World.unconv(250)), 2f);
                    Time.run(120f, () -> control.input.logicCutscene = false);
                }
            });
        });

        Events.run(EventType.Trigger.draw, () -> {
            if (control.input.logicCutscene){
                Draw.z(Layer.effect);
                Draw.color(Pal.accent);
                Draw.rect(MinfoMark.mark, World.unconv(250), World.unconv(250));
                Draw.reset();
            }
        });
        Events.run(EventType.Trigger.draw, () -> {
            BuildsDraw.draw();
            UnitsDraw.draw();
        });
    }
    @Override
    public void init(){
        MOD = Vars.mods.getMod(getClass());
        MinfoMark.load();
    }
    @Override
    public void loadContent() {
        super.loadContent();

        MinfoVars.init();
    }

}
