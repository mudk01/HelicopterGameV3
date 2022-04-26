package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import org.csc133.a3.GameWorld;
import org.csc133.a3.commands.*;

public class ControlCluster extends Container {
    GameWorld gw;
    Button left, right, fight, exit, drink, brake, accelerate;
    Container leftCont, rightCont, exitCont;
    public ControlCluster(GameWorld gw) {
        this.gw = gw;
        this.setLayout(new BorderLayout());
        this.getStyle().setBgColor(ColorUtil.rgb(0,0,0));

        leftCont = new Container(new BorderLayout());
        rightCont = new Container(new BorderLayout());
        exitCont = new Container(new BorderLayout());

        left = this.buttonMaker(new SteerLeftCommand(gw), "Left");
        right = this.buttonMaker(new SteerRightCommand(gw), "Right");
        fight = this.buttonMaker(new FightCommand(gw), "Fight");
        exit = this.buttonMaker(new ExitCommand(gw), "Exit");
        drink = this.buttonMaker(new DrinkCommand(gw), "Drink");
        brake = this.buttonMaker(new DecelerateCommand(gw), "Brake");
        accelerate = this.buttonMaker(new AccelerateCommand(gw), "Accel");

        leftCont.add(BorderLayout.WEST, left);
        leftCont.add(BorderLayout.CENTER, right);
        leftCont.add(BorderLayout.EAST, fight);
        exitCont.add(BorderLayout.CENTER, exit);
        rightCont.add(BorderLayout.WEST, drink);
        rightCont.add(BorderLayout.CENTER, brake);
        rightCont.add(BorderLayout.EAST, accelerate);
        this.add(BorderLayout.WEST, leftCont);
        this.add(BorderLayout.CENTER, exitCont);
        this.add(BorderLayout.EAST, rightCont);

    }

    Button buttonMaker(Command cmd, String label){
        Button button = new Button(label);
        button.setCommand(cmd);
        button.getAllStyles().setBgTransparency(255);
        return button;
    }

}
