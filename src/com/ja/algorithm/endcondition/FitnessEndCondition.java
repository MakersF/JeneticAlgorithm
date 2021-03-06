package com.ja.algorithm.endcondition;

import com.ja.callables.EndCondition;
import com.ja.pupulation.Fitness;

public class FitnessEndCondition<Chrmosome> implements EndCondition<Chrmosome>{

	double targetFitness;
	public FitnessEndCondition(double pTargetFitness) {
		targetFitness = pTargetFitness;
	}
	
	@Override
	public boolean shouldEnd(Fitness<Chrmosome> fittness) {
		if(fittness.getElements().first().fitness >= targetFitness)
			return true;
		return false;
	}

}
