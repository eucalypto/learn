package net.eucalypto.designpatterns.iterator;

import java.util.Iterator;

public interface Menu {

  Iterator<MenuItem> createIterator();
}
