package org.csc133.a3;

import static com.codename1.ui.CN.*;
import com.codename1.system.Lifecycle;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.io.*;
import com.codename1.ui.plaf.*;
import com.codename1.ui.util.Resources;

public class AppMain extends Lifecycle {
    @Override
    public void runApp() {
        new Game();
    }
}