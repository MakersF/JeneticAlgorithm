package com.ja.algorithm.threading;

import java.util.ArrayDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class WorkerThread extends Thread {

	private final Object monitor = new Object();

	private final ArrayDeque<FutureTaskPair<?>> tasks = new ArrayDeque<FutureTaskPair<?>>();
	private boolean shutdown = false;
	private boolean running = false;
	private boolean terminated = false;

	
	public <V> Future<V> submitWork(Callable<V> task) {
		if(task == null)
			throw new NullPointerException("Task may not be null");
		TaskFuture<V> future = new TaskFuture<V>();
		FutureTaskPair<V> taskFuture = new FutureTaskPair<V>(future, task);
		synchronized (monitor) {
			// Enqueue a new task to work on and notify the thread in case it was waiting for new work.
			tasks.addLast(taskFuture);
			monitor.notifyAll();
		}
		return future;
	}

	public boolean isWorking() {
		synchronized (monitor) {
			return running;
		}
	}

	public boolean awaitTermination(long time, TimeUnit unit) throws InterruptedException {
		long total_wait_nanos = 0;
		while(!terminated) {
			if(total_wait_nanos >= unit.toNanos(time)) {
				return false;
			}
			long startTime = System.nanoTime();
			synchronized (monitor) {
				monitor.wait();
			}
			total_wait_nanos += System.nanoTime() - startTime;
		}
		return true;
	}

	public void shutdown() {
		shutdown = true;
		synchronized (monitor) {
			monitor.notifyAll();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while(true) {
			FutureTaskPair<Object> task = null;
			synchronized (monitor) {
				// Check if there is a task waiting in the queue
				 task = (FutureTaskPair<Object>) tasks.poll();
				 if(task != null)
					 running = true;
			}
			if(task != null) {
				executeTask(task);
			} else {
				// If there is no more task to execute
				// wait for some task to be submitted
				try {
					synchronized (monitor) {
						running = false;
						// If shutdown is set to true terminate instead of waiting
						if(shutdown) {
							break;
						}
						monitor.wait();
					}
				} catch (InterruptedException e) {
					continue;
				}
			}
		}
		terminated = true;
		synchronized (monitor) {
			monitor.notifyAll();
		}
	}

	private void executeTask(FutureTaskPair<Object> task) {
		boolean run = false;
		synchronized (task.future) {
			if(!task.future.askedToCancel()) {
				task.future.onExecutionStart();
				run = true;
			}
		}
		if(run) {
			try {
				Object result = task.callable.call();
				task.future.setResult(result);
			} catch (Exception e) {
				task.future.setException(e);
			}
		}
	}

	private static class FutureTaskPair<V> {
		public TaskFuture<V> future;
		public Callable<V> callable;

		public FutureTaskPair(TaskFuture<V> f, Callable<V> c) {
			future = f;
			callable = c;
		}
	}
}
