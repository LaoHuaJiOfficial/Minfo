package Minfo.ui;

import Minfo.graphics.MinfoMark;
import arc.Core;
import arc.graphics.g2d.Draw;
import arc.scene.Element;
import arc.scene.style.Drawable;
import arc.scene.ui.*;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.core.World;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;

import static Minfo.ui.MinfoTables.*;
import static arc.Core.camera;
import static mindustry.Vars.*;

public class MinfoFragment {
    public void build(){
        contentTable.table(t -> t.image().color(Pal.gray).size(392, 4)).size(392, 4);

        buttons.bottom().left();
        buttons.table(buttonTable -> {
            buttonTable.background(Tex.pane2);
            addButton(buttonTable, Icon.zoom, "Display Manager", B0_display::build);
            addButton(buttonTable, Icon.save, "Detail Mode", B1_detail::build);
            addButton(buttonTable, Icon.waves, "Wave Viewer", B2_wave::build);
            addButton(buttonTable, Icon.cancel, "Event Viewer", B3_event::build);
            addButton(buttonTable, Icon.redo, "Debug Utils", B4_debug::build);
            addButton(buttonTable, Icon.exit, "Multiplayer Options", B5_multiplayer::build);
            addButton(buttonTable, Icon.paste, "Map Rules", B6_rule::build);
            addButton(buttonTable, Icon.paste, "Other Settings", B7_other::build);
        }).size(tableLength, 52).marginTop(-2).marginBottom(2);

        group.setMinCheckCount(0);
        group.uncheckAll();

        minfo.bottom().left();
        minfo.add(contentTable).row();
        minfo.add(buttons).row();
        minfo.visible(() -> !ui.chatfrag.shown());

        ui.hudGroup.addChild(minfo);
    }
    private void addButton(Table table, Drawable icon, String tooltip, Runnable option){
        Button button = new Button(Styles.clearNoneTogglei);
        button.table(cont -> {
            cont.add(new Image(icon));
        }).size(Vars.iconXLarge).tooltip(tooltip);
        table.add(button);
        group.add(button);

        button.changed(() -> {
            if(button.isChecked()) {
                contentTable.clear();
                option.run();
            }else{
                contentTable.clear();
                contentTable.table(t -> t.image().color(Pal.gray).size(392, 4)).size(392, 4);
            }}
        );
    }
}
