package com.ja.algorithm.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class WorkerThread extends Thread {

	private final Object monitor = new Object();

	private final BlockingQueue<Runnable> tasks;
	private volatile boolean shutdown = false;
	private volatile boolean running = false;
	private volatile boolean terminated = false;

	public WorkerThread(BlockingQueue<Runnable> producerQueue) {
		tasks = producerQueue;
	}

	public boolean isWorking() {
		return running;
	}

	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		if(!shutdown)
			return false;
		long total_wait_nanos = 0;
		while(!terminated) {
			if(total_wait_nanos >= unit.toNanos(timeout)) {
				return false;
			}
			long startTime = System.nanoTime();
			synchronized (monitor) {
				monitor.wait(unit.toMillis(timeout));
			}
			total_wait_nanos += System.nanoTime() - startTime;
		}
		return true;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public boolean isShutdown() {
		return shutdown;
	}

	public void shutdown() {
		shutdown = true;
	}

	@Override
	public void run() {
		while(!shutdown) {
			try {
				Runnable task = tasks.poll(500, TimeUnit.MILLISECONDS);
				if(task != null) {
					running = true;
					task.run();
					running = false;
				}
			} catch (InterruptedException e) {
				continue;
			} catch (Exception e) {
				// If a task throws an Exception we do not want to shutdown the thread. Just catch, ignore and continue with the next task.
				continue;
			}
		}
		terminated = true;
	}
}
