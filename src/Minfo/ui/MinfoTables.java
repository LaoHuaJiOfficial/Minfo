package Minfo.ui;

import arc.scene.ui.Button;
import arc.scene.ui.ButtonGroup;
import arc.scene.ui.layout.Collapser;
import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;
import mindustry.ui.Styles;

public class MinfoTables {
    public static final float tableLength = 392f;
    public static final float optionLength = 368f;
    public static final float optionSubLength = 280f;
    public static final float optionHeight = 265f;
    public static final float tableLineStroke = 4f;


    public static Table minfo;
    public static Table buttons;
    public static Table contentTable;
    public static ButtonGroup<Button> group;


    public static void init(){
        minfo = new Table();
        buttons = new Table();
        contentTable = new Table();
        group = new ButtonGroup<>();
    }


    public static void addOptionLabel(Table cont, String text){
        cont.table(t -> {
            t.left().top().margin(0,10,0,0);
            t.label(() -> text);
        }).size(optionLength, 0).pad(5,0,5,0).row();
    }
    public static void addOptions(Table cont, boolean option, String text, Runnable runnable){
        cont.table(t -> {
            Table main = new Table();
            main.left().top().margin(0,6,0,0);
            main.check("", option, var -> runnable.run()).update(b -> b.setChecked(option));
            main.label(() -> text).padLeft(5).wrap().size(280,40);
            t.add(main);
        }).size(optionLength, 0).pad(0,0,0,0).row();
    }

    public static void addExpandOptions(Table cont, boolean option, String text, Runnable runnable, Option...subOptions){
        cont.table(t -> {
            Table subCont = new Table();
            subCont.left().top().margin(0,6,0,-6);
            Collapser sub = new Collapser(subCont, true);
            sub.setDuration(0.1f);

            Table main = new Table();
            main.left().top().margin(0,6,0,0);
            main.check("", option, var -> runnable.run()).update(b -> b.setChecked(option));
            main.label(() -> text).padLeft(5).wrap().size(252,40);
            main.button(Icon.downOpen, Styles.emptyi, () -> sub.toggle(false)).right()
                .update(i -> i.getStyle().imageUp = (!sub.isCollapsed() ? Icon.upOpen : Icon.downOpen));

            for (Option subOption: subOptions){
                subCont.table(st -> {
                    Table subMain = new Table();
                    subMain.left().top().margin(0,6,0,0);
                    subMain.check("", subOption.option, var -> subOption.runnable.run()).update(b -> b.setChecked(subOption.option));
                    subMain.label(() -> subOption.text).padLeft(5).wrap().size(260,40);
                    st.add(subMain);
                }).row();
            }
            t.add(main).row();
            t.add(sub).row();
        }).size(368, 0).pad(0,0,0,0).row();
    }

    public static Option addSubOptions(boolean option, String text, Runnable runnable){
        return new Option(option, text, runnable);
    }

    public static class Option {
        public boolean option;
        public String text;
        public Runnable runnable;
        public Option(boolean option, String text, Runnable runnable){
            this.option = option;
            this.text = text;
            this.runnable = runnable;
        }
    }
}
