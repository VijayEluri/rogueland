package org.sankozi.rogueland.gui;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import org.sankozi.rogueland.model.Actor;
import org.sankozi.rogueland.model.Destroyable;
import org.sankozi.rogueland.model.Player;

/**
 *
 * @author sankozi
 */
public final class PlayerStatsRenderer {
    JTextPane playerStats = new DescriptionTextArea();

    public JComponent renderPlayerStats(Player player){
        StringBuilder sb = new StringBuilder(50);
        sb.append("<html><font size='6'>Player stats</font>");

        sb.append("<table><tr valign='top'><td><table>");
        for(Player.Param param : Player.Param.values()){
            sb.append("<tr><td>").append(param.toString()).append("</td><td>")
              .append(player.playerParam(param)).append("</td></tr>");
        }
        sb.append("</table></td><td><table>");
        for(Destroyable.Param param : Destroyable.Param.values()){
            sb.append("<tr><td>").append(param.toString()).append("</td><td>")
              .append(player.destroyableParam(param)).append("</td></tr>");
        }
        sb.append("</table></td><td><table>");
        for(Actor.Param param : Actor.Param.values()){
            sb.append("<tr><td>").append(param.toString()).append("</td><td>")
              .append(player.actorParam(param)).append("</td></tr>");
        }
        sb.append("</table></td></tr></table>");
        sb.append("</html>");
        playerStats.setText(sb.toString());
        return playerStats;
    }
}
