package org.pokeapp.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Config {
	private static Path path = Paths.get("eclipse-workspace/SOEN387/.config").toAbsolutePath();

	private static Map<String, String> loaded;

	private synchronized static void load() {
		// Config is only loaded from the file once.
		if (Config.loaded != null) {
			return;
		}
		Config.loaded = new HashMap<String, String>();

		// Config file is read into a string.
		byte[] config;
		try {
			System.out.printf("Reading config from '%s'\n", Config.path);
			config = Files.readAllBytes(Config.path);
		} catch (IOException e) {
			// Errors are logged, but not thrown.
			System.err.printf("Could not read config from '%s': %s\n", Config.path, e);
			return;
		}

		// Each line of the config file is parsed to extract name/value pairs in the form "NAME=VALUE".
		String[] lines = new String(config).split("\n");
		for (String line : lines) {
			String[] parts = line.split("=", 2);
			if (parts.length != 2) {
				continue;
			}
			Config.loaded.put(parts[0].trim(), parts[1].trim());
		}
	}

	public static String read(String name) {
		load();

		// May return null if the config value is not defined or the config file could not be read.
		return Config.loaded.get(name);
	}
}
