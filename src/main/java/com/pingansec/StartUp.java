package com.pingansec;

import com.google.inject.Guice;
import com.pingansec.executor.Executor;
import com.pingansec.module.MainModule;

public class StartUp {
	public static void main(String[] args) {
		Executor executor = Guice.createInjector(new MainModule())
		.getInstance(Executor.class);
		
		executor.run();
	}
}
