package Minfo.util;

public class MinfoVars {
    public static MinfoInputListener listener;

    public static void init(){
        listener = new MinfoInputListener();

        listener.init();
    }
}
