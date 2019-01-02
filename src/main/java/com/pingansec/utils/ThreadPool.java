package com.pingansec.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ThreadPool {
	public static final int nThreads = 10;
	public static final String 	THREAD_PREFIX = "O";
	
	private static class ThreadPoolHolder{
		private static ExecutorService executor = Executors.newFixedThreadPool(nThreads);
	}

	public static ExecutorService getInstance() {
		return ThreadPoolHolder.executor;
	}
	
	public static Future<?> submit(Runnable task) {
		return getInstance().submit(task);
	}

	public static void shutDown() {
		ExecutorService instance = getInstance();
		if(!instance.isShutdown()) {
			instance.shutdown();
		}
	}
}
