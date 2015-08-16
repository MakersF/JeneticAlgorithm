package com.ja.callables;

import com.ja.pupulation.Fittness;

public interface Selection<Individual> {
	/**
	 * Called at the beginning of the new generation process.
	 * @param population The fitness of the current population
	 */
	void onSelectionStart(Fittness<Individual> population);
	
	/**
	 * Selects a pair of parents to be used to generate a new Individual to add to the new population
	 * @param population The fitness of the current population
	 * @return The pair of parents to use for generating a new Individual
	 */
	Parents<Individual> select(Fittness<Individual> population);
	
	/**
	 * Called at the end of the generation process.
	 */
	void onSelectionEnd();

	public static class Parents<Individual> {
		Individual parentA;
		Individual parentB;
		
		public Parents() {};
		public Parents(Individual a, Individual b) {
			parentA = a;
			parentB = b;
		};
		public Individual getParentA() { return parentA;}
		public Individual getParentB() { return parentB;}
	}
}
