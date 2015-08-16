package com.ja.callables;

public interface Crossover<Chromosome> {
	/**
	 * Returns the offspring chromosome of the two parents. MUST return a new object, meaning that cross(a,b) != a && cross(a,b) != b
	 * @param parent1 The first parent
	 * @param parent2 The second parent
	 * @return The chromosome which resulted from combining the two parents
	 */
	Chromosome cross(Chromosome parent1, Chromosome parent2);
}
