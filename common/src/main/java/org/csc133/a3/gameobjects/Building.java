//package org.csc133.a3.gameobjects;
//
//import com.codename1.charts.util.ColorUtil;
//import com.codename1.ui.Graphics;
//import com.codename1.ui.geom.Dimension;
//import com.codename1.ui.geom.Point;
//
//import java.util.Random;
//
//public class Building extends Fixed{
//    private Point newFireLocation;
//    private int value, damage;
//    private Dimension worldSize;
//    private Fires fires;
//
//    public Building(Dimension worldSize, Point location, Dimension dimensions) {
//        this.color = ColorUtil.rgb(255,0,0);
//        this.dimension = new Dimension(dimensions.getWidth(),
//                dimensions.getHeight());
//        this.location = new Point(location.getX(), location.getY());
//        this.worldSize = worldSize;
//        value = (new Random().nextInt(10) + 1)*100;
//        fires = new Fires();
//    }
//
//    public void setFireInBuilding(Fire fire) {
//        if(dimension.getWidth() != 0 && dimension.getHeight() != 0) {
//            newFireLocation =
//                new Point(new Random().nextInt(dimension.getWidth()-
//                        15) +
//                        location.getX(),
//                        new Random().nextInt(dimension.getHeight()-
//                                15) +
//                                location.getY());
//            fire.setLocation(newFireLocation);
//            fire.start();
//        }
//    }
//
//    public int getValue(){
//        return value;
//    }
//
//    public void setDamage(int damage) {
//        this.damage = Math.max(damage, this.damage);
//    }
//
//    public void setFires(Fire fire) {
//        fires.add(fire);
//    }
//
//    public Fires getFires() {
//        return fires;
//    }
//
//    public int getDamage() {
//        return damage;
//    }
//
////    @Override
//    public void draw(Graphics g, Point containerOrigin) {
//        int xInfoOffset = 10;
//        g.setColor(color);
//        g.drawRect(containerOrigin.getX() + location.getX(),
//                containerOrigin.getY() + location.getY(),
//                dimension.getWidth(), dimension.getHeight(), 5);
//        g.drawString("V: " + value,
//                location.getX() + dimension.getWidth() + xInfoOffset,
//                containerOrigin.getY() + location.getY() +
//                        dimension.getHeight() - worldSize.getHeight()/15);
//        g.drawString("D: " + damage +"%",
//                location.getX() + dimension.getWidth() + xInfoOffset,
//                containerOrigin.getY() + location.getY() +
//                        dimension.getHeight() - worldSize.getHeight()/32);
//    }
//
//}
