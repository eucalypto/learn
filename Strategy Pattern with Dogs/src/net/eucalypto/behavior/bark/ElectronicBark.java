package net.eucalypto.behavior.bark;

public class ElectronicBark implements BarkBehavior {
    @Override
    public void performBark() {
        System.out.println("Electronic sounding bark.");
    }
}
