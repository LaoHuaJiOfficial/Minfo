package Minfo.util;

import arc.ApplicationListener;
import arc.Core;
import arc.KeyBinds;
import arc.input.InputDevice;
import arc.input.KeyCode;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;

import java.lang.reflect.Field;

import static arc.Core.keybinds;

public class MinfoInputListener implements ApplicationListener {

    @Override
    public void init(){
        try{
            Field definitionsField = KeyBinds.class.getDeclaredField("definitions");
            Field defaultCacheField = KeyBinds.class.getDeclaredField("defaultCache");

            definitionsField.setAccessible(true);
            defaultCacheField.setAccessible(true);

            KeyBinds.KeyBind[] definitionsMinfo = MinfoKeyBind.values();
            KeyBinds.KeyBind[] definitions = (KeyBinds.KeyBind[])definitionsField.get(keybinds);
            Seq<KeyBinds.KeyBind> definitionSeq = new Seq<>(false, definitions.length + definitionsMinfo.length, KeyBinds.KeyBind.class);
            definitionSeq.addAll(definitionsMinfo).addAll(definitions);
            definitionsField.set(keybinds, definitionSeq.toArray());

            @SuppressWarnings("unchecked")
            ObjectMap<KeyBinds.KeyBind, ObjectMap<InputDevice.DeviceType, KeyBinds.Axis>> defaultCache = (ObjectMap<KeyBinds.KeyBind, ObjectMap<InputDevice.DeviceType, KeyBinds.Axis>>)defaultCacheField.get(keybinds);
            for(KeyBinds.KeyBind def : definitionsMinfo){
                defaultCache.put(def, new ObjectMap<>());
                for(InputDevice.DeviceType type : InputDevice.DeviceType.values()){
                    defaultCache.get(def).put(type,
                        def.defaultValue(type) instanceof KeyBinds.Axis ?
                            (KeyBinds.Axis)def.defaultValue(type) : new KeyBinds.Axis((KeyCode)def.defaultValue(type)));
                }
            }


        }catch(NoSuchFieldException | IllegalAccessException e){
            Log.info(e);
        }

        Log.info("loaded");

    }

    public void update(){
        //todo here
        if(Core.input.keyTap(KeyCode.altLeft)){
            Log.info("tap");
        }
    }

}
