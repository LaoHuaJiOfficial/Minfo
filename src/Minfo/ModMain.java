package Minfo;

import Minfo.graphics.BuildsDraw;
import Minfo.graphics.MinfoShader;
import Minfo.ui.MinfoFragment;
import Minfo.util.MinfoVars;
import arc.Core;
import arc.Events;
import arc.func.Boolf;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.mod.Mod;
import mindustry.mod.Mods;
import mindustry.world.blocks.defense.turrets.Turret;

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

        //Log.info(BuildsDraw.tiles.size);
        //Core.app.post(BuildsDraw::draw);
        //Log.info("drawed");
        Events.run(EventType.Trigger.draw, BuildsDraw::draw);
    }
    @Override
    public void init(){
        MOD = Vars.mods.getMod(getClass());
    }
    @Override
    public void loadContent() {
        super.loadContent();

        MinfoVars.init();
    }

}
