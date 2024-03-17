package Minfo.ui;

import Minfo.graphics.MinfoMark;
import arc.Core;
import arc.graphics.g2d.Draw;
import arc.scene.Element;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import mindustry.core.World;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;

import static Minfo.ui.MinfoTables.contentTable;
import static arc.Core.camera;
import static mindustry.Vars.control;

public class B3_event extends ButtonOptions {
    public static void build(){
        Table full = new Table();
        Table cont = new Table();

        Runnable rebuild = () -> {
            cont.clear();

            cont.table(t -> {
                t.table(text -> {
                    text.label(() -> "Game Time");
                }).size(300,0);

                t.button(Icon.eyeSmall, Styles.emptyi ,48, () -> {
                    camera.position.set(World.unconv(250), World.unconv(250));

                    if (control.input.logicCutscene){
                        Draw.z(Layer.effect);
                        Draw.color(Pal.accent);
                        Draw.rect(MinfoMark.mark, World.unconv(250), World.unconv(250));
                        Draw.reset();
                    }
                });
            }).size(368, 0).pad(0,0,0,0).row();

        };
        rebuild.run();
        contentTable.table(t -> {
            t.setBackground(Tex.pane);
            ScrollPane pane = new ScrollPane(cont, Styles.smallPane);
            pane.update(() -> {
                if(pane.hasScroll()){
                    Element result = Core.scene.hit(Core.input.mouseX(), Core.input.mouseY(), true);
                    if(result == null || !result.isDescendantOf(pane)){
                        Core.scene.setScrollFocus(null);
                    }
                }
            });
            pane.setScrollingDisabled(true, false);
            pane.setOverscroll(false, false);
            full.add(pane).maxHeight(265f);

            t.top().add(full);
        }).size(392, 265);
    }
}
