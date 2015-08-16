package com.ja.examples.knapsack_problem;

import com.ja.callables.Crossover;
import com.ja.callables.Evaluation;
import com.ja.callables.Mutate;

public class KnapEvalCrossMutate implements Evaluation<KnapIndividual>, Crossover<KnapIndividual>, Mutate<KnapIndividual>{

	// Evaluation implementation

	@Override
	public double evaluate(KnapIndividual a) {
		int total_weight = 0;
		int total_value = 0;
		for(int i=0; i< a.picked.length; i++) {
			total_value += (a.picked[i] ? 1:0) * KnapIndividual.value_per_item[i];
			total_weight += (a.picked[i] ? 1:0) * KnapIndividual.weight_per_item[i];
		}
		if(total_weight > KnapIndividual.knapsack_max_weigth) 
			return 0;
		return total_value;
	}
	
	// Crossover implementation
	@Override
	public KnapIndividual cross(KnapIndividual parent1, KnapIndividual parent2) {
		int upto = (int) (Math.random() * parent1.picked.length);
		KnapIndividual offspring = new KnapIndividual();
		for(int i=0; i<upto; i++) {
			offspring.picked[i] = parent1.picked[i];
		}
		for(int i=upto; i<offspring.picked.length; i++) {
			offspring.picked[i] = parent2.picked[i];
		}
		return offspring;
	}
	
	// Mutate implementation
	@Override
	public KnapIndividual mutate(KnapIndividual i, boolean shouldCopy) {
		KnapIndividual ret = i;
		if(shouldCopy) {
			ret = copy(i);
		}
		int index = (int) (Math.random() * ret.picked.length);
		ret.picked[index] ^= true;
		return i;
	}

	private KnapIndividual copy(KnapIndividual i) {
		KnapIndividual copy= new KnapIndividual();
		System.arraycopy(i.picked, 0, copy.picked, 0, i.picked.length);
		return copy;
	}
	
}
