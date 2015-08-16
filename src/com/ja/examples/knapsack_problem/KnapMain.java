package com.ja.examples.knapsack_problem;

import java.util.ArrayList;
import java.util.List;

import com.ja.algorithm.GeneticAlgorithm;
import com.ja.algorithm.ProblemDescription;
import com.ja.pupulation.Fittness.FitnessEntry;

public class KnapMain {

	public static void main(String[] args) {
		ProblemDescription<KnapIndividual> problem = new ProblemDescription<KnapIndividual>();
		KnapEvalCrossMutate ecm = new KnapEvalCrossMutate();
		problem.mCrossoverFunction = ecm;
		problem.mEvaluationFunction = ecm;
		problem.mMutateFunction = ecm;
		problem.mCrossoverProbability = 0.8f;
		problem.mMutateProbability = 0.15f;
		problem.mElitismNumber = 3;
		
		List<KnapIndividual> initialPopulation = new ArrayList<KnapIndividual>(20);
		for(int i=0; i< 20; i++) {
			initialPopulation.add(new KnapIndividual());
		}
		
		problem.mInitialPopulation = initialPopulation;

		GeneticAlgorithm<KnapIndividual> solver = new GeneticAlgorithm<KnapIndividual>(problem);
		solver.run();
		FitnessEntry<KnapIndividual> best = solver.getBest();
		System.out.println("" + best.fitness + " - " + best.individual);

	}

}
