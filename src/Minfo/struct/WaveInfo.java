package Minfo.struct;

import arc.struct.ObjectMap;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.type.ItemStack;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;

public class WaveInfo {
    public static int waveIndex;
    public static Seq<UnitInfo> waveInfo;
    public static class UnitInfo{
        public UnitType unit;
        public int amount;
        public @Nullable ItemStack item;
        public @Nullable StatusEffect status;
        public UnitInfo(UnitType unit, int amount, ItemStack item, StatusEffect status){
            this.unit = unit;
            this.amount = amount;
            this.item = item;
            this.status = status;
        }
    }
}
