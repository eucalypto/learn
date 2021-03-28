package net.eucalypto.behavior.bark;

public class Bark implements BarkBehavior {
    @Override
    public void performBark() {
        System.out.println("I'm barking like a normal dog, nothing suspicious.");
    }
}
