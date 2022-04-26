package org.csc133.a3.gameobjects;

public class BurningFire extends FireState {

    private static BurningFire instance = new BurningFire();
    private BurningFire(){}

    public static BurningFire instance() {
        return instance;
    }

    @Override
    public void updateState(Fire fire) {
        fire.setCurrentState(ExtinguishedFire.instance());
    }
}
