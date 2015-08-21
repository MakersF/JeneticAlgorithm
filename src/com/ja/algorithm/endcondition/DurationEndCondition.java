package com.ja.algorithm.endcondition;

import java.util.concurrent.TimeUnit;

import com.ja.callables.EndCondition;
import com.ja.pupulation.Fitness;

public class DurationEndCondition<Chromosome> implements EndCondition<Chromosome> {

	private long timeToWaitMillis;
	private long old = -1;

	public DurationEndCondition(long duration, TimeUnit unit) {
		timeToWaitMillis = unit.toMillis(duration);
	}

	@Override
	public boolean shouldEnd(Fitness<Chromosome> fittness) {
		if(old == -1) {
			old = System.currentTimeMillis();
			return false;
		}
		long now = System.currentTimeMillis();
		timeToWaitMillis -= now - old;
		old = now;
		if(timeToWaitMillis < 0 ) {
			return true;
		}
		return false;
	}

}
