package com.ja.tests.callables;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ja.callables.Mutate;

public abstract class AbstractMutateTest<Chromosome> extends BaseTest<Chromosome> {
	
	/**
	 * @return The Mutate callback to test
	 */
	protected abstract Mutate<Chromosome> getMutateCallable();
	
	@Test
	public void shouldCopy() {
		Mutate<Chromosome> m = getMutateCallable();
		Chromosome chromosome = getOneChromosome();
		
		Chromosome mutated = m.mutate(chromosome, true);
		assertTrue(mutated != chromosome);
	}
}
