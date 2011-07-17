package org.sankozi.rogueland.model;

import java.awt.Point;

/**
 *
 * @author sankozi
 */
public interface Actor extends Destroyable{
    Move act(Level input, Locator locator);

    Point getLocation();
    void setLocation(Point point);

    Damage getPower();

    /** Returns value stored in int */
    public float actorParam(Param param);

    public enum Param {
        DAMAGE_MIN,
        DAMAGE_MAX,
       
        MANA_REGEN,
    }
}
