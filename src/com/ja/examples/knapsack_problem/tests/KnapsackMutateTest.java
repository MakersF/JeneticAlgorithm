package com.ja.examples.knapsack_problem.tests;

import com.ja.callables.Mutate;
import com.ja.examples.knapsack_problem.KnapsackChromosome;
import com.ja.examples.knapsack_problem.KnapsackEvalCrossMutate;
import com.ja.tests.callables.AbstractMutateTest;

public class KnapsackMutateTest extends AbstractMutateTest<KnapsackChromosome> {

	@Override
	protected Mutate<KnapsackChromosome> getMutateCallable() {
		return new KnapsackEvalCrossMutate();
	}

	@Override
	protected KnapsackChromosome getOneChromosome() {
		return new KnapsackChromosome();
	}

}
