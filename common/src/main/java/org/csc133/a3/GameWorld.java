package org.csc133.a3;

import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import org.csc133.a3.gameobjects.*;
import java.util.ArrayList;
import java.util.Random;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

public class GameWorld {
    private River river;
    private Helipad helipad;
    private PlayerHelicopter playerHelicopter;
    private ArrayList<GameObject> gameObjects;
    private Dimension worldSize;
    private Building buildingTop, buildingRight, buildingLeft;
    private Point topBuildingLocation, rightBuildingLocation,
            leftBuildingLocation;
    private Dimension topBuildingSize, rightBuildingSize, leftBuildingSize;
    private Fire fire;
    private Fires fires, deadFires;
    private Buildings buildings;
    private final int FUEL = 25000;
    private int fireArea, area, fireSize,
            size, buildingCount, averageBuildingDamage,
            remainingAreaSize, buildingDamage, randomFiresInBuilding, fireCount,
            chosenFire, score;
    private double financialLoss;
    private ArrayList<Integer> initialAreas;

    private static GameWorld gameWorld;

    private GameWorld() {}

    public static GameWorld getInstance() {
        gameWorld = new GameWorld();
        gameWorld.worldSize = new Dimension();
        return gameWorld;
    }

    public void init() {
        initialAreas = new ArrayList<>();
        river = new River(worldSize);
        helipad = new Helipad(worldSize);
        playerHelicopter =
                new PlayerHelicopter(worldSize,
                        new Point((int) helipad.getTransform().getTranslateX(),
                (int) helipad.getTransform().getTranslateY()),
                        helipad.getHelipadBoxSize(), river.getDimension());
        gameObjects = new ArrayList<>();
        fires = new Fires();
        deadFires = new Fires();
        buildings = new Buildings();
        createBuildings();
        fireArea = 1000;
        area = 0;
        gameObjects.add(buildings);
        createFiresInBuilding();
        checkFireBudget();
        gameObjects.add(fires);
        gameObjects.add(river);
        gameObjects.add(helipad);
        playerHelicopter.setFuel(FUEL);
        gameObjects.add(playerHelicopter);
    }

    public void tick() {
        buildingCount = 0;
        for(GameObject go : gameObjects) {
            if(go instanceof Buildings) {
                for(Building b : buildings) {
                    buildingDamage = 0;
                    for(Fire fire : b.getFires()) {
                        buildingDamage += fire.getArea();
                    }
                    int currDamage =
                            (buildingDamage-initialAreas.get(buildingCount))/
                                    b.getValue();
                    if(currDamage < 100) {
                        b.setDamage(currDamage);
                    } else {
                        b.setDamage(100);
                        for(Fire fire : b.getFires()) {
                            fire.setCanGrow(false);
                        }
                    }
                    buildingCount++;
                }
            }
        }
        playerHelicopter.move();
        fireCount = 0;
        if(getFireCount()>0) {
            chosenFire = new Random().nextInt(getFireCount());
        }
        for(GameObject go: gameObjects) {
            if(go instanceof Fires) {
                for (Fire fire : fires) {
                    if (chosenFire == fireCount) {
                        fire.growFire();
                    }
                    fireCount++;
                    if (playerHelicopter.checkFireCollision(fire)) {
                        fire.setTrue();
                    } else {
                        fire.setFalse();
                    }
                    if (fire.getSize() <= 0) {
                        fire.extinguishFire();
                        deadFires.add(fire);
                    }
                }
            }
        }
        for(GameObject go: gameObjects) {
            if(go instanceof Fires) {
                for(Fire fire: fires) {
                    for(Fire deadFire : deadFires) {
                        if(fire==deadFire) {
                            fires.remove(deadFire);
                        }
                    }
                }
            }
        }
        if((getFireCount() == 0 && playerHelicopter.isOnPad()) &&
                (!checkBuildingsDestroyed())) {
            gameWon();
        }
        if(checkBuildingsDestroyed()) {
            endGameBuildings();
        }
//        river.isCollidingWith(helicopter);
        playerHelicopter.checkRiverCollision();
        if(playerHelicopter.checkFuel()) {
            endGameFuel();
        }
    }

    private void createBuildings() {
        rightBuildingSize = new Dimension(worldSize.getHeight()/6,
                worldSize.getHeight()/2);
        rightBuildingLocation =
                new Point(worldSize.getWidth()/9 ,
                        worldSize.getHeight()/4 + worldSize.getHeight()/18);
        leftBuildingSize = new Dimension(worldSize.getHeight()/5,
                (int) (worldSize.getHeight()/2.5));
        leftBuildingLocation =
                new Point(worldSize.getWidth() - worldSize.getWidth()/9,
                        worldSize.getHeight()/4 +
                                (rightBuildingSize.getHeight() -
                                        leftBuildingSize.getHeight()));
        topBuildingSize = new Dimension((worldSize.getWidth()*2)/3,
                worldSize.getHeight()/10);
        topBuildingLocation = new Point(worldSize.getWidth()/2,
                worldSize.getHeight() - topBuildingSize.getHeight());
        buildingTop = new Building(worldSize, topBuildingLocation,
                topBuildingSize);
        buildingRight = new Building(worldSize, rightBuildingLocation,
                rightBuildingSize);
        buildingLeft = new Building(worldSize, leftBuildingLocation,
                leftBuildingSize);
        buildings.add(buildingLeft);
        buildings.add(buildingRight);
        buildings.add(buildingTop);
    }

    private boolean checkBuildingsDestroyed() {
        return getTotalAverageDamage() >= 100;
    }

    private void createFiresInBuilding(){
        for(GameObject go : gameObjects) {
            if(go instanceof Buildings) {
                for(Building building : buildings) {
                    randomFiresInBuilding = new Random().nextInt(2)+2;
                    for(int i =0;i<randomFiresInBuilding;i++) {
                        size = new Random().nextInt(4) + 10;
                        fire = new Fire(worldSize, size);
                        area += fire.getArea();
                        fires.add(fire);
                        building.setFires(fire);
                        fire.setFire(building);
                    }
                    getInitialArea(area);
                }
            }
        }
    }

    private void getInitialArea(int area) {
        initialAreas.add(area);
    }

    private void checkFireBudget() {
        for(GameObject go : gameObjects) {
            if(go instanceof Buildings) {
                for(Building building: buildings) {
                    if(area < fireArea) {
                        remainingAreaSize =
                                (int)Math.sqrt(Math.ceil((fireArea - area)/
                                        Math.PI)) * 2;
                        fire = new Fire(worldSize, remainingAreaSize);
                        fires.add(fire);
                        building.setFires(fire);
                        fire.setFire(building);
                        area+=fire.getArea();
                    }
                }
            }
        }
    }

    private void gameWon() {
        if(Dialog.show("Congratulations!",
                "You put out all the fires!\n Score: " + calculateScore(),
                "Replay", "Exit")) {
            init();
        }
        else {
            Display.getInstance().exitApplication();
        }
    }

    private void endGameFuel() {
        if(Dialog.show("Game Over", "You ran out of fuel",
                "Replay", "Exit")) {
            init();
        }
        else {
            Display.getInstance().exitApplication();
        }
    }

    private void endGameBuildings() {
        if(Dialog.show("Game Over", "All Buildings Were Destroyed",
                "Replay", "Exit")) {
            init();
        }
        else {
            Display.getInstance().exitApplication();
        }
    }

    public int calculateScore() {
        score = 100 - getTotalAverageDamage();
        return score;
    }

    public ArrayList<GameObject> getGameObjectCollection() {
        return gameObjects;
    }

    public String getHeading() {
        return String.valueOf(playerHelicopter.getHeadingAngle());
    }

    public String getFuel() {
        return String.valueOf(playerHelicopter.getFuel());
    }

    public String getSpeed() {
        return String.valueOf(playerHelicopter.getSpeed());
    }

    public void setDimension(Dimension worldSize) {
        this.worldSize = worldSize;
    }

    public void exitApplication() {
        if(Dialog.show("Confirm", "Do you want to Quit?", "OK",
                "Cancel")) {
            Display.getInstance().exitApplication();
        }
    }

    public void decelerateHeli() {
        playerHelicopter.slowDown();
    }

    public void accelerateHeli() {
        playerHelicopter.speedUp();
    }

    public void steerLeft() {
        playerHelicopter.steerLeft();
    }

    public void steerRight() {
        playerHelicopter.steerRight();
    }

    public void drinkWater() {
        playerHelicopter.drinkWater();
    }

    public void fightFire() {
        playerHelicopter.fightFire(fires);
        playerHelicopter.dropWater();
    }

    public int getFireCount() {
        return fires.getSize();
    }

    public String getFireSize() {
        fireSize = 0;
        for(GameObject go : gameObjects) {
            if(go instanceof Fires) {
                for(Fire fire : fires) {
                    fireSize += fire.getSize();
                }
            }
        }
        return String.valueOf(fireSize);
    }

    public int getTotalAverageDamage() {
        averageBuildingDamage = 0;
        for(GameObject go : gameObjects) {
            if(go instanceof Buildings) {
                for(Building building : buildings) {
                    averageBuildingDamage += building.getDamage();
                }
            }
        }
        averageBuildingDamage = Math.round(averageBuildingDamage/3);
        return averageBuildingDamage;
    }

    public String getFinancialLoss() {
        financialLoss = 0.0;
        for(GameObject go : gameObjects) {
            if(go instanceof Buildings) {
                for(Building building : buildings) {
                    financialLoss += ((building.getDamage())/100.0) *
                            building.getValue();
                }
            }
        }
        return String.valueOf((int)financialLoss);
    }
}
