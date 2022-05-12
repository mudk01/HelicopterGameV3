package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;
import org.csc133.a3.views.ControlCluster;
import org.csc133.a3.views.MapView;
import org.csc133.a3.views.GlassCockpit;
import org.csc133.a3.commands.*;


public class Game extends Form implements Runnable {
    private GameWorld gw;
    private MapView mapView;
    private GlassCockpit glassCockpit;
    private ControlCluster controlCluster;

    public Game() {
        gw = GameWorld.getInstance();
        mapView = new MapView(gw);
//        mapView.getStyle().setBgColor(ColorUtil.rgb(0, 0, 0));
        mapView.getStyle().setBgTransparency(255);
        glassCockpit = new GlassCockpit(gw);
        controlCluster = new ControlCluster(gw);

        addKeyListener(-92, new DecelerateCommand(gw));
        addKeyListener(-91, new AccelerateCommand(gw));
        addKeyListener(-94, new SteerRightCommand(gw));
        addKeyListener(-93, new SteerLeftCommand(gw));
        addKeyListener('d', new DrinkCommand(gw));
        addKeyListener('f', new FightCommand(gw));
        addKeyListener('Q', new ExitCommand(gw));


        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH, glassCockpit);
        this.add(BorderLayout.CENTER, mapView);
        this.add(BorderLayout.SOUTH, controlCluster);
        UITimer timer = new UITimer(this);
        timer.schedule(50, true, this);

        this.show();
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void run() {
        gw.tick();
        glassCockpit.update();
        mapView.updateLocalTransforms();
        mapView.repaint();
    }
}
