package com.pingansec.module;

import com.google.inject.AbstractModule;

public class MainModule extends AbstractModule {
	@Override
	protected void configure() {
		install(new ExecutorModule());
		install(new ProcessorModule());
	}
}
