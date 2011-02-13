package org.sankozi.rogueland.model;

import java.awt.Point;

/**
 *
 * @author sankozi
 */
public interface Actor extends Destroyable{
    Move act(Level input);

    Point getLocation();
    void setLocation(Point point);

    Damage getPower();

}
