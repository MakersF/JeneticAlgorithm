package com.ja.algorithm.endcondition;

import com.ja.callables.EndCondition;
import com.ja.pupulation.Fitness;

public class FitnessEndCondition<Chromosome> implements EndCondition<Chromosome>{

	double targetFitness;
	public FitnessEndCondition(double pTargetFitness) {
		targetFitness = pTargetFitness;
	}
	
	@Override
	public boolean shouldEnd(Fitness<Chromosome> fittness) {
		if(fittness.getElements().first().fitness >= targetFitness)
			return true;
		return false;
	}

}
