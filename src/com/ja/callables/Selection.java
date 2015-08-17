package com.ja.callables;

import com.ja.pupulation.Fitness;

public interface Selection<Chromosome> {
	/**
	 * Called at the beginning of the new generation process.
	 * @param population The fitness of the current population
	 */
	void onSelectionStart(Fitness<Chromosome> population);
	
	/**
	 * Selects a pair of parents to be used to generate a new Individual to add to the new population
	 * @param population The fitness of the current population
	 * @return The pair of parents to use for generating a new Individual
	 */
	Parents<Chromosome> select(Fitness<Chromosome> population);
	
	/**
	 * Called at the end of the generation process.
	 */
	void onSelectionEnd();

	public static class Parents<Chromosome> {
		Chromosome parentA;
		Chromosome parentB;
		
		public Parents() {};
		public Parents(Chromosome a, Chromosome b) {
			parentA = a;
			parentB = b;
		};
		public Chromosome getParentA() { return parentA;}
		public Chromosome getParentB() { return parentB;}
	}
}
