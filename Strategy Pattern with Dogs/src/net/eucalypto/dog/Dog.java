package net.eucalypto.dog;

import net.eucalypto.behavior.bark.BarkBehavior;
import net.eucalypto.behavior.run.RunBehavior;

public abstract class Dog {

    BarkBehavior barkBehavior;
    RunBehavior runBehavior;

    public Dog() {

    }

    public void setBarkBehavior(BarkBehavior bb) {
        barkBehavior = bb;
    }

    public void setRunBehavior(RunBehavior rb) {
        runBehavior = rb;
    }


    public void bark() {
        barkBehavior.performBark();
    }

    public void run() {
        runBehavior.performRun();
    }

    public abstract void display();

    public void exist() {
        System.out.println("All Dogs exist, regardless of kind.");
    }
}
