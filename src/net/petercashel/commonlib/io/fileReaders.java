package net.petercashel.commonlib.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

public class fileReaders {
	
	public List<String> readFileToString (String path ) {
		List<String> Lines = null;
		try {
			Lines = Files.readAllLines(  
					FileSystems.getDefault().getPath(path), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			//Return, because we loaded nothing. So don't parse it.
			return null;
		}
		return Lines;
	}



}
