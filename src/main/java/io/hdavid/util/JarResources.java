package io.hdavid.util;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JarResources {

	public static List<String> getResourceLines(String resourcePath) {
		List<String> resp = new ArrayList<>();
		try {
			InputStream ras = JarResources.class.getResourceAsStream(resourcePath);
			InputStreamReader isr = new InputStreamReader(ras, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				resp.add(line);
			}
			br.close();
			isr.close();
			ras.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	public static byte[] getResourceBytes(String resourcePath) {

		try {
			InputStream ras = JarResources.class.getResourceAsStream(resourcePath);
			byte[] bytes = ByteStreams.toByteArray(ras);

			ras.close();
			return bytes;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getResourceAsString(String resourcePath) {
		return getResourceLines(resourcePath).stream().collect(Collectors.joining("\n"));
	}
//	@SneakyThrows
//	public static String getResourceAsBase64(String resourcePath) {
//		byte[] arr =  ByteStreams.toByteArray(JarResources.class.getClassLoader().getResourceAsStream(resourcePath));
//		return Base64.getEncoder().encodeToString(arr);
//	}

	@SneakyThrows
	public static Map<String, Object> getFileResourcesInPath(String path, boolean asByteArray, String ... extensionsToLookFor) { // "path/to/your/dir/"
		Map<String, Object> fileName_content = new HashMap<>();
		List<String> xtlf = Arrays.asList(extensionsToLookFor);
		String[] resourcesOnFolder = new String(ByteStreams.toByteArray(JarResources.class.getResourceAsStream(path)), StandardCharsets.UTF_8).split("\n");
		for (String name : resourcesOnFolder) {
			if (xtlf.stream().anyMatch(x->name.endsWith(x))) {
				try {
					fileName_content.put(name, asByteArray ?
							getResourceBytes(path + name) :
							getResourceAsString(path + name));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return fileName_content;
//		Map<String, Object> fileName_content = new HashMap<>();
//		CodeSource src = JarResources.class.getProtectionDomain().getCodeSource();
//		Stream<String> xtlf = Arrays.asList(extensionsToLookFor).stream();
//		if (src != null) {
//			URL jar = src.getLocation();
//			ZipInputStream zip = new ZipInputStream(jar.openStream());
//			while(true) {
//				ZipEntry e = zip.getNextEntry();
//				if (e == null)
//					break;
//				String name = e.getName();
//				if (name.startsWith(path) && xtlf.anyMatch(x->name.endsWith(x))) {
//					fileName_content.put(name, asByteArray ?
//							getResourceBytes(path) : getResourceAsString(name));
//				}
//			}
//		}
//		else {
//			throw new Exception("getFileResourcesInPath: "+ path);
//		}
//		return fileName_content;
	}

}
