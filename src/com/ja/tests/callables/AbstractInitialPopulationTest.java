package com.ja.tests.callables;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractInitialPopulationTest<Chromosome> {

	protected abstract Collection<Chromosome> getInitialCollection();

	@Test
	public void checkIterationPredictability() {
		Collection<Chromosome> collection = getInitialCollection();
		Iterator<Chromosome> it1 = collection.iterator();
		Iterator<Chromosome> it2 = collection.iterator();
		for(;it1.hasNext() && it2.hasNext();) {
			assertTrue(it1.next().equals(it2.next()));
		}
		assertFalse(it1.hasNext() || it2.hasNext());
	}
}
