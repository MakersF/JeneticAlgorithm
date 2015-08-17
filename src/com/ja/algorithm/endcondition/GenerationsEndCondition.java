package com.ja.algorithm.endcondition;

import com.ja.callables.EndCondition;
import com.ja.pupulation.Fitness;

public class GenerationsEndCondition<Chromosome> implements EndCondition<Chromosome> {

	private int counter = 0;
	
	public GenerationsEndCondition(int generations) {
		counter = generations;
	}

	@Override
	public boolean shouldEnd(Fitness<Chromosome> fittness) {
		counter--;
		if (counter == 0)
			return true;
		return false;
	}

}
