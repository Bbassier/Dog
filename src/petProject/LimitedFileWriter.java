package petProject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LimitedFileWriter implements AutoCloseable {

	private FileWriter fileWriter;
	private PrintWriter printWriter;
	private Integer currentWriterCount;
	private Integer currentFileCount;
	private String outputFilePrefix;
	private Integer numberOfDigitsOfFiles;
	private Integer maxNumberOfElementsInFile;

	public LimitedFileWriter(String outputFilePrefix, Integer numberOfDigitsOfFiles,
			Integer maxNumberOfElementsInFile) {
		currentFileCount = 0;
		currentWriterCount = 0;
		this.outputFilePrefix = outputFilePrefix;
		this.numberOfDigitsOfFiles = numberOfDigitsOfFiles;
		this.maxNumberOfElementsInFile = maxNumberOfElementsInFile;
		fileWriter = null;
		printWriter = null;
		setUpNextFile();
	}

	private void setUpNextFile() {
		String formatted = String.format("%0" + numberOfDigitsOfFiles + "d", currentFileCount);
		String nameOfOutputFile = outputFilePrefix + formatted + ".txt";
		closePrinterAndFile();
		try {
			this.fileWriter = new FileWriter(nameOfOutputFile);
			this.printWriter = new PrintWriter(fileWriter);
			currentFileCount++;
		} catch (IOException e) {
			
		}

	}

	public void writeLine(String string) {
		currentWriterCount++;
		if (currentWriterCount < maxNumberOfElementsInFile) {
			printWriter.println(string);
		} else {
			currentWriterCount = 0;
			setUpNextFile();
			printWriter.println(string);
		}
	}

	private void closePrinterAndFile() {
		if (printWriter != null) {
			printWriter.close();
		}
		if (fileWriter != null) {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	@Override
	public void close() throws Exception {
		closePrinterAndFile();
	}

}
