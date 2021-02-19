package net.eucalypto.composition.beverages;

import net.eucalypto.composition.condiments.Condiment;

import java.util.ArrayList;
import java.util.List;

public abstract class Beverage {

    String description;
    double cost;

    List<Condiment> condiments = new ArrayList<>();

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(description);

        for (Condiment condiment : condiments) {
            sb.append(", ").append(condiment.getDescription());
        }

        return sb.toString();
    }

    public double cost() {
        double sum = cost;
        for (Condiment condiment : condiments) {
            sum += condiment.getCost();
        }

        return sum;
    }


    public void addCondiment(Condiment condiment) {
        condiments.add(condiment);
    }
}
