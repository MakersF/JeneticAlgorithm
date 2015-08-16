package com.ja.algorithm.endcondition;

import com.ja.callables.EndCondition;
import com.ja.pupulation.Fittness;

public class GenerationsEndCondition<Chromosome> implements EndCondition<Chromosome> {

	private int counter = 0;
	
	public GenerationsEndCondition(int generations) {
		counter = generations;
	}

	@Override
	public boolean shouldEnd(Fittness<Chromosome> fittness) {
		counter--;
		if (counter == 0)
			return true;
		return false;
	}

}
