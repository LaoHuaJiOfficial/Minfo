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



    public static void init(){
        mendShader = new RangeShader();
        overdriveShader = new RangeShader();
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
