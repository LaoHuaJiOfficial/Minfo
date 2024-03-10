package Minfo.ui;

import arc.scene.style.Drawable;
import arc.scene.ui.Button;
import arc.scene.ui.ButtonGroup;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;

import static mindustry.Vars.ui;

public class MinfoFragment {
    static Table minfo = new Table();
    static Table buttons = new Table();
    static Table options = new Table();
    static ButtonGroup<Button> group = new ButtonGroup<>();

    public static void build(){
        options.table(t -> {
            t.image().color(Pal.gray).size(392, 4);
            }
        ).size(392, 4);
        buttons.bottom().left();
        buttons.table(buttonTable -> {
            buttonTable.background(Tex.pane2);
            addButton(buttonTable, Icon.modePvp, "1", () -> {
                options.table(t -> {
                    t.setBackground(Tex.pane);
                    t.label(() -> "[accent]Game Info").top().left().row();
                    t.label(() -> "[accent]Game Info").top().left().row();
                    t.label(() -> "[accent]Game Info").top().left().row();
                    t.label(() -> "[accent]Game Info").top().left().row();
                    t.label(() -> "[accent]Game Info").top().left().row();
                    t.label(() -> "[accent]Game Info").top().left().row();
                    t.button(Icon.warning, Styles.defaulti, () -> {
                        ui.showInfo("frog");
                    });
                }).size(392, 200);
            });
            addButton(buttonTable, Icon.save, "2", () -> {
                options.table(t -> {
                    t.setBackground(Tex.pane);
                    t.label(() -> "[accent]Lucky Clover").top().left().row();
                    t.button(Icon.warning, Styles.defaulti, () -> {
                        ui.showInfo("CN-ARC");
                    });
                }).size(392, 192);
            });
            addButton(buttonTable, Icon.add, "3", () -> {
                options.table(t -> {
                    t.setBackground(Tex.pane);
                    t.label(() -> "[accent]Emperor").top().left().row();
                }).size(392, 192);
            });
            addButton(buttonTable, Icon.cancel, "4", () -> {
                options.table(t -> {
                    t.setBackground(Tex.pane);
                    t.label(() -> "[accent]Anuke").top().left().row();
                }).size(392, 192);
            });
            addButton(buttonTable, Icon.redo, "5", () -> {});
            addButton(buttonTable, Icon.exit, "6", () -> {});
            addButton(buttonTable, Icon.paste, "7", () -> {});
            addButton(buttonTable, Icon.edit, "8", () -> {});
        }).size(392, 52).marginTop(-2).marginBottom(2);
        buttons.visible(() -> !ui.chatfrag.shown());

        group.setMinCheckCount(0);
        group.uncheckAll();

        minfo.bottom().left();
        minfo.add(options).row();
        minfo.add(buttons).row();

        ui.hudGroup.addChild(minfo);
    }
    private static void addButton(Table table, Drawable icon, String tooltip, Runnable option){
        Button button = new Button(Styles.clearNoneTogglei);
        button.table(cont -> {
            cont.add(new Image(icon));
        }).size(Vars.iconXLarge).tooltip(tooltip);
        table.add(button);
        group.add(button);

        button.changed(() -> {
            if(button.isChecked()) {
                options.clear();
                option.run();
            }else{
                options.clear();
                options.table(t -> t.image().color(Pal.gray).size(392, 4)).size(392, 4);
            }}
        );
    }
}
