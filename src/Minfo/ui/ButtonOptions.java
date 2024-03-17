package Minfo.ui;

import mindustry.graphics.Pal;

import static Minfo.ui.MinfoTables.*;

public class ButtonOptions {
    public static void build(){
        contentTable.clear();
        contentTable.table(t -> t.image().color(Pal.gray).size(tableLength, tableLineStroke)).size(tableLength, tableLineStroke);
    }
}
