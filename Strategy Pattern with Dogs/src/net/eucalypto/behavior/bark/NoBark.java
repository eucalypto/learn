package net.eucalypto.behavior.bark;

public class NoBark implements BarkBehavior {
    @Override
    public void performBark() {
        System.out.println("I cannot bark, so I won't.");
    }
}
