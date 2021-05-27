package net.eucalypto.designpatterns.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Waitress {

  List<Menu> menus = new ArrayList<>();

  public Waitress(Menu... menus) {
    this.menus.addAll(Arrays.asList(menus));
  }

  public void printMenu() {
    menus.forEach(menu -> printMenu(menu.createIterator()));
  }

  private void printMenu(Iterator<MenuItem> iterator) {
    while (iterator.hasNext()) {
      var menuItem = iterator.next();
      System.out.println(
          menuItem.getName() + ", "
              + menuItem.getPrice() + " -- "
              + menuItem.getDescription());
    }
    System.out.println();
  }

  // other methods here
}
