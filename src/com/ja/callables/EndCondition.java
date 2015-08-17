package com.ja.callables;

import com.ja.pupulation.Fitness;

public interface EndCondition<Choromosome> {
	/**
	 * Return whether the algorithm should stop iterating or should continue generating new chromosomes
	 * @param fittness The fitness of the current population of chromosomes
	 * @return True if the algorithm should stop running, false otherwise
	 */
	boolean shouldEnd(Fitness<Choromosome> fittness);
}
