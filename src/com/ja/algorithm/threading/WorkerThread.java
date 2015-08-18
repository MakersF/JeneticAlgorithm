package com.ja.algorithm.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerThread extends Thread {

	private final static AtomicInteger counter = new AtomicInteger(0);
	// Would it be a good design to insert a poison pill to make the polling stop and terminate,
	// or is it fine to leave the 500ms delay between shutdown() and actual termination?
	// public static final Runnable POISON_PILL = new Runnable() {@Override public void run() {return;}};

	private final Object monitor = new Object();

	private final BlockingQueue<Runnable> tasks;
	private volatile boolean shutdown = false;
	private volatile boolean running = false;
	private volatile boolean terminated = false;
	private volatile boolean terminate = false;

	public WorkerThread(BlockingQueue<Runnable> producerQueue) {
		super("WorkerThread " + counter.getAndIncrement());
		if(producerQueue == null)
			throw new NullPointerException("producerQueue can not be null");
		tasks = producerQueue;
	}

	public boolean isWorking() {
		return running;
	}

	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		if(timeout < 0)
			throw new IllegalArgumentException("Timeout needs to be a positive long");
		if(unit == null)
			throw new NullPointerException("unit can nto be null");
		long remaining = unit.toNanos(timeout);

		while(!terminated) {
			long old = System.nanoTime();
			synchronized (monitor) {
				monitor.wait(unit.toMillis(timeout));
			}
			long now = System.nanoTime();
			remaining -= now - old;
			if(remaining < 0)
				return false;
		}
		return true;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public boolean isShutdown() {
		return shutdown;
	}

	public void shutdownNow() {
		terminate = true;
	}

	public void shutdown() {
		shutdown = true;
	}

	@Override
	public void run() {
		while(!terminate) {
			try {
				if(tasks.isEmpty() && shutdown) {
					break;
				}
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
		synchronized (monitor) {
			terminated = true;
			monitor.notifyAll();
		}
	}
}
