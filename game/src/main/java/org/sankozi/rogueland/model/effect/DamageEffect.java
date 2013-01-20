package org.sankozi.rogueland.model.effect;

import java.util.Collections;
import org.sankozi.rogueland.control.GameLog;
import org.sankozi.rogueland.model.Damagable;
import org.sankozi.rogueland.model.Damage;

/**
 *
 * @author sankozi
 */
public class DamageEffect extends InstantEffect {
    private final Iterable<Damage> damages;

    private DamageEffect(Iterable<Damage> damages){
        this.damages = damages;
    }
    
    public static DamageEffect simpleDamageEffect(Damage.Type type, int value){
        DamageEffect ret = new DamageEffect(Collections.singleton(new Damage(type, value)));
        return ret;
    }
    
    @Override
    protected void apply(AccessManager manager) {
        Damagable damagable = manager.getDamagable();
        for(Damage dam : damages){
            int res = damagable.protection(dam.type);
            if(res < dam.value){
                damagable.damage(dam.value - (int) res);
                
            }
        }
    }

    @Override
    public String getObjectName() {
        return "effect.damage";
    }
}