package com.ja.callables;

public interface Crossover<Individual> {
	/**
	 * Returns the offspring individual of the two parents. MUST return a new object, meaning that cross(a,b) != a && cross(a,b) != b
	 * @param parent1 The first parent
	 * @param parent2 The second parent
	 * @return The Individual which resulted from combining the two parents
	 */
	Individual cross(Individual parent1, Individual parent2);
}
