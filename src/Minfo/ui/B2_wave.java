package Minfo.ui;

import arc.Core;
import arc.scene.Element;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import mindustry.core.Logic;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.net.Packets;
import mindustry.ui.Styles;

import static Minfo.ui.MinfoTables.contentTable;
import static mindustry.Vars.*;

public class B2_wave extends ButtonOptions {
    public static void build(){
        Table full = new Table();
        Table waveLeft = new Table();
        Table waveList = new Table();

        Table waveInfo = new Table();

        Runnable rebuild = () -> {
            full.clear();
            waveLeft.table(t -> {
                t.top().left();
                t.table(b -> {
                    b.button(Icon.pause, Styles.emptyi, () -> {
                        player.unit().controller();
                    }).size(40, 40);
                    b.button(Icon.add, Styles.emptyi, () -> {
                        for(int i = 0; i < 5; i++){
                            if(net.client() && player.admin){
                                Call.adminRequest(player, Packets.AdminAction.wave, null);
                            }else{
                                logic.skipWave();
                            }
                        }
                    }).size(40, 40);
                    b.button(Icon.cancel, Styles.emptyi, () -> {
                        for(int i = 0; i < 10; i++){
                            if(net.client() && player.admin){
                                Call.gameOver(Team.sharded);
                            }else{
                                Logic.gameOver(Team.sharded);
                            }
                        }
                    }).size(40, 40);
                }).size(120, 40).padTop(4).padLeft(4).margin(0).row();
                t.image().color(Pal.gray).fill().size(124,4).pad(0).margin(0).row();

                waveList.table(list -> {
                    list.add("Wave 1").row();
                    list.add("Wave 2").row();
                    list.add("Wave 3").row();
                    list.add("Wave 4").row();
                    list.add("Wave 5").row();
                    list.add("Wave 6").row();
                    list.add("Wave 7").row();
                    list.add("Wave 8").row();
                    list.add("Wave 9").row();
                    list.add("Wave 10").row();
                    list.add("Wave 11").row();
                    list.add("Wave 12").row();
                    list.add("Wave 13").row();
                    list.add("Wave 14").row();
                    list.add("Wave 15").row();
                    list.add("Wave 16").row();
                    list.add("Wave 17").row();
                    list.add("Wave 18").row();
                    list.add("Wave 19").row();
                    list.add("Wave 20").row();
                });

                ScrollPane pane = new ScrollPane(waveList, Styles.smallPane);
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

                t.add(pane).maxHeight(220);


            }).size(124, 265);



            waveInfo.table(i -> {
                i.add("Select A Wave...");
            }).size(260, 265);

            full.table(t -> {
                t.top().left();
                t.add(waveLeft);
                t.image().color(Pal.gray).fill().size(4,265).pad(0).margin(0);
                t.add(waveInfo);
            }).size(392, 265);
        };
        rebuild.run();
        contentTable.table(t -> {
            t.setBackground(Tex.pane);
            t.add(full);
        }).size(392,265);
    }
}
