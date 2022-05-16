package org.csc133.a3.gameobjects;

public abstract class Fixed extends GameObject {
    public Fixed() {
        super();
    }

    public boolean isCollidingWith(Helicopter helicopter) {
        boolean bool = ((helicopter.myTranslation.getTranslateX() >=
                myTranslation.getTranslateX()) &&
                (helicopter.myTranslation.getTranslateY() >=
                        myTranslation.getTranslateY())) &&
                ((helicopter.myTranslation.getTranslateX() <=
                        myTranslation.getTranslateY() +
                                myDimension.getWidth()/2)  &&
                        (helicopter.myTranslation.getTranslateY() <=
                                myTranslation.getTranslateY() +
                                        myDimension.getHeight()/2 ));
        System.err.println(bool);
//        if(bool) {
            helicopter.setTrue(helicopter.myTranslation.getTranslateX()
                            - helicopter.myTranslation.getTranslateX(),
                    0);
//        }
        return bool;
    }
}

