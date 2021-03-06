package org.sankozi.rogueland.gui;

import javax.swing.*;

import com.google.inject.Inject;
import org.apache.logging.log4j.*;
import org.sankozi.rogueland.control.LogListener;
import org.sankozi.rogueland.control.MessageType;

/**
 * Base class for Components that show game logs,
 *
 * Subclasses cannot be focusable
 *
 * @author sankozi
 */
public abstract class LogPanel extends JPanel implements LogListener{
    private final static Logger LOG = LogManager.getLogger(LogPanel.class);

    {
        this.setFocusable(false);
    }

    @Override
    public final void onMessage(String message, MessageType type) {
        SwingUtilities.invokeLater(() -> onMessageEDT(message, type));
    }

    protected abstract void onMessageEDT(String message, MessageType type);

    @Inject
    void setGameSupport(GameSupport gs){
        gs.addLogListener(this);
    }
}
