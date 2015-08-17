package com.ja.examples.knapsack_problem;

import com.ja.algorithm.GeneticAlgorithm;
import com.ja.algorithm.ProblemDescription;
import com.ja.algorithm.endcondition.GenerationsEndCondition;
import com.ja.pupulation.Fitness.FitnessEntry;

public class KnapsackMain {

	public static void main(String[] args) {
		
		ProblemDescription<KnapsackChromosome> problem = new ProblemDescription<KnapsackChromosome>();
		KnapsackEvalCrossMutate evaluateCrossoverMutate = new KnapsackEvalCrossMutate();
		problem.mCrossoverFunction = evaluateCrossoverMutate;
		problem.mEvaluationFunction = evaluateCrossoverMutate;
		problem.mMutateFunction = evaluateCrossoverMutate;
		problem.mInitialPopulation = KnapsackChromosome.initialPopulation(20);
		problem.mCrossoverProbability = 0.8f;
		problem.mMutateProbability = 0.15f;
		problem.mElitismNumber = 3;
		problem.mEndConditionFunction = new GenerationsEndCondition<KnapsackChromosome>(1000);
		//problem.numberOfThreads = 1;

		GeneticAlgorithm<KnapsackChromosome> solver = new GeneticAlgorithm<KnapsackChromosome>(problem);
		solver.run();
		FitnessEntry<KnapsackChromosome> best = solver.getBest();
		
		System.out.println("" + best.fitness + " - " + best.chromosome);
		
	}
}
