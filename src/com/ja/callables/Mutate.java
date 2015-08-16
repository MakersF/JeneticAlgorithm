package com.ja.callables;

public interface Mutate<Chromosome> {
	/**
	 * Get an chromosome as parameter and returns a mutation of it
	 * @param i The chromosome to mutate
	 * @param shouldCopy If shouldCopy is set true, the returned chromosome MUST be a new object, meaning that mutate(i, true) != i. 
	 * 					In case shouldCopy is set to false, the chromosome can be directly mutated and returned
	 * @return The mutated chromosome
	 */
	Chromosome mutate(Chromosome i, boolean shouldCopy);
}
