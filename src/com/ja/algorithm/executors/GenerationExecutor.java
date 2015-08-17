package com.ja.algorithm.executors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

import com.ja.callables.Crossover;
import com.ja.callables.Mutate;
import com.ja.callables.Selection;
import com.ja.callables.Selection.Parents;
import com.ja.pupulation.Fitness;

public class GenerationExecutor<Chromosome> implements Callable<Collection<Chromosome>> {

	private final Fitness<Chromosome> mFitness;
	private final Selection<Chromosome> mSelection;
	private final Crossover<Chromosome> mCrossover;
	private final float mCrossoverProbability;
	private final Mutate<Chromosome> mMutate;
	private final float mMutateProbability;
	private final int generations;
	
	public GenerationExecutor(Fitness<Chromosome> fitness, Selection<Chromosome> selection, Crossover<Chromosome> crossover, float crossoverProb,
								Mutate<Chromosome> mutate, float mutateProb, int generations) {
		mFitness = fitness;
		mSelection = selection;
		mCrossover = crossover;
		mCrossoverProbability = crossoverProb;
		mMutate = mutate;
		mMutateProbability = mutateProb;
		this.generations = generations;
	}

	@Override
	public Collection<Chromosome> call() throws Exception {
		Collection<Chromosome> newPartialPopulation = new ArrayList<Chromosome>(generations);
		for(int i=0; i < generations; i++) {
			newPartialPopulation.add(generateNewOffspring());
		}
		return newPartialPopulation;
	}

	private Chromosome generateNewOffspring() {
		Parents<Chromosome> parents = mSelection.select(mFitness);
		Chromosome offspring;
		boolean cross = ThreadLocalRandom.current().nextFloat() <= mCrossoverProbability;
		if(cross) {
			offspring = mCrossover.cross(parents.getParentA(), parents.getParentB());
		} else {
			offspring = parents.getParentA();
		}

		boolean mutate = ThreadLocalRandom.current().nextFloat() <= mMutateProbability;
		if(mutate) {
			// if the offspring is just one of the parents (not crossed) when crossing get a copy and do not modify the input one
			offspring = mMutate.mutate(offspring, !cross);
		}

		return offspring;
	}
}
