package Minfo;

import Minfo.graphics.BuildsDraw;
import Minfo.graphics.MinfoMark;
import Minfo.graphics.MinfoShader;
import Minfo.graphics.UnitsDraw;
import Minfo.ui.MinfoTables;
import Minfo.util.MinfoVars;
import Minfo.util.MinfoVarsTemp;
import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import mindustry.Vars;
import mindustry.core.World;
import mindustry.game.EventType;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.mod.Mod;
import mindustry.mod.Mods;

import static mindustry.Vars.*;

public class ModMain extends Mod{
    public static Mods.LoadedMod MOD;

    public ModMain(){
        Events.run(EventType.ClientLoadEvent.class, () -> {
            MinfoVars.minfoFragment.build();
            Core.app.post(BuildsDraw::init);
            MinfoShader.init();
        });

        Events.run(EventType.Trigger.update, () -> {
            //Log.info(BuildsDraw.tiles.size);
            Core.app.post(BuildsDraw::load);
        });

        Events.run(EventType.Trigger.update, () -> {

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

        MinfoTables.init();
        MinfoVars.init();
        MinfoMark.load();
    }
    @Override
    public void loadContent() {
        super.loadContent();

        MinfoVarsTemp.init();
    }

}
