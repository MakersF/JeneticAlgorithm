package com.ja.algorithm.endcondition;

import com.ja.callables.EndCondition;
import com.ja.pupulation.Fittness;

public class GenerationsEndCondition<Individual> implements EndCondition<Individual> {

	private int counter = 0;
	
	public GenerationsEndCondition(int generations) {
		counter = generations;
	}

	@Override
	public boolean shouldEnd(Fittness<Individual> fittness) {
		counter--;
		if (counter == 0)
			return true;
		return false;
	}

}
