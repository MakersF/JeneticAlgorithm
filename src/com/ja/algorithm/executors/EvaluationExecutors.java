package com.ja.algorithm.executors;

import java.util.Iterator;
import java.util.concurrent.Callable;

import com.ja.callables.Evaluation;
import com.ja.pupulation.Fitness;

public class EvaluationExecutors<Chromosome> implements Callable<Void> {

	private final int stride;
	private final Iterator<Chromosome> it;
	private final Evaluation<Chromosome> eval;
	private final Fitness<Chromosome> fitness;
	
	public EvaluationExecutors(Iterator<Chromosome> iter, int numberOfThreads, int offset, Evaluation<Chromosome> evaluation, Fitness<Chromosome> fitness) {
		it = iter;
		stride = numberOfThreads - 1;
		eval = evaluation;
		this.fitness = fitness;
		skip(offset);
	}

	@Override
	public Void call() throws Exception {
		while(it.hasNext()) {
			Chromosome chrom = it.next();
			fitness.put(eval.evaluate(chrom), chrom);
			skip(stride);
		}
		return null;
	}

	private void skip(int number) {
		for(int i=0; i < number && it.hasNext(); i++) {
			it.next();
		}
	}
}
