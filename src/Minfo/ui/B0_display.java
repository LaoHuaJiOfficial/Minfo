package Minfo.ui;

import Minfo.util.MinfoVars;
import arc.Core;
import arc.scene.Element;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Collapser;
import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;

import static Minfo.ui.MinfoTables.*;

public class B0_display extends ButtonOptions{
    public static void build(){
        Table full = new Table();
        Table cont = new Table();

        full.table().fill();

        Runnable rebuild = () -> {
            cont.clear();

            addOptionLabel(cont, "[accent]Range Indicator");
            addOptions(cont, MinfoVars.ShowMendRange, "Show Mend Range", () -> MinfoVars.ShowMendRange = !MinfoVars.ShowMendRange);
            addOptions(cont, MinfoVars.ShowOverdriveRange, "Show Overdrive Range", () -> MinfoVars.ShowOverdriveRange = !MinfoVars.ShowOverdriveRange);
            addExpandOptions(cont, MinfoVars.ShowTurretRange, "Show Turret Range", () -> {
                    MinfoVars.ShowTurretRange = !MinfoVars.ShowTurretRange;
                    if(MinfoVars.ShowTurretRange){
                        MinfoVars.ShowTurretAirRange = MinfoVars.ShowTurretGroundRange = true;
                    }else {
                        MinfoVars.ShowTurretAirRange = MinfoVars.ShowTurretGroundRange = false;
                    }
                },
                addSubOptions(MinfoVars.ShowTurretGroundRange, "Show Target-Ground Turret Range", () -> {
                    MinfoVars.ShowTurretGroundRange = !MinfoVars.ShowTurretGroundRange;
                    if (MinfoVars.ShowTurretGroundRange){
                        MinfoVars.ShowTurretRange = true;
                    }
                    if (!MinfoVars.ShowTurretAirRange && !MinfoVars.ShowTurretGroundRange){
                        MinfoVars.ShowTurretRange = false;
                    }
                }),
                addSubOptions(MinfoVars.ShowTurretAirRange, "Show Target-Air Turret Range", () -> {
                    MinfoVars.ShowTurretAirRange = !MinfoVars.ShowTurretAirRange;
                    if (MinfoVars.ShowTurretAirRange){
                        MinfoVars.ShowTurretRange = true;
                    }
                    if (!MinfoVars.ShowTurretAirRange && !MinfoVars.ShowTurretGroundRange){
                        MinfoVars.ShowTurretRange = false;
                    }
                }));
            addOptionLabel(cont, "[accent]Detail Mode");
            addOptions(cont, MinfoVars.DetailMode, "Enable Detail Mode", () -> MinfoVars.DetailMode = !MinfoVars.DetailMode);
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
        }).size(tableLength, optionHeight);
    }
}
