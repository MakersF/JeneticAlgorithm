package com.ja.algorithm;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.ja.callables.Crossover;
import com.ja.callables.EndCondition;
import com.ja.callables.Evaluation;
import com.ja.callables.Mutate;
import com.ja.callables.Selection;
import com.ja.callables.Selection.Parents;
import com.ja.pupulation.Fittness;
import com.ja.pupulation.Fittness.FitnessEntry;


public class GeneticAlgorithm<Chromosome> {

	private Collection<Chromosome> mPopulation;
	private final Fittness<Chromosome> mFittness = new Fittness<Chromosome>();
	private final Evaluation<Chromosome> mEvaluationFunction;
	private final Selection<Chromosome> mSelection;
	private final Crossover<Chromosome> mCrossover;
	private final float mCrossoverProbability;
	private final Mutate<Chromosome> mMutate;
	private final float mMutateProbability;
	private final Random mRand = new Random();
	private final EndCondition<Chromosome> mEndCondition;
	private final int mElitismNumber;

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
	}

	private void evaluate() {
		mFittness.clear();
		for(Chromosome i : mPopulation) {
			mFittness.put(mEvaluationFunction.evaluate(i), i);
		}
	}

	private Chromosome generateNewOffspring() {
		Parents<Chromosome> parents = mSelection.select(mFittness);
		Chromosome offspring;
		boolean cross = mRand.nextFloat() <= mCrossoverProbability;
		if(cross) {
			offspring = mCrossover.cross(parents.getParentA(), parents.getParentB());
		} else {
			offspring = parents.getParentA();
		}

		boolean mutate = mRand.nextFloat() <= mMutateProbability;
		if(mutate) {
			// if the offspring is just one of the parents (not crossed) when crossing get a copy and not modify it
			offspring = mMutate.mutate(offspring, !cross);
		}

		return offspring;
	}

	private void newGeneration() {
		int populationSize = mPopulation.size();
		Collection<Chromosome> newPopulation = new LinkedList<Chromosome>();

		// Preserve the best mElitismNumber Individuals
		Iterator<FitnessEntry<Chromosome>> it = mFittness.getElements().iterator();
		int elits = 0;
		for(int i=0; i< mElitismNumber && it.hasNext(); i++, elits++) {
			newPopulation.add(it.next().chromosome);
		}

		mSelection.onSelectionStart(mFittness);
		for(int i=0; i< populationSize - elits; i++) {
			Chromosome offspring = generateNewOffspring();
			newPopulation.add(offspring);
		}
		mSelection.onSelectionEnd();
		mPopulation = newPopulation;
		evaluate();
	}

	public void run() {
		evaluate();
		while(!mEndCondition.shouldEnd(mFittness)) {
			newGeneration();
		}
	}

	public FitnessEntry<Chromosome> getBest() {
		return mFittness.getElements().first();
	}
}
