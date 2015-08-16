package com.ja.algorithm.endcondition;

import com.ja.callables.EndCondition;
import com.ja.pupulation.Fittness;

public class FitnessEndCondition<Individual> implements EndCondition<Individual>{

	double targetFitness;
	public FitnessEndCondition(double pTargetFitness) {
		targetFitness = pTargetFitness;
	}
	
	@Override
	public boolean shouldEnd(Fittness<Individual> fittness) {
		if(fittness.getElements().first().fitness >= targetFitness)
			return true;
		return false;
	}

}
