package com.ja.algorithm.threading;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TaskFuture<V> implements Future<V> {

	private Object monitor = new Object();
	private V result = null;
	private Exception exception = null;
	private boolean resultSubmitted = false;
	/**
	 * Set to true when the task is started
	 */
	private boolean run = false;
	/**
	 * Set to true when the task is asked to be cancelled
	 */
	private boolean askedToCancel = false;
	private boolean cancelled = false;

	
	/**
	 * This method is called before the start of the execution
	 */
	synchronized void onExecutionStart() {
		run = true;
	}

	synchronized void setResult(V res) {
		if(!resultSubmitted && exception == null) {
			result = res;
			resultSubmitted = true;
			monitor.notifyAll();
		}
	}

	synchronized void setException(Exception e) {
		if(!resultSubmitted && exception == null) {
			exception = e;
			monitor.notifyAll();
		}
	}

	synchronized boolean askedToCancel() {
		return askedToCancel;
	}
	
	@Override
	public synchronized boolean cancel(boolean arg0) {
		askedToCancel = true;
		if(run)
			return false;
		cancelled = true;
		monitor.notifyAll();
		return true;
	}

	@Override
	public synchronized V get() throws InterruptedException, ExecutionException {
		if(cancelled) {
			throw new CancellationException();
		}
		while(!resultSubmitted && exception == null) {
			monitor.wait();
		}
		if(exception != null)
			throw new ExecutionException(exception);
		return result;
	}

	@Override
	public synchronized V get(long time, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if(cancelled) {
			throw new CancellationException();
		}
		long total_wait_nanos = 0;
		while(!resultSubmitted && exception == null) {
			if(total_wait_nanos >= unit.toNanos(time)) {
				throw new TimeoutException();
			}
			long startTime = System.nanoTime();
			monitor.wait(unit.toMillis(time));
			total_wait_nanos += System.nanoTime() - startTime;
		}
		if(exception != null)
			throw new ExecutionException(exception);
		return result;
	}

	@Override
	public synchronized boolean isCancelled() {
		return cancelled;
	}

	@Override
	public synchronized boolean isDone() {
		if(isCancelled() || resultSubmitted || exception != null)
			return true;
		return false;
	}

}
