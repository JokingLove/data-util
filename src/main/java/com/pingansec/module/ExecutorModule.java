package com.pingansec.module;

import com.google.inject.AbstractModule;
import com.pingansec.executor.Executor;
import com.pingansec.executor.ImportExcelToDbExecutor;

public class ExecutorModule extends AbstractModule{
	@Override
	protected void configure() {
		bind(Executor.class).to(ImportExcelToDbExecutor.class);
	}
}
