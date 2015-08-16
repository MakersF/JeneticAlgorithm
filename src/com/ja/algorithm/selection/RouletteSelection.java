package com.ja.algorithm.selection;

import java.util.Iterator;

import com.ja.callables.Selection;
import com.ja.pupulation.Fittness;
import com.ja.pupulation.Fittness.FitnessEntry;

public class RouletteSelection<Individual> implements Selection<Individual> {
	/**
	 * See http://www.obitko.com/tutorials/genetic-algorithms/selection.php
	 */
	private double sum = 0;

	@Override
	public void onSelectionStart(Fittness<Individual> population) {
		for(FitnessEntry<Individual> entry : population.getElements()) {
			sum += entry.fitness;
		}
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
		double threshold = Math.random() * sum;
		double accumulator = 0;
		for(Iterator<FitnessEntry<Individual>> it = population.getElements().iterator(); it.hasNext();) {
			FitnessEntry<Individual> entry = it.next();
			accumulator += entry.fitness;
			if(accumulator > threshold)
				return entry.individual;
			it.next();
		}
		throw new RuntimeException("It should never happen that the threshold is not reached before finishing iterating the population");
	}
}
