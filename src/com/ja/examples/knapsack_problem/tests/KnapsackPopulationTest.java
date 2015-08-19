package com.ja.examples.knapsack_problem.tests;

import java.util.Collection;

import com.ja.examples.knapsack_problem.KnapsackChromosome;
import com.ja.tests.callables.AbstractInitialPopulationTest;

public class KnapsackPopulationTest extends AbstractInitialPopulationTest<KnapsackChromosome> {

	@Override
	protected Collection<KnapsackChromosome> getInitialCollection() {
		return KnapsackChromosome.initialPopulation(20);
	}

}
