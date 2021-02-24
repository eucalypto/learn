package net.eucalypto.decorator;

import net.eucalypto.decorator.beverages.Beverage;
import net.eucalypto.decorator.beverages.DarkRoast;
import net.eucalypto.decorator.beverages.Espresso;
import net.eucalypto.decorator.beverages.HouseBlend;
import net.eucalypto.decorator.condiments.Mocha;
import net.eucalypto.decorator.condiments.Soy;
import net.eucalypto.decorator.condiments.Whip;

public class StarbuzzCoffeeMain {

    public static void main(String[] args) {
        setSizeBamboozle();
    }

    private static void setSizeBamboozle() {
        Beverage espresso = new Espresso();

        System.out.println(espresso.getSize());

        espresso.setSize(Beverage.Size.GRANDE);

        System.out.println(espresso.getSize());

        espresso = new Soy(espresso);

        System.out.println(espresso.getSize());

    }

    private static void happyPath() {
        Beverage beverage1 = new Espresso();

        print(beverage1);

        Beverage beverage2 = new DarkRoast();

        beverage2 = new Mocha(beverage2);
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        print(beverage2);


        Beverage beverage3 = new HouseBlend();
        beverage3 = new Soy(beverage3);
        beverage3 = new Mocha(beverage3);
        beverage3 = new Whip(beverage3);
        print(beverage3);
    }

    private static void print(Beverage beverage) {
        System.out.println(beverage.getDescription() + " $" + beverage.cost());
    }
}
