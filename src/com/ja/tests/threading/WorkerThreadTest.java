package com.ja.tests.threading;

import static org.junit.Assert.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import com.ja.algorithm.threading.WorkerThread;

public class WorkerThreadTest {

	int num_tasks = 10;
	int thread_index = 0;
	int terminated = 0;

	@Before
	public void resetCounters() {
		thread_index =0;
		terminated = 0;
	}

	@Test
	public void allExecuted() {
		BlockingQueue<Runnable> tasks = new ArrayBlockingQueue<Runnable>(num_tasks);
		for(int i=0; i < num_tasks; i++) {
			tasks.add(new Runnable() {
				
				@Override
				public void run() {
					terminated++;
					
				}
			});
		}
		WorkerThread t = new WorkerThread(tasks);
		t.start();
		assertEquals(thread_index, terminated);
		t.shutdown();
	}
	
	@Test
	public void waitedExecution() throws InterruptedException {
		BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<Runnable>();
		WorkerThread t = new WorkerThread(tasks);
		t.start();
		for(int i=0; i<num_tasks; i++) {
			tasks.put(new Runnable() {
				
				@Override
				public void run() {
					terminated++;
					
				}
			});
			Thread.sleep(100);
		}
		while(!tasks.isEmpty()) ;
		assertTrue(true);
		t.shutdown();
		
	}

	@Test
	public void shutdown() throws InterruptedException {
		BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<Runnable>();
		WorkerThread t = new WorkerThread(tasks);
		t.start();
		final AtomicInteger counter = new AtomicInteger(0);
		for(int i=0; i<num_tasks; i++) {
			tasks.put(new Runnable() {
				
				@Override
				public void run() {
					try {
						if(counter.compareAndSet(0, 1)) {
							terminated++;
							counter.compareAndSet(1, 2);
							return;
						}
						Thread.sleep(500);
					} catch (InterruptedException e) {
						throw new RuntimeException();
					}
					
				}
			});
		}
		while(counter.get() != 2) ;
		assertEquals(1, terminated);
		t.shutdown();
		while(!t.isTerminated()) ;
	}
}
