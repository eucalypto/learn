package net.eucalypto.designpatterns.iterator.pancakehouse;

import java.util.List;
import net.eucalypto.designpatterns.iterator.Iterator;
import net.eucalypto.designpatterns.iterator.MenuItem;

public class PancakeHouseMenuIterator implements Iterator {

  List<MenuItem> menuItems;
  int position = 0;

  public PancakeHouseMenuIterator(List<MenuItem> menuItems) {
    this.menuItems = menuItems;
  }


  @Override
  public boolean hasNext() {
    return position < menuItems.size();
  }

  @Override
  public MenuItem next() {
    var menuItem = menuItems.get(position);
    position++;
    return menuItem;
  }
}
