package com.ja.callables;

public interface Evaluation<Individual> {
	/**
	 * Evaluates the current individual
	 * @param a The individual to evaluate
	 * @return The fitness of the individual
	 */
	double evaluate(Individual a);
}
