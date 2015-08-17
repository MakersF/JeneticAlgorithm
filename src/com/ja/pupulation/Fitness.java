package com.ja.pupulation;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

public class Fitness<Chromosome> {

	private final SortedSet<Fitness.FitnessEntry<Chromosome>> elements = new TreeSet<Fitness.FitnessEntry<Chromosome>>();
	private final FitnessEntryPool<Chromosome> pool = new FitnessEntryPool<Chromosome>();

	public void put(double fitness, Chromosome i) {
		FitnessEntry<Chromosome> entry = pool.getOne(fitness, i);
		synchronized (elements) {
			elements.add(entry);
		}
	}

	public SortedSet<Fitness.FitnessEntry<Chromosome>> getElements() {
		return elements;
	}
	
	public void clear() {
		for(FitnessEntry<Chromosome> entry : elements) {
			pool.recycle(entry);
		}
		elements.clear();
	}

	static public class FitnessEntry<Chromosome> implements Comparable<FitnessEntry<Chromosome>> {
		public double fitness;
		public Chromosome chromosome;
		
		public FitnessEntry(double pFitness, Chromosome chrom) {
			fitness = pFitness;
			chromosome = chrom;
		}

		@Override
		public int compareTo(FitnessEntry<Chromosome> o) {
			return fitness > o.fitness ? -1 :
					(fitness == o.fitness ? 0:
											+1);
		}
	}

	static public class FitnessEntryPool<Chromosome> {
		Queue<FitnessEntry<Chromosome>> pool = new ArrayDeque<Fitness.FitnessEntry<Chromosome>>();

		public FitnessEntry<Chromosome> getOne(double pFitness, Chromosome chrom) {
			FitnessEntry<Chromosome> entry = null;
			synchronized (pool) {
				entry = pool.poll();
			}

			if(entry == null)
				return new FitnessEntry<Chromosome>(pFitness, chrom);
			entry.fitness = pFitness;
			entry.chromosome = chrom;
			return entry;
		}

		public void recycle(FitnessEntry<Chromosome> chrom) {
			synchronized (pool) {
				pool.add(chrom);
			}
		}
	}
}
