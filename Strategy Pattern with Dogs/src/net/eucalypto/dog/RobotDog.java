package net.eucalypto.dog;

import net.eucalypto.behavior.bark.ElectronicBark;
import net.eucalypto.behavior.run.Run;

public class RobotDog extends Dog {

    public RobotDog() {
        barkBehavior = new ElectronicBark();
        runBehavior = new Run();
    }

    @Override
    public void display() {
        System.out.println("I'm a Robot dog.");
    }
}
