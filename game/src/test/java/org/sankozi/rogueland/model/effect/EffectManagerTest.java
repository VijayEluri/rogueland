package org.sankozi.rogueland.model.effect;

import org.junit.Test;
import org.sankozi.rogueland.model.*;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author sankozi
 */
public class EffectManagerTest {

	@Test
	public void registerEventTest(){
        Player p = getPlayer();
		EffectManager em = EffectManager.forPlayer(p);
		Effect mockedEffect = mock(Effect.class);
		when(mockedEffect.getFinishTime()).thenReturn(1f);
		em.registerEffect(mockedEffect);
		//it can be 0 
		verify(mockedEffect, atLeast(1)).getFinishTime();
		verify(mockedEffect).start(em);
		
		verify(mockedEffect, never()).end(em);
		em.tick();
		verify(mockedEffect).end(em);
	}

	@Test
	public void registerEvent0TimeTest(){
		Player p = getPlayer();
		EffectManager em = EffectManager.forPlayer(p);
		Effect mockedEffect = mock(Effect.class);
		when(mockedEffect.getFinishTime()).thenReturn(0f);
		em.registerEffect(mockedEffect);
		//it can be 0 
		verify(mockedEffect, atLeast(1)).getFinishTime();
		verify(mockedEffect).start(em);
		verify(mockedEffect).end(em);
	}

    private Player getPlayer() {
        Player p = new Player();
        p.setControls(Controls.ALWAYS_WAIT);
        return p;
    }

	/** Effect increases Destroyable.Param.BLUNT_PROT by 2 */
	private class MockedAccessingEffect extends Effect {
		public MockedAccessingEffect(float finishTime) {super(finishTime);}
		
		@Override
		public Description start(AccessManager manager) {
			manager.accessDestroyableParam(Destroyable.Param.BLUNT_PROT).setChange(2f);
            manager.accessActorParam(Actor.Param.MANA_REGEN).setChange(5f);
            return Description.EMPTY;
		}

		@Override
		public void end(AccessManager manager) {
			manager.accessDestroyableParam(Destroyable.Param.BLUNT_PROT).setChange(0f);
            manager.accessActorParam(Actor.Param.MANA_REGEN).setChange(0f);
		}

		@Override
		public String getObjectName() {
			return "effect/mocked";
		}

        @Override
        public Map<? extends Param, Float> getDescriptionParameters() {
            return Collections.emptyMap();
        }
    }

	@Test 
	public void accessDestroyableParamTest(){
		Player p = getPlayer();
		EffectManager em = EffectManager.forPlayer(p);
		float before = p.destroyableParam(Destroyable.Param.BLUNT_PROT);
        float beforeDamage = p.actorParam(Actor.Param.MANA_REGEN);
		em.registerEffect(new MockedAccessingEffect(2f));
		float after = p.destroyableParam(Destroyable.Param.BLUNT_PROT);
        float afterDamage = p.actorParam(Actor.Param.MANA_REGEN);
		assertEquals(2f, after - before, 0.01f);
		assertEquals(5f, afterDamage - beforeDamage, 0.01f);
	}
}
