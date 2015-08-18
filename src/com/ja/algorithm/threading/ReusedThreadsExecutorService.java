package com.ja.algorithm.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ReusedThreadsExecutorService extends AbstractExecutorService {

	private final WorkerThread[] threads;
	private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<Runnable>();
	private boolean shutdown = false;
	private boolean terminated = false;

	public ReusedThreadsExecutorService(int numberOfThreads) {
		if(numberOfThreads <= 0) {
			throw new IllegalArgumentException("numberOfThreads must be greater than 0.");
		}
		threads = new WorkerThread[numberOfThreads];
		for(int i=0; i < numberOfThreads; i++) {
			threads[i] = new WorkerThread(tasks);
			threads[i].start();
		}
	}
	@Override
	public void execute(Runnable command) {
		if(command == null)
			throw new NullPointerException("command can not be null");
		if(!shutdown) {
			while(true) {
				try {
					tasks.put(command);
				} catch (InterruptedException e) {
					continue;
				}
			}
		}
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		if(timeout < 0)
			throw new IllegalArgumentException("Timeout needs to be a positive long");
		if(unit == null)
			throw new NullPointerException("unit can nto be null");
		if(terminated)
			return true;
		long remaining = unit.toNanos(timeout);
		for(WorkerThread wt : threads) {
			long old = System.nanoTime();
			if(!wt.awaitTermination(remaining, TimeUnit.NANOSECONDS))
				return false;
			long now = System.nanoTime();
			remaining -= now - old;
			if(remaining < 0 ) {
				return false;
			}
		}
		terminated = true;
		return true;
	}

	@Override
	public boolean isShutdown() {
		return shutdown;
	}

	@Override
	public boolean isTerminated() {
		if(terminated)
			return true;
		if(!shutdown)
			return false;

		for(WorkerThread wt : threads) {
			if(!wt.isTerminated())
				return false;
		}
		terminated = true;
		return terminated;
	}

	@Override
	public void shutdown() {
		shutdown = true;
	}

	@Override
	public List<Runnable> shutdownNow() {
		shutdown();
		for(WorkerThread wt : threads) {
			wt.shutdown();
		}

		List<Runnable> remaining_tasks = new ArrayList<Runnable>(tasks.size());
		tasks.drainTo(remaining_tasks);
		return remaining_tasks;
	}
}
