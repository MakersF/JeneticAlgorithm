package com.ja.examples.knapsack_problem;

import java.util.Arrays;
import java.util.Random;

public class KnapIndividual {
	
	static public int number_of_objects = 4;
	static public int[] value_per_item = {5,2,3,4};
	static public int[] weight_per_item = {3,2,2,4};
	
	static public int knapsack_max_weigth = 4;
	
	boolean picked[];
	
	public KnapIndividual() {
		picked = new boolean[number_of_objects];
		Random r = new Random();
		for(int i=0; i < number_of_objects; i++) {
			picked[i] = r.nextBoolean();
		}
	}
	
	@Override
	public String toString() {
		return Arrays.toString(picked);
	}
}
