package net.eucalypto.designpatterns.iterator.pancakehouse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.eucalypto.designpatterns.iterator.Menu;
import net.eucalypto.designpatterns.iterator.MenuItem;

public class PancakeHouseMenu implements Menu {

  List<MenuItem> menuItems;

  public PancakeHouseMenu() {
    menuItems = new ArrayList<>();
    populateMenu();
  }

  public Iterator<MenuItem> createIterator() {
    return menuItems.iterator();
  }

  private void populateMenu() {
    addItem("K&B's Pancake Breakfast",
        "Pancakes with scrambled eggs and toast",
        true, 2.99);
    addItem("Regular Pancake Breakfast",
        "Pancakes with fried eggs, sausage",
        false, 2.99);
    addItem("Blueberry Pancakes",
        "Pancakes made with fresh blueberries", true,
        3.49);
    addItem("Waffles",
        "Waffles with your choice of blueberries or strawberries",
        true, 3.59);
  }

  public void addItem(String name, String description,
      boolean vegetarian,
      double price) {
    var menuItem = new MenuItem(name, description, vegetarian, price);
    menuItems.add(menuItem);
  }

  // other menu methods here
}
