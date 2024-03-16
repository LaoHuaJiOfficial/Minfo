package Minfo.graphics;

import Minfo.ModMain;
import arc.Core;
import arc.files.Fi;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.gl.Shader;
import arc.scene.ui.layout.Scl;
import arc.util.Time;
import mindustry.graphics.Shaders;
import mindustry.mod.Mods;

import static mindustry.Vars.renderer;

public class MinfoShader {

    public static RangeShader mendShader;
    public static RangeShader overdriveShader;
    public static TurretShader turretShader;
    public static DetailShader detailShader;
    //public static Shaders.FogShader fogShaderClear;

    public static void init(){
        mendShader = new RangeShader();
        overdriveShader = new RangeShader();
        turretShader = new TurretShader();
        detailShader = new DetailShader();
        //fogShaderClear = new Shaders.FogShader();
    }

    public static void load(){
        if(renderer.animateShields && mendShader != null) {
            Draw.drawRange(MinfoLayer.mendRange, 0.1f, () -> renderer.effectBuffer.begin(Color.clear), () -> {
                renderer.effectBuffer.end();
                renderer.effectBuffer.blit(mendShader);
            });
        }
        if(renderer.animateShields && mendShader != null) {
            Draw.drawRange(MinfoLayer.overdriveRange, 0.1f, () -> renderer.effectBuffer.begin(Color.clear), () -> {
                renderer.effectBuffer.end();
                renderer.effectBuffer.blit(overdriveShader);
            });
        }
        if(renderer.animateShields && turretShader != null) {
            Draw.drawRange(MinfoLayer.turretRange, 0.1f, () -> renderer.effectBuffer.begin(Color.clear), () -> {
                renderer.effectBuffer.end();
                renderer.effectBuffer.blit(turretShader);
            });
        }

        //todo use a new EffectBuffer
        /*
        if(renderer.animateShields && detailShader != null) {
            Draw.drawRange(MinfoLayer.detailRange, 10f, () -> renderer.effectBuffer.begin(Color.clear), () -> {
                renderer.effectBuffer.end();
                renderer.effectBuffer.blit(detailShader);
            });
        }

         */

    }

    public static class RangeShader extends ModShader {
        public RangeShader(){
            super("screenspace", "range");
        }

        @Override
        public void apply(){
            setUniformf("u_dp", Scl.scl(1f));
            setUniformf("u_time", Time.time / Scl.scl(1f));
            setUniformf("u_offset",
                Core.camera.position.x - Core.camera.width / 2,
                Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_texsize", Core.camera.width, Core.camera.height);
            setUniformf("u_invsize", 1f/Core.camera.width, 1f/Core.camera.height);
        }
    }

    public static class TurretShader extends ModShader {
        public TurretShader(){
            super("screenspace", "outline");
        }

        @Override
        public void apply(){
            setUniformf("u_dp", Scl.scl(1f));
            setUniformf("u_time", Time.time / Scl.scl(1f));
            setUniformf("u_offset",
                Core.camera.position.x - Core.camera.width / 2,
                Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_texsize", Core.camera.width, Core.camera.height);
            setUniformf("u_invsize", 1f/Core.camera.width, 1f/Core.camera.height);
        }
    }

    public static class DetailShader extends ModShader {
        public DetailShader(){
            super("screenspace", "detail");
        }
        @Override
        public void apply() {
            super.apply();
        }
    }

    public static class FogShader extends ModShader {
        public FogShader(){
            super("default", "screenspace");
        }
    }
    public static class FogShaderClear extends Shaders.LoadShader {
        public FogShaderClear(){
            super("screenspace", "default");
        }
    }

    public static class ModShader extends Shader {
        public ModShader(String vert, String frag){
            super(getShaderFi(vert + ".vert"), getShaderFi(frag + ".frag"));
        }
    }

    public static Fi getShaderFi(String file){
        Mods.LoadedMod mod = ModMain.MOD;

        Fi shaders = mod.root.child("shaders");
        if(shaders.exists()){
            if(shaders.child(file).exists())return shaders.child(file);
        }

        return Shaders.getShaderFi(file);
    }
}
