package com.ja.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ja.algorithm.executors.EvaluationExecutors;
import com.ja.algorithm.executors.GenerationExecutor;
import com.ja.callables.Crossover;
import com.ja.callables.EndCondition;
import com.ja.callables.Evaluation;
import com.ja.callables.Mutate;
import com.ja.callables.Selection;
import com.ja.pupulation.Fitness;
import com.ja.pupulation.Fitness.FitnessEntry;


public class GeneticAlgorithm<Chromosome> {

	private Collection<Chromosome> mPopulation;
	private final Fitness<Chromosome> mFitness = new Fitness<Chromosome>();
	private final Evaluation<Chromosome> mEvaluationFunction;
	private final Selection<Chromosome> mSelection;
	private final Crossover<Chromosome> mCrossover;
	private final float mCrossoverProbability;
	private final Mutate<Chromosome> mMutate;
	private final float mMutateProbability;
	private final EndCondition<Chromosome> mEndCondition;
	private final int mElitismNumber;
	private final int numberOfThreads;
	private final ExecutorService executor;

	public GeneticAlgorithm(ProblemDescription<Chromosome> problemDescription) {
		if(	problemDescription.mCrossoverFunction == null ||
			problemDescription.mEndConditionFunction == null ||
			problemDescription.mEvaluationFunction == null ||
			problemDescription.mInitialPopulation == null ||
			problemDescription.mMutateFunction == null ||
			problemDescription.mSelectionFunction == null
		) {
			throw new IllegalArgumentException("All the problemDescription fields must be not null");
		}
		if( problemDescription.mInitialPopulation.size() < 2  ) {
			throw new IllegalArgumentException("The initial population must be have more than 1 individual");
		}
		mPopulation = problemDescription.mInitialPopulation;
		mEvaluationFunction = problemDescription.mEvaluationFunction;
		mSelection = problemDescription.mSelectionFunction;
		mCrossover = problemDescription.mCrossoverFunction;
		mMutate = problemDescription.mMutateFunction;
		mEndCondition = problemDescription.mEndConditionFunction;

		mCrossoverProbability = Math.max(problemDescription.mCrossoverProbability, 0);
		mMutateProbability = Math.max(problemDescription.mMutateProbability, 0);
		mElitismNumber = Math.max(problemDescription.mElitismNumber, 0);
		
		numberOfThreads = Math.max(problemDescription.numberOfThreads, 1);
		executor = Executors.newFixedThreadPool(numberOfThreads);
	}

	private void evaluate() {
		mFitness.clear();
		// Every thread evaluates one numberOfThreads-th of the total population
		// The population collection needs to have PREDICTABLE ITERATION ORDER
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>(numberOfThreads);
		for(int i=0; i < numberOfThreads; i++) {
			tasks.add(new EvaluationExecutors<Chromosome>(mPopulation.iterator(), numberOfThreads, i, mEvaluationFunction, mFitness));
		}
		while(true) {
			try {
				executor.invokeAll(tasks);
				break;
			} catch (InterruptedException e) {
				mFitness.clear();
				continue;
			}
		}
	}

	private void newGeneration() {
		int populationSize = mPopulation.size();
		Collection<Chromosome> newPopulation = new LinkedList<Chromosome>();

		// Preserve the best mElitismNumber Individuals
		Iterator<FitnessEntry<Chromosome>> it = mFitness.getElements().iterator();
		int elits = 0;
		for(int i=0; i< mElitismNumber && it.hasNext(); i++, elits++) {
			newPopulation.add(it.next().chromosome);
		}

		mSelection.onSelectionStart(mFitness);
		int generatedPerThread = (int) Math.ceil(((populationSize - elits) / numberOfThreads));
		List<Callable<Collection<Chromosome>>> tasks = new ArrayList<Callable<Collection<Chromosome>>>(numberOfThreads);
		for(int i=0; i< numberOfThreads; i++) {
			tasks.add(new GenerationExecutor<Chromosome>(mFitness, mSelection, mCrossover, mCrossoverProbability, mMutate, mMutateProbability, generatedPerThread));
		}
		while(true) {
			try {
				for(Future<Collection<Chromosome>> coll : executor.invokeAll(tasks)) {
					newPopulation.addAll(coll.get());
				}
				break;
			} catch (InterruptedException e) {
				continue;
			} catch (ExecutionException e) {
				throw new RuntimeException("Should not happen");
			}
		}
		mSelection.onSelectionEnd();
		mPopulation = newPopulation;
		evaluate();
	}

	public void run() {
		evaluate();
		while(!mEndCondition.shouldEnd(mFitness)) {
			newGeneration();
		}
		executor.shutdown();
	}

	public FitnessEntry<Chromosome> getBest() {
		return mFitness.getElements().first();
	}
}
