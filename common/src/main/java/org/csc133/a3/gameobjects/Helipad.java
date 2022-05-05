//package org.csc133.a3.gameobjects;
//
//import com.codename1.charts.util.ColorUtil;
//import com.codename1.ui.Graphics;
//import com.codename1.ui.geom.Dimension;
//import com.codename1.ui.geom.Point;
//
//public class Helipad extends Fixed{
//    private Point rectangleLocation, centerLocation;
//    private int boxSize;
//    private int circleSize, radius;
//
//    public Helipad(Dimension worldSize) {
//        this.color = ColorUtil.rgb(139,139,139);
//        this.dimension = new Dimension(worldSize.getWidth(),
//                worldSize.getHeight());
//        boxSize = (int) (dimension.getHeight()/8.5);
//        circleSize = (int) (dimension.getHeight()/11.5);
//        radius = circleSize/2;
//        rectangleLocation = new Point(dimension.getWidth()/2 - boxSize/2,
//                (int) (dimension.getHeight() - (boxSize*1.5)));
//        centerLocation =
//                new Point(rectangleLocation.getX() + (boxSize/2),
//                        rectangleLocation.getY() + (boxSize/2));
//    }
//
//    public Point getHelipadCenter() {
//        return centerLocation;
//    }
//
//    public int getHelipadSize() {
//        return circleSize;
//    }
//
////    @Override
//    public void draw(Graphics g, Point containerOrigin) {
//        g.setColor(color);
//        g.drawRect(containerOrigin.getX() + rectangleLocation.getX(),
//                containerOrigin.getY() + rectangleLocation.getY(), boxSize,
//                boxSize, 5);
//        g.drawArc(containerOrigin.getX() + centerLocation.getX()-radius,
//                containerOrigin.getY() + centerLocation.getY()-radius,
//                circleSize, circleSize, 0, 360);
//    }
//}
