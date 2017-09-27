package com.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtility {

	public static void overWrite(String fileName, String content) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName,false);
		} catch (IOException e1) {
			
			e1.printStackTrace();
		};

		BufferedWriter bw = new BufferedWriter(fw);

		try {
			bw.write(content);
			bw.newLine();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		if (bw != null)
			try {
				bw.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		if (fw != null)
			try {
				fw.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}

				
	}
	
	
	public static void createOrAppendContent(String fileName, String content) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName,true);
		} catch (IOException e1) {
			
			e1.printStackTrace();
		};

		BufferedWriter bw = new BufferedWriter(fw);

		try {
			bw.write(content);
			bw.newLine();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		if (bw != null)
			try {
				bw.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		if (fw != null)
			try {
				fw.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}

				
	}
	
	public static ArrayList<String> readFromFile(String filename) {
		
		String file = filename;
		BufferedReader br = null;
		FileReader fr = null;
		ArrayList<String> result = new ArrayList<String>();
		
		//if file does not exist, just return
		File f = new File(filename);
		if(!f.exists() || f.isDirectory()) { 
		    return result;
		}
		
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		
		
		br = new BufferedReader(fr);

		String sCurrentLine;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				result.add(sCurrentLine);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return result;
		
	}

		
	
	
	
}
