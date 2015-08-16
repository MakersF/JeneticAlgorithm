package com.ja.algorithm;

import java.util.Collection;

import com.ja.algorithm.endcondition.GenerationsEndCondition;
import com.ja.algorithm.selection.RankSelection;
import com.ja.callables.Crossover;
import com.ja.callables.EndCondition;
import com.ja.callables.Evaluation;
import com.ja.callables.Mutate;
import com.ja.callables.Selection;

public class ProblemDescription<Individual> {

	public Collection<Individual> mInitialPopulation;
	public Evaluation<Individual> mEvaluationFunction;
	public Selection<Individual> mSelectionFunction = new RankSelection<Individual>();
	public Crossover<Individual> mCrossoverFunction;
	public Mutate<Individual> mMutateFunction;
	public EndCondition<Individual> mEndConditionFunction = new GenerationsEndCondition<Individual>(1000);
	public float mCrossoverProbability;
	public float mMutateProbability;
	public int mElitismNumber;
}
