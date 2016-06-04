package com.it_zabota.jira.telephony.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.it_zabota.jira.telephony.encryptng.SecurityFile;

public class Modi {
	
	private static  String FILE_ENCRIPT_KEY = "ROOT_789_WERT";
	private static String propPathEnc = new File("telephony.dat").getAbsolutePath();
	private static String propPath ;
	private static final Properties prop = new Properties();
	
	public static Properties getProp() {
		return prop;
	}

	public static void main(String[] args) {
		Modi modi = new Modi();
		modi.setProp(args);		
	}
	
	private void setProp(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String str = args[i];
			Modi.getProp().setProperty(str.substring(0, str.indexOf("|")), str.substring(str.indexOf("|") + 1));
		}
		saveProp();		
	}
	
	private void saveProp() {
		try {
			// set the properties value			
			prop.store(new OutputStreamWriter(new FileOutputStream(propPath), "UTF-8"), "Edited ".concat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
				FileInputStream fis = new FileInputStream(propPath);
				FileOutputStream fos = new FileOutputStream(propPathEnc);
				SecurityFile.encrypt(FILE_ENCRIPT_KEY, fis, fos);
				fis.close();
				fos.close();				
				//new File(propPath).delete();				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		
	
	private void init() {
		File tempFile;
		try {
			tempFile = File.createTempFile("prop", ".tmp");
			propPath = tempFile.getAbsolutePath();
			System.out.println(propPath);
			FileInputStream fis = new FileInputStream(propPathEnc);
			FileOutputStream fos = new FileOutputStream(propPath);    		
			SecurityFile.decrypt(FILE_ENCRIPT_KEY, fis, fos);			
			
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(propPath), StandardCharsets.UTF_8));
			prop.load(in);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
		
	protected Modi() {
		init();
	}
}
