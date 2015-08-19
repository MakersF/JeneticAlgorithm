package com.ja.examples.knapsack_problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class KnapsackChromosome {
	
	static public int number_of_objects = 4;
	static public int[] value_per_item = {5,2,3,4};
	static public int[] weight_per_item = {3,2,2,4};
	
	static public int knapsack_max_weigth = 4;
	static private Random RANDOM = new Random();
	boolean picked[];
	
	public KnapsackChromosome() {
		picked = new boolean[number_of_objects];
		for(int i=0; i < number_of_objects; i++) {
			picked[i] = RANDOM.nextBoolean();
		}
	}
	
	@Override
	public String toString() {
		return Arrays.toString(picked);
	}

	static public Collection<KnapsackChromosome> initialPopulation(int size) {
		List<KnapsackChromosome> initialPopulation = new ArrayList<KnapsackChromosome>(size);
		for(int i=0; i < size; i++) {
			initialPopulation.add(new KnapsackChromosome());
		}
		return initialPopulation;
	}
}
