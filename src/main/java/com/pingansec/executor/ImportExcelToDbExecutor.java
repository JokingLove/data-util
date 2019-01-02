package com.pingansec.executor;

import javax.inject.Inject;

import com.pingansec.processor.Processor;

public class ImportExcelToDbExecutor implements Executor{
	
	private final Processor processor;
	
	@Inject	
	public ImportExcelToDbExecutor(Processor processor) {
		this.processor = processor;
	}

	public void run() {
		processor.process(null);
	}

}
