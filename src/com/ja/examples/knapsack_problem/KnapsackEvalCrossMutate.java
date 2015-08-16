package com.ja.examples.knapsack_problem;

import com.ja.callables.Crossover;
import com.ja.callables.Evaluation;
import com.ja.callables.Mutate;

public class KnapsackEvalCrossMutate implements Evaluation<KnapsackChromosome>, Crossover<KnapsackChromosome>, Mutate<KnapsackChromosome>{

	// Evaluation implementation

	@Override
	public double evaluate(KnapsackChromosome a) {
		int total_weight = 0;
		int total_value = 0;
		for(int i=0; i< a.picked.length; i++) {
			total_value += (a.picked[i] ? 1:0) * KnapsackChromosome.value_per_item[i];
			total_weight += (a.picked[i] ? 1:0) * KnapsackChromosome.weight_per_item[i];
		}
		if(total_weight > KnapsackChromosome.knapsack_max_weigth) 
			return 0;
		return total_value;
	}
	
	// Crossover implementation
	@Override
	public KnapsackChromosome cross(KnapsackChromosome parent1, KnapsackChromosome parent2) {
		int upto = (int) (Math.random() * parent1.picked.length);
		KnapsackChromosome offspring = new KnapsackChromosome();
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
	public KnapsackChromosome mutate(KnapsackChromosome i, boolean shouldCopy) {
		KnapsackChromosome ret = i;
		if(shouldCopy) {
			ret = copy(i);
		}
		int index = (int) (Math.random() * ret.picked.length);
		ret.picked[index] ^= true;
		return i;
	}

	private KnapsackChromosome copy(KnapsackChromosome i) {
		KnapsackChromosome copy= new KnapsackChromosome();
		System.arraycopy(i.picked, 0, copy.picked, 0, i.picked.length);
		return copy;
	}
	
}
