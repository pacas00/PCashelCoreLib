package net.petercashel.commonlib.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class fileWriters {
	
	public void writeStringsToFile (String path, List<String> lines ) {
		try {
			Files.deleteIfExists(FileSystems.getDefault().getPath(path));
			Files.write(FileSystems.getDefault().getPath(path), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}
