package org.sankozi.rogueland.control;

import org.apache.logging.log4j.*;

/**
 * EnemySpawner
 *
 * @author sankozi
 */
public class EnemySpawner implements Observer {
    private final static Logger LOG = LogManager.getLogger(EnemySpawner.class);

    @Override
    public void attach(Locator level, LevelControl control) {
        LOG.info("attaching");
    }

    @Override
    public void tick(Locator level, LevelControl control) {
        LOG.info("tick");
    }
}
