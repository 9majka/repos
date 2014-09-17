package com.mygdx.sample.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.sample.Drop;

public class DesktopLauncher {
    public static void main (String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Drop";
        config.width = 320;
        config.height = 480;
        new LwjglApplication(new Drop(), config);
    }
}
