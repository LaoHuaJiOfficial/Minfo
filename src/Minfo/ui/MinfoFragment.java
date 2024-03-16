package Minfo.ui;

import Minfo.MinfoVars;
import arc.Core;
import arc.scene.Element;
import arc.scene.style.Drawable;
import arc.scene.ui.*;
import arc.scene.ui.layout.Collapser;
import arc.scene.ui.layout.Stack;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.input.DesktopInput;
import mindustry.input.MobileInput;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.ui.ItemDisplay;
import mindustry.ui.Styles;

import static arc.Core.input;
import static mindustry.Vars.*;

public class MinfoFragment {
    static Table minfo = new Table();
    static Table buttons = new Table();
    static Table options = new Table();
    static ButtonGroup<Button> group = new ButtonGroup<>();

    public static void build(){
        options.table(t -> t.image().color(Pal.gray).size(392, 4)).size(392, 4);

        buttons.bottom().left();
        buttons.table(buttonTable -> {
            buttonTable.background(Tex.pane2);
            addButton(buttonTable, Icon.zoom, "Info Manager", () -> {
                //Info Manager Inner Table
                Table full = new Table();
                //full.setBackground(Tex.alphaaaa);
                Table cont = new Table();
                //cont.setBackground(Tex.alphaaaa);

                full.table().fill();

                Runnable rebuild = () -> {

                    cont.clear();

                    cont.table(t -> {
                        t.left().top().margin(0,10,0,0);
                        t.label(() -> "[accent]Range Indicator: ");
                    }).size(368, 0).pad(5,0,5,0).row();

                    cont.table(t -> {
                        Table main = new Table();
                        main.left().top().margin(0,6,0,0);
                        main.check("", MinfoVars.ShowMendRange, var -> {
                            MinfoVars.ShowMendRange = !MinfoVars.ShowMendRange;
                        });
                        main.label(() -> "Show Mend Range").padLeft(5).wrap().size(280,40);
                        t.add(main);
                    }).size(368, 0).pad(0,0,0,0).row();

                    cont.table(t -> {
                        Table main = new Table();
                        main.left().top().margin(0,6,0,0);
                        main.check("", MinfoVars.ShowOverdriveRange, var -> {
                            MinfoVars.ShowOverdriveRange = !MinfoVars.ShowOverdriveRange;
                        });
                        main.label(() -> "Show Overdrive Range").padLeft(5).wrap().size(280,40);
                        t.add(main);

                    }).size(368, 0).pad(0,0,0,0).row();

                    cont.table(t -> {
                        Table subCont = new Table();
                        subCont.left().top().margin(0,6,0,-6);
                        Collapser sub = new Collapser(subCont, true);
                        sub.setDuration(0.1f);

                        Table main = new Table();
                        main.left().top().margin(0,6,0,0);
                        main.check("", MinfoVars.ShowTurretRange, var -> {
                            MinfoVars.ShowTurretRange = !MinfoVars.ShowTurretRange;
                            if(MinfoVars.ShowTurretRange){
                                MinfoVars.ShowTurretAirRange = MinfoVars.ShowTurretGroundRange = true;
                            }else {
                                MinfoVars.ShowTurretAirRange = MinfoVars.ShowTurretGroundRange = false;
                            }
                        }).update(i -> {
                            i.setChecked(MinfoVars.ShowTurretRange);
                        });
                        main.label(() -> "Show Turret Range").padLeft(5).wrap().size(252,40);

                        main.button(Icon.downOpen, Styles.emptyi, () -> sub.toggle(false)).right()
                            .update(i -> i.getStyle().imageUp = (!sub.isCollapsed() ? Icon.upOpen : Icon.downOpen));



                        subCont.table(st -> {
                            Table subMain = new Table();
                            subMain.left().top().margin(0,6,0,0);
                            subMain.check("", MinfoVars.ShowTurretGroundRange, var -> {
                                MinfoVars.ShowTurretGroundRange = !MinfoVars.ShowTurretGroundRange;
                                if (MinfoVars.ShowTurretGroundRange){
                                    MinfoVars.ShowTurretRange = true;
                                }
                                if (!MinfoVars.ShowTurretAirRange && !MinfoVars.ShowTurretGroundRange){
                                    MinfoVars.ShowTurretRange = false;
                                }
                            }).update(i -> {
                                i.setChecked(MinfoVars.ShowTurretGroundRange);
                            });
                            subMain.label(() -> "Show Target-Ground Turret Range").padLeft(5).wrap().size(260,40);
                            st.add(subMain);
                        }).row();

                        subCont.table(st -> {
                            Table subMain = new Table();
                            subMain.left().top().margin(0,6,0,0);
                            subMain.check("", MinfoVars.ShowTurretAirRange, var -> {
                                MinfoVars.ShowTurretAirRange = !MinfoVars.ShowTurretAirRange;
                                if (MinfoVars.ShowTurretAirRange){
                                    MinfoVars.ShowTurretRange = true;
                                }
                                if (!MinfoVars.ShowTurretAirRange && !MinfoVars.ShowTurretGroundRange){
                                    MinfoVars.ShowTurretRange = false;
                                }
                            }).update(i -> {
                                i.setChecked(MinfoVars.ShowTurretAirRange);
                            });
                            subMain.label(() -> "Show Target-Air Turret Range").padLeft(5).wrap().size(260,40);
                            st.add(subMain);
                        }).row();

                        t.add(main).row();
                        t.add(sub).row();
                    }).size(368, 0).pad(0,0,0,0).row();

                    cont.table(t -> {
                        Table main = new Table();
                        main.left().top().margin(0,6,0,0);
                        main.check("", MinfoVars.DetailMode, var -> {
                            MinfoVars.DetailMode = !MinfoVars.DetailMode;
                        });
                        main.label(() -> "Enable Detail Mode").padLeft(5).wrap().size(280,40);
                        t.add(main);
                    }).size(368, 0).pad(0,0,0,0).row();

                };
                rebuild.run();

                options.table(t -> {
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
                    //pane.cancelTouchFocus();
                    pane.setScrollingDisabled(true, false);
                    pane.setOverscroll(false, false);
                    full.add(pane).maxHeight(265f);

                    t.top().add(full);
                }).size(392, 265);
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
            addButton(buttonTable, Icon.redo, "5", () -> {
                options.clear();
                options.table(t -> t.image().color(Pal.gray).size(392, 4)).size(392, 4);
            });
            addButton(buttonTable, Icon.exit, "6", () -> {
                options.clear();
                options.table(t -> t.image().color(Pal.gray).size(392, 4)).size(392, 4);
            });
            addButton(buttonTable, Icon.paste, "7", () -> {
                options.clear();
                options.table(t -> t.image().color(Pal.gray).size(392, 4)).size(392, 4);
            });
            addButton(buttonTable, Icon.edit, "8", () -> {
                options.clear();
                options.table(t -> t.image().color(Pal.gray).size(392, 4)).size(392, 4);
            });
        }).size(392, 52).marginTop(-2).marginBottom(2);

        group.setMinCheckCount(0);
        group.uncheckAll();

        minfo.bottom().left();
        minfo.add(options).row();
        minfo.add(buttons).row();

        minfo.visible(() -> !ui.chatfrag.shown());

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

    private static void optionAddButton(Table table, Runnable runnable){

    }
}
