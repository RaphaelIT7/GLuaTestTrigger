package de.Raphael.Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.json.JSONObject;

public class DataCache {
	private static JSONObject m_pObject = null;
	private static String m_strFileName = "DataCache.json";
	public static void Init() {
		try {
			Path filePath = Paths.get(m_strFileName);
			if (Files.exists(filePath)) {
				String json = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
				m_pObject = new JSONObject(json);
			} else {
				m_pObject = new JSONObject();
				Save();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> Save()));
	}

	public static void Save() {
		try {
			String json = m_pObject.toString(2);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(m_strFileName));
			writer.write(json);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void Set(String key, Object obj) {
		m_pObject.put(key, obj);
	}
	
	public static Object Get(String key) {
		if (!Has(key))
			return null;
		
		return m_pObject.get(key);
	}
	
	public static boolean Has(String key) {
		return m_pObject.has(key);
	}
}
