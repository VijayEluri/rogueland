package org.sankozi.rogueland.gui;

import com.google.inject.Singleton;

import java.awt.*;
import javax.swing.*;

/**
 * Central panel of MainFrame, switches between various view - LevelPanel, InventoryPanel etc.
 * @author sankozi
 */
@Singleton
public final class MainPanel extends JPanel implements ComponentSwitcher {

    {
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
    }

    @Override
    public void setComponent(JComponent component) {
        removeAll();
        add(component, BorderLayout.CENTER);
        component.requestFocusInWindow();
        revalidate();
        this.repaint();
    }
}
