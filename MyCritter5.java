package assignment4;

import java.util.*;

public class MyCritter5 extends Critter.TestCritter {

    @Override
    public void doTimeStep() {
        int dir = getRandomInt(8);
        run(dir);
    }

    @Override
    public boolean fight(String opponent) {
        if (getEnergy() > 15) return true;
        return false;
    }

    public String toString() {
        return "5";
    }
}
