package Minfo.util;

import Minfo.struct.WaveInfo;
import Minfo.ui.B0_display;
import Minfo.ui.MinfoFragment;
import Minfo.ui.B2_wave;
import arc.struct.Seq;

public class MinfoVars {
    public static boolean ShowMendRange = false;
    public static boolean ShowOverdriveRange = false;
    public static boolean ShowTurretRange = false;
    public static boolean ShowTurretGroundRange = false;
    public static boolean ShowTurretAirRange = false;
    public static boolean DetailMode = false;
    public static boolean DetailGroundUnit = true;
    public static boolean DetailAirUnit = true;
    public static boolean DetailLegUnit = false;


    //UI List
    public static MinfoFragment minfoFragment;
    //Game Info List
    public static Seq<WaveInfo> waveInfo;
    public static void init(){
        minfoFragment = new MinfoFragment();

        waveInfo = new Seq<>();
    }

}
