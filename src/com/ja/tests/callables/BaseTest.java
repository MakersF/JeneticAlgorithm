package com.ja.tests.callables;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class BaseTest<Chromosome> {

	/**
	 * Should return a new Chromosome (a new Object)
	 * @return A Chromosome
	 */
	protected abstract Chromosome getOneChromosome();
	
	@Test
	public void producesDifferentChromosomes() {
		assertTrue(getOneChromosome() != getOneChromosome());
	}
	
}
