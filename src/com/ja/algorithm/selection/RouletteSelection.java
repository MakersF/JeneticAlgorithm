package com.ja.algorithm.selection;

import java.util.Iterator;

import com.ja.callables.Selection;
import com.ja.pupulation.Fittness;
import com.ja.pupulation.Fittness.FitnessEntry;

public class RouletteSelection<Chromosome> implements Selection<Chromosome> {
	/**
	 * See http://www.obitko.com/tutorials/genetic-algorithms/selection.php
	 */
	private double sum = 0;

	@Override
	public void onSelectionStart(Fittness<Chromosome> population) {
		for(FitnessEntry<Chromosome> entry : population.getElements()) {
			sum += entry.fitness;
		}
	}

	@Override
	public com.ja.callables.Selection.Parents<Chromosome> select(Fittness<Chromosome> population) {
		Chromosome a = pickOne(population);
		Chromosome b = pickOne(population);
		return new Parents<Chromosome>(a,b);
	}

	@Override
	public void onSelectionEnd() {
		
	}

	private Chromosome pickOne(Fittness<Chromosome> population) {
		double threshold = Math.random() * sum;
		double accumulator = 0;
		for(Iterator<FitnessEntry<Chromosome>> it = population.getElements().iterator(); it.hasNext();) {
			FitnessEntry<Chromosome> entry = it.next();
			accumulator += entry.fitness;
			if(accumulator > threshold)
				return entry.chromosome;
			it.next();
		}
		throw new RuntimeException("It should never happen that the threshold is not reached before finishing iterating the population");
	}
}
