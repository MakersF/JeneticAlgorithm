package com.ja.callables;

public interface Evaluation<Chromosome> {
	/**
	 * Evaluates the current chromosome
	 * @param a The chromosome to evaluate
	 * @return The fitness of the chromosome
	 */
	double evaluate(Chromosome chromosome);
}
