package com.ja.callables;

import com.ja.pupulation.Fittness;

public interface EndCondition<Individual> {
	/**
	 * Return whether the algorithm should stop iterating or should continue generating new Individuals
	 * @param fittness The fitness of the current population
	 * @return True if the algorithm should stop running, false otherwise
	 */
	boolean shouldEnd(Fittness<Individual> fittness);
}
