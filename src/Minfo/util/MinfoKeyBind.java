package Minfo.util;

import arc.*;
import arc.KeyBinds.*;
import arc.input.InputDevice.*;
import arc.input.*;

public enum MinfoKeyBind implements KeyBind{
    MINFO_KEY_BIND((KeyCode.altLeft), "Minfo"),

    ;
    private final KeybindValue defaultValue;
    private final String category;

    MinfoKeyBind(KeybindValue defaultValue, String category){
        this.defaultValue = defaultValue;
        this.category = category;
    }

    MinfoKeyBind(KeybindValue defaultValue){
        this(defaultValue, null);
    }

    @Override
    public KeybindValue defaultValue(DeviceType type){
        return defaultValue;
    }

    @Override
    public String category(){
        return category;
    }
}
