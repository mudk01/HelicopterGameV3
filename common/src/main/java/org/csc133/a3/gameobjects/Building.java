package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameobjects.parts.Rectangle;

import java.util.Random;

//
//import java.util.Random;
//
public class Building extends Fixed {
    private Point newFireLocation, location;
    private int value, damage;
    private Dimension worldSize;
    private Fires fires;
    private Rectangle building;
    private Dimension dimension;
    private AddString valueString, damageString;

    public Building(Dimension worldSize, Point location, Dimension dimensions) {
        setColor(ColorUtil.rgb(255,0,0));
        dimension = new Dimension(dimensions.getWidth(),
                dimensions.getHeight());
        this.worldSize = worldSize;
        value = (new Random().nextInt(10) + 1)*100;
        fires = new Fires();
        building = new Rectangle(getColor(),
                dimensions.getWidth(), dimensions.getHeight(),
                0, 0,
                1, 1, 0);
        building.translate(location.getX(), location.getY());
        this.location = location;
    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        int xInfoOffset = 10;
        building.draw(g, parentOrigin, screenOrigin);
        valueString = new AddString("V: " + value, getColor(),
                new Point(location.getX()+building.getDimension().getWidth()
                        /2+xInfoOffset,
                        location.getY()-building.getDimension().getHeight()
                                /2+xInfoOffset*10));
        damageString = new AddString("D: " + damage, getColor(),
                new Point(location.getX()+building.getDimension().getWidth()
                        /2+xInfoOffset,
                        location.getY()-building.getDimension().getHeight()
                                /2+xInfoOffset*5));
        valueString.draw(g, parentOrigin, screenOrigin);
        damageString.draw(g, parentOrigin, screenOrigin);
    }
//
    public void setFireInBuilding(Fire fire) {
        if(building.getDimension().getWidth() != 0 &&
                building.getDimension().getHeight() != 0) {
            newFireLocation =
                new Point((int) (building.myTranslation.getTranslateX() -
                        building.getDimension().getWidth()/2) +
                        new Random().nextInt(building.getDimension().getWidth()),
                        (int) (building.myTranslation.getTranslateY() -
                                building.getDimension().getHeight()/2) +
                                new Random().nextInt(
                                        building.getDimension().getHeight()));
            fire.setLocation(newFireLocation);
            fire.start();
        }
    }

    public int getValue(){
        return value;
    }

    public void setDamage(int damage) {
        this.damage = Math.max(damage, this.damage);
    }

    public void setFires(Fire fire) {
        fires.add(fire);
    }

    public Fires getFires() {
        return fires;
    }

    public int getDamage() {
        return damage;
    }
}
