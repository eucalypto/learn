package net.eucalypto.factory.simplefactory;

public class SimplePizzaFactory {

    public Pizza createPizza(String type) {
        Pizza pizza = null;

        switch (type) {
            case "clam":
                pizza = new ClamPizza();
                break;
            case "pepperoni":
                pizza = new PepperoniPizza();
                break;
            case "veggie":
                pizza = new VeggiePizza();
                break;
            case "cheese":
                pizza = new CheesePizza();
                break;
            default:
                throw new IllegalStateException("Unexpected pizza type: " + type);
        }

        return pizza;
    }
}
