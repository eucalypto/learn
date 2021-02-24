package net.eucalypto.decorator.condiments;

import net.eucalypto.decorator.beverages.Beverage;

public class Soy extends CondimentDecorator {

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Soy";
    }

    @Override
    public double cost() {
        double cost;
        switch (beverage.getSize()) {
            case TALL:
                cost = 0.10;
                break;
            case GRANDE:
                cost = 0.15;
                break;
            case VENTI:
                cost = 0.20;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + beverage.getSize());
        }
        return beverage.cost() + cost;
    }
}
