package com.ja.algorithm.selection;

import java.util.Iterator;

import com.ja.callables.Selection;
import com.ja.pupulation.Fittness;
import com.ja.pupulation.Fittness.FitnessEntry;

public class RankSelection<Individual> implements Selection<Individual>{
	/**
	 * See http://www.obitko.com/tutorials/genetic-algorithms/selection.php
	 */
	
	@Override
	public void onSelectionStart(Fittness<Individual> population) {
		
	}

	@Override
	public com.ja.callables.Selection.Parents<Individual> select(Fittness<Individual> population) {
		Individual a = pickOne(population);
		Individual b = pickOne(population);
		return new Parents<Individual>(a,b);
	}

	@Override
	public void onSelectionEnd() {
		
	}

	private Individual pickOne(Fittness<Individual> population) {
		// threshold picked in range [0, N*(N+1)/2], which is the sum 1..N
		int N = population.getElements().size();
		float threshold = (int) (Math.random() * (N * (N +1) / 2));
		int accumulator = 0;
		// Rank Selection: the best weights N, the worst weights 1. Since the Set is sorted the first should weight N and decrease by 1 at each iteration
		int currentWeight = N;
		for(Iterator<FitnessEntry<Individual>> it = population.getElements().iterator(); it.hasNext() && currentWeight >= 0;) {
			accumulator += currentWeight;
			if(accumulator >= threshold)
				return it.next().individual;
			it.next();
			currentWeight--;
		}
		throw new RuntimeException("It should never happen that the threshold is not reached before finishing iterating the population");
	}
}
