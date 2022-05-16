package org.csc133.a3.views;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.GridLayout;
import org.csc133.a3.GameWorld;

public class GlassCockpit extends Container {
    private GameWorld gw;
    private Label heading, speed, fuel, fires, fireSize, damage, loss;
    public GlassCockpit(GameWorld gw) {
        this.gw = gw;
        this.setLayout(new GridLayout(2, 7));
        this.getStyle().setBgTransparency(255);

        this.add("Heading");
        this.add("Speed");
        this.add("Fuel");
        this.add("Fires");
        this.add("Fire Size");
        this.add("Damage");
        this.add("Loss");

        heading = new Label("0");
        speed = new Label("0");
        fuel = new Label("0");
        fires = new Label("0");
        fireSize = new Label("0");
        damage = new Label("0");
        loss = new Label("0");


        this.add(heading);
        this.add(speed);
        this.add(fuel);
        this.add(fires);
        this.add(fireSize);
        this.add(damage);
        this.add(loss);
    }

    public void update() {
        heading.setText(gw.getHeading());
        speed.setText(gw.getSpeed());
        fuel.setText(gw.getFuel());
        fires.setText(String.valueOf(gw.getFireCount()));
        fireSize.setText(gw.getFireSize());
        damage.setText(String.valueOf(gw.getTotalAverageDamage()) + "%");
        loss.setText(gw.getFinancialLoss());

    }
}
