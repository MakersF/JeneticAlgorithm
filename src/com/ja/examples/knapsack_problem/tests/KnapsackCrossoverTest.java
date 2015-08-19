package com.ja.examples.knapsack_problem.tests;

import com.ja.callables.Crossover;
import com.ja.examples.knapsack_problem.KnapsackChromosome;
import com.ja.examples.knapsack_problem.KnapsackEvalCrossMutate;
import com.ja.tests.callables.AbstractCrossoverTests;

public class KnapsackCrossoverTest extends AbstractCrossoverTests<KnapsackChromosome> {

	@Override
	protected Crossover<KnapsackChromosome> getCrossoverCallable() {
		return new KnapsackEvalCrossMutate();
	}

	@Override
	protected KnapsackChromosome getOneChromosome() {
		return new KnapsackChromosome();
	}

}
