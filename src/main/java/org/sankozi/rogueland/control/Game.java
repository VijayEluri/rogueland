package org.sankozi.rogueland.control;

import com.google.common.collect.Lists;
import com.google.inject.internal.Preconditions;
import java.awt.Point;
import java.util.List;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Actor;
import org.sankozi.rogueland.model.AiActor;
import org.sankozi.rogueland.model.Controls;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Move;
import org.sankozi.rogueland.model.Player;
import org.sankozi.rogueland.model.Tile;
import org.sankozi.rogueland.model.Tile.Type;

/**
 * Object responsible for single game instance
 * @author sankozi
 */
public class Game {
    private final static Logger LOG = Logger.getLogger(Game.class);

    Level level = new Level();
    Player player = null;
    Thread gameThread = new Thread(new GameRunnable());
    List<Actor> actors = Lists.newArrayList();

    public void start(){
        Preconditions.checkState(!gameThread.isAlive(), "game has already started");
        gameThread.start();
    }

    public void setControls(Controls controls){
        this.player = new Player(controls);
        player.setLocation(new Point(5,5));
        actors.add(player);
        Actor ai = new AiActor();
        ai.setLocation(new Point(10,10));
        actors.add(ai);
    }

    public Level getLevel() {
        return level;
    }

    private class GameRunnable implements Runnable {
        @Override
        public void run() {
            do {
                processActors();
//                LOG.info("player location:" + playerLocation);
            } while (true);
        }

        private void processActor(Actor actor) {
            Move m;
            Point newLocation;
            Point actorLocation = actor.getLocation();
            do {
                level.getTiles()[actorLocation.x][actorLocation.y].actor = actor;
                m = actor.act(level);
                newLocation = actorLocation.getLocation();
                processMove(m, newLocation);
//                LOG.info("actor : " + actor + " move : " + newLocation);
                level.getTiles()[actorLocation.x][actorLocation.y].actor = null;
            } while (!validLocation(newLocation, level.getTiles()));
            actorLocation = newLocation;
            level.getTiles()[actorLocation.x][actorLocation.y].actor = actor;
            actor.setLocation(actorLocation);
        }

        private void processActors() {
            for(Actor actor:actors){
                processActor(actor);
            }
        }
    }

    private static void processMove(Move m, Point newLocation) {
        if(m instanceof Move.Go){
            switch ((Move.Go) m) {
                case EAST:
                    newLocation.x++;
                    break;
                case NORTH:
                    newLocation.y--;
                    break;
                case WEST:
                    newLocation.x--;
                    break;
                case SOUTH:
                    newLocation.y++;
                    break;
                case NORTHEAST:
                    newLocation.y--;
                    newLocation.x++;
                    break;
                case NORTHWEST:
                    newLocation.y--;
                    newLocation.x--;
                    break;
                case SOUTHEAST:
                    newLocation.y++;
                    newLocation.x++;
                    break;
                case SOUTHWEST:
                    newLocation.y++;
                    newLocation.x--;
                    break;
                default:
                    LOG.error("unhandled move : " + m);
            }
        } else {
            LOG.error("unhandled move : " + m);
        }
    }

    private static boolean validLocation(Point playerLocation, Tile[][] tiles) {
        if(playerLocation.x < 0 || playerLocation.y < 0
                || playerLocation.x >= tiles.length
                || playerLocation.y >= tiles[0].length) {
            return false;
        } else {
            Tile tile = tiles[playerLocation.x][playerLocation.y];
//            LOG.info("invalid location : " + playerLocation);
            return tile.type != Tile.Type.WALL
                  && tile.actor == null;
        }
    }
}
