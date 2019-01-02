package com.pingansec.module;

import com.google.inject.AbstractModule;
import com.pingansec.processor.ImportExcelToDbProcessor;
import com.pingansec.processor.Processor;

public class ProcessorModule extends AbstractModule{
	@Override
	protected void configure() {
		bind(Processor.class).to(ImportExcelToDbProcessor.class);
	}
}
