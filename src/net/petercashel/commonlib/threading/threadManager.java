package net.petercashel.commonlib.threading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("rawtypes")
public class threadManager {
	private int processors = Runtime.getRuntime().availableProcessors();
	public ExecutorService exec = Executors.newFixedThreadPool(processors);
	
	public threadManager() {
		
	}
	
	public Future addRunnable(threadRunnable r) {
		return exec.submit(r);
	}

	public Future addRunnable(Runnable r) {
		return exec.submit(r);
	}

	@SuppressWarnings("unchecked")
	public Future addCallable(Callable r) {
		return exec.submit(r);
	}
	
	public void shutdown() {
		exec.shutdown();
	}
}
