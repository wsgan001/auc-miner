package aucminer.transactions;

import aucminer.core.TransactionList;

public class TransactionUtility {
	
	public static CDModel buildOldCDModel(String oldProjectName, String saveDirectory, boolean includField, boolean storeToFile) {
		CDModelBuilder builder = new CDModelBuilder(oldProjectName, saveDirectory, includField, storeToFile);
		return builder.extract();
	}
	
	public static CDModel buildNewCDModel(String newProjectName, String saveDirectory, boolean includField, boolean storeToFile) {
		CDModelBuilder builder = new CDModelBuilder(newProjectName, saveDirectory, includField, storeToFile);
		return builder.extract();
	}
	
	public static TransactionList extractTransactions(CDModel oldModel, CDModel newModel, boolean usingSplit, int splitThreshold, 
			boolean storeToFile, String saveDirectory) {
		TranscationExtractor extractor = new TranscationExtractor(oldModel, newModel, saveDirectory);
		return extractor.extract(storeToFile, usingSplit, splitThreshold);
	}
}
