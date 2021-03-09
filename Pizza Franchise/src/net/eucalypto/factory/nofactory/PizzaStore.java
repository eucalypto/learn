package net.eucalypto.factory.nofactory;

public class PizzaStore {

    public Pizza orderPizza(String type) {
        Pizza pizza;

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

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
