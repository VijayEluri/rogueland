package org.sankozi.rogueland.model.effect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.sankozi.rogueland.control.GameLog;
import org.sankozi.rogueland.model.Damagable;
import org.sankozi.rogueland.model.Damage;
import org.sankozi.rogueland.model.Param;

/**
 *
 * @author sankozi
 */
public class DamageEffect extends InstantEffect {
    private final Iterable<Damage> damages;

    private Map<Damage.Type, Float> descriptionParameters;

    private DamageEffect(Iterable<Damage> damages){
        this.damages = damages;
    }
    
    public static DamageEffect simpleDamageEffect(Damage.Type type, int value){
        DamageEffect ret = new DamageEffect(Collections.singleton(new Damage(type, value)));
        return ret;
    }

    public static DamageEffect multiDamageEffect(Map<Damage.Type, Integer> values){
        ArrayList<Damage> damages = Lists.newArrayListWithCapacity(values.size());
        for(Map.Entry<Damage.Type, Integer> entry : values.entrySet()){
            damages.add(new Damage(entry.getKey(), entry.getValue()));
        }
        return new DamageEffect(damages);
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
    public Map<? extends Param, Float> getDescriptionParameters() {
        if(descriptionParameters == null){
            ImmutableMap.Builder<Damage.Type, Float> builder = ImmutableMap.builder();
            for(Damage damage: damages){
                builder.put(damage.type, (float)damage.value);
            }
            descriptionParameters = builder.build();
        }
        return descriptionParameters;
    }

    @Override
    public String getObjectName() {
        return "effect.damage";
    }
}
