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


public class GeneticAlgorithm<Individual> {
	
	private Collection<Individual> mPopulation;
	private Fittness<Individual> mFittness;
	private Evaluation<Individual> mEvaluationFunction;
	private Selection<Individual> mSelection;
	private Crossover<Individual> mCrossover;
	private float mCrossoverProbability;
	private Mutate<Individual> mMutate;
	private float mMutateProbability;
	private Random mRand = new Random();
	private EndCondition<Individual> mEndCondition;
	private int mElitismNumber;
	
	public GeneticAlgorithm(ProblemDescription<Individual> problemDescription) {
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
		mFittness = new Fittness<Individual>();
		for(Individual i : mPopulation) {
			mFittness.put(mEvaluationFunction.evaluate(i), i);
		}
	}

	private Individual generateNewOffspring() {
		Parents<Individual> parents = mSelection.select(mFittness);
		Individual offspring;
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
		Collection<Individual> newPopulation = new LinkedList<Individual>();
		
		// Preserve the best mElitismNumber Individuals
		Iterator<FitnessEntry<Individual>> it = mFittness.getElements().iterator();
		int elits = 0;
		for(int i=0; i< mElitismNumber && it.hasNext(); i++, elits++) {
			newPopulation.add(it.next().individual);
		}
		
		mSelection.onSelectionStart(mFittness);
		for(int i=0; i< populationSize - elits; i++) {
			Individual offspring = generateNewOffspring();
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

	public FitnessEntry<Individual> getBest() {
		return mFittness.getElements().first();
	}
}
