package org.csc133.a3.gameobjects;

public class ExtinguishedFire extends FireState {
    private static ExtinguishedFire instance = new ExtinguishedFire();
    private ExtinguishedFire(){}

    public static ExtinguishedFire instance() {
        return instance;
    }

    @Override
    public void updateState(Fire fire) {

    }
}
