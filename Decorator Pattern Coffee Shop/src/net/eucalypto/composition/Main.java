package net.eucalypto.composition;

import net.eucalypto.composition.beverages.Beverage;
import net.eucalypto.composition.beverages.DarkRoast;
import net.eucalypto.composition.beverages.HouseBlend;
import net.eucalypto.composition.condiments.Mocha;
import net.eucalypto.composition.condiments.Whip;

public class Main {

    public static void main(String[] args) {
        Beverage houseBlend = new HouseBlend();
        houseBlend.addCondiment(new Mocha());
        houseBlend.addCondiment(new Mocha());

        System.out.println(houseBlend.getDescription());
        System.out.println(houseBlend.cost());


        Beverage dark = new DarkRoast();
        dark.addCondiment(new Mocha());
        dark.addCondiment(new Mocha());
        dark.addCondiment(new Whip());

        System.out.println(dark.getDescription());
        System.out.println(dark.cost());
    }
}
