package com.ja.callables;

public interface Mutate<Individual> {
	/**
	 * Get an individual as parameter and returns a mutation of it
	 * @param i The individual to mutate
	 * @param shouldCopy If shouldCopy is set true, the returned individual MUST be a new object, meaning that mutate(i, true) != i. 
	 * 					In case shouldCopy is set to false, the Individual i can be directly mutated and returned
	 * @return The mutated individual
	 */
	Individual mutate(Individual i, boolean shouldCopy);
}
