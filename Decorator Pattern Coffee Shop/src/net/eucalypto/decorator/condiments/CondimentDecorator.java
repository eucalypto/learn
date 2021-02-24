package net.eucalypto.decorator.condiments;

import net.eucalypto.decorator.beverages.Beverage;

public abstract class CondimentDecorator extends Beverage {
    Beverage beverage;

    public abstract String getDescription();

    public Size getSize() {
        return beverage.getSize();
    }

    public void setSize(Size size) {
        beverage.setSize(size);
    }
}
