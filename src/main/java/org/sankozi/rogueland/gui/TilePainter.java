package org.sankozi.rogueland.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import org.sankozi.rogueland.model.Tile;

/**
 *
 * @author sankozi
 */
public interface TilePainter {

    void paint(Rectangle rect, Tile[][] tiles, Graphics g);

    Rectangle getPixelLocation(Rectangle rect, Point location);
}
