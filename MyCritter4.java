package assignment4;

import java.util.*;

public class MyCritter4 extends Critter.TestCritter {

    @Override
    public void doTimeStep() {
        walk(6);
    }

    @Override
    public boolean fight(String opponent) {
        return true;
    }

    public String toString() {
        return "4";
    }
}