package Minfo;

import Minfo.graphics.BuildsDraw;
import Minfo.graphics.MinfoShader;
import Minfo.ui.MinfoFragment;
import Minfo.util.MinfoVars;
import Minfo.util.ReflectionUtils;
import arc.Core;
import arc.Events;
import arc.func.Boolf;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.FogControl;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Groups;
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

        /*
        Events.on(EventType.WorldLoadEvent.class, e -> {
            Time.run(60f, () -> {
                Events.run(EventType.Trigger.preDraw, () -> {
                    //ReflectionUtils.setValue(ReflectionUtils.getField(Shaders.class, "fog"), Shaders.fog, MinfoShader.fogShaderClear);


                    if(player.team() != Team.get(255)){

                        Object[] value = ReflectionUtils.getValue(ReflectionUtils.getField(fogControl.getClass(), "fog"), fogControl);
                        //Object value255 = fogControl.getDiscovered(Team.get(255));
                        if (value != null){
                            //Log.info(value.length + " " + (value[0] != null? "true": "false") + " " + (value[1] != null? "true": "false") + " " + (value[2] != null? "true": "false") + " " + (value[3] != null? "true": "false") + " " + (value[4] != null? "true": "false") + " " + (value[5] != null? "true": "false"));
                            if(value[255] != null){

                            }
                            value[player.team().id] = null;
                            ReflectionUtils.setValue(ReflectionUtils.getField(fogControl.getClass(), "fog"), fogControl, null);
                            //Log.info(value.length + " " + (value[0] != null? "true": "false") + " " + (value[1] != null? "true": "false") + " " + (value[2] != null? "true": "false") + " " + (value[3] != null? "true": "false") + " " + (value[4] != null? "true": "false") + " " + (value[5] != null? "true": "false"));
                            //Log.info(player.team().id + " changed");
                        }
                    }
                });
            });
        });

         */

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
