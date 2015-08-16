package com.ja.pupulation;

import java.util.SortedSet;
import java.util.TreeSet;

public class Fittness<Individual> {

	SortedSet<Fittness.FitnessEntry<Individual>> elements = new TreeSet<Fittness.FitnessEntry<Individual>>();

	public void put(double fitness, Individual i) {
		elements.add(new FitnessEntry<Individual>(fitness, i));
	}

	public SortedSet<Fittness.FitnessEntry<Individual>> getElements() {
		return elements;
	}
	
	static public class FitnessEntry<Individual> implements Comparable<FitnessEntry<Individual>> {
		public double fitness;
		public Individual individual;
		
		public FitnessEntry(double pFitness, Individual pInd) {
			fitness = pFitness;
			individual = pInd;
		}

		@Override
		public int compareTo(FitnessEntry<Individual> o) {
			return fitness > o.fitness ? -1 :
					(fitness == o.fitness ? 0:
											+1);
		}
	}
}
