package com.ja.tests.callables;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ja.callables.Crossover;

public abstract class AbstractCrossoverTests<Chromosome> extends BaseTest<Chromosome> {
	
	/**
	 * @return The Mutate callback to test
	 */
	protected abstract Crossover<Chromosome> getCrossoverCallable();

	
	@Test
	public void differentObjects() {
		Chromosome p1 = getOneChromosome();
		Chromosome p2 = getOneChromosome();
		Crossover<Chromosome> crossover = getCrossoverCallable();
		
		Chromosome offspring = crossover.cross(p1, p2);
		assertTrue(offspring != p1);
		assertTrue(offspring != p2);
	}
}
