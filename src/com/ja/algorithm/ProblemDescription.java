package com.ja.algorithm;

import java.util.Collection;

import com.ja.algorithm.endcondition.GenerationsEndCondition;
import com.ja.algorithm.selection.RankSelection;
import com.ja.callables.Crossover;
import com.ja.callables.EndCondition;
import com.ja.callables.Evaluation;
import com.ja.callables.Mutate;
import com.ja.callables.Selection;

public class ProblemDescription<Chromosome> {

	public Collection<Chromosome> mInitialPopulation;
	public Evaluation<Chromosome> mEvaluationFunction;
	public Selection<Chromosome> mSelectionFunction = new RankSelection<Chromosome>();
	public Crossover<Chromosome> mCrossoverFunction;
	public Mutate<Chromosome> mMutateFunction;
	public EndCondition<Chromosome> mEndConditionFunction = new GenerationsEndCondition<Chromosome>(1000);
	public float mCrossoverProbability;
	public float mMutateProbability;
	public int mElitismNumber;
}
