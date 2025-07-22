package com.pclewis.mcpatcher;

import net.fabricmc.loader.api.FabricLoader;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipFile;

/**
 * Collection of static methods available to mods at runtime.  This class is always injected into
 * the output minecraft jar.
 */
public class MCPatcherUtils {
	public static final HashMap<String, String> MODS = new HashMap<>();
	static Config config = null;
	private static File minecraftDir = null;
	private static boolean debug = false;
	public static final Logger LOGGER = Logger.getLogger("MCPatcher");

	static {
		LOGGER.setLevel(debug ? Level.FINE : Level.INFO);
		MODS.put("hdtextures", "HD Textures");
		MODS.put("hdfont", "HD Font");
		MODS.put("bettergrass", "Better Grass");

		if (setGameDir(getDefaultGameDir())) {
			LOGGER.info("MCPatcherUtils initialized. Directory " + minecraftDir.getPath());
		} else {
			LOGGER.info("MCPatcherUtils initialized. Current directory " + new File(".").getAbsolutePath());
		}
	}

	private MCPatcherUtils() {
	}

	static File getDefaultGameDir() {
		return FabricLoader.getInstance().getGameDir().toFile();
	}

	static boolean setGameDir(File dir) {
		if (dir != null &&
			dir.isDirectory() &&
			new File(dir, "resources").isDirectory()) {
			minecraftDir = dir.getAbsoluteFile();
		} else {
			minecraftDir = null;
		}
		return loadProperties();
	}

	private static boolean loadProperties() {
		config = null;
		if (minecraftDir != null && minecraftDir.exists()) {
			try {
				config = new Config(minecraftDir);
			} catch (Exception e) {
				e.printStackTrace();
			}
			debug = getBoolean(Config.TAG_DEBUG, false);
			return true;
		}
		return false;
	}

	/**
	 * Get the path to a file/directory within the minecraft folder.
	 *
	 * @param subdirs zero or more path components
	 * @return combined path
	 */
	public static File getMinecraftPath(String... subdirs) {
		File f = minecraftDir;
		for (String s : subdirs) {
			f = new File(f, s);
		}
		return f;
	}

	/**
	 * Returns true if running inside game, false if running inside MCPatcher.  Useful for
	 * code shared between mods and runtime classes.
	 *
	 * @return true if in game
	 */
	public static boolean isGame() {
		return true;
	}

	/**
	 * Write a debug message to minecraft standard output.
	 *
	 * @param format printf-style format string
	 * @param params printf-style parameters
	 */
	public static void log(String format, Object... params) {
		LOGGER.fine(MessageFormat.format(format, params));
	}

	/**
	 * Write a warning message to minecraft standard output.
	 *
	 * @param format printf-style format string
	 * @param params printf-style parameters
	 */
	public static void warn(String format, Object... params) {
		LOGGER.warning(MessageFormat.format(format, params));
	}

	/**
	 * Write an error message to minecraft standard output.
	 *
	 * @param format printf-style format string
	 * @param params printf-style parameters
	 */
	public static void error(String format, Object... params) {
		LOGGER.severe(MessageFormat.format(format, params));
	}

	public static boolean getModEnabled(String mod) {
		if (config == null) {
			return true;
		}
		return config.getModEnabled(mod);
	}

	/**
	 * Gets a value from mcpatcher.xml.
	 *
	 * @param mod          name of mod
	 * @param tag          property name
	 * @param defaultValue default value if not found in .properties file
	 * @return String value
	 */
	public static String getString(String mod, String tag, Object defaultValue) {
		if (config == null) {
			return defaultValue == null ? null : defaultValue.toString();
		}
		String value = config.getModConfigValue(mod, tag);
		if (value == null && defaultValue != null) {
			value = defaultValue.toString();
			config.setModConfigValue(mod, tag, value);
		}
		return value;
	}

	/**
	 * Gets a value from mcpatcher.xml.
	 *
	 * @param tag          property name
	 * @param defaultValue default value if not found in .properties file
	 * @return String value
	 */
	public static String getString(String tag, Object defaultValue) {
		if (config == null) {
			return defaultValue == null ? null : defaultValue.toString();
		}
		String value = config.getConfigValue(tag);
		if (value == null && defaultValue != null) {
			value = defaultValue.toString();
			config.setConfigValue(tag, value);
		}
		return value;
	}

	/**
	 * Gets a value from mcpatcher.xml.
	 *
	 * @param mod          name of mod
	 * @param tag          property name
	 * @param defaultValue default value if not found in .properties file
	 * @return int value or 0
	 */
	public static int getInt(String mod, String tag, int defaultValue) {
		int value;
		try {
			value = Integer.parseInt(getString(mod, tag, defaultValue));
		} catch (NumberFormatException e) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * Gets a value from mcpatcher.xml.
	 *
	 * @param tag          property name
	 * @param defaultValue default value if not found in .properties file
	 * @return int value or 0
	 */
	public static int getInt(String tag, int defaultValue) {
		int value;
		try {
			value = Integer.parseInt(getString(tag, defaultValue));
		} catch (NumberFormatException e) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * Gets a value from mcpatcher.xml.
	 *
	 * @param mod          name of mod
	 * @param tag          property name
	 * @param defaultValue default value if not found in .properties file
	 * @return boolean value
	 */
	public static boolean getBoolean(String mod, String tag, boolean defaultValue) {
		String value = getString(mod, tag, defaultValue).toLowerCase();
		if (value.equals("false")) {
			return false;
		} else if (value.equals("true")) {
			return true;
		} else {
			return defaultValue;
		}
	}

	/**
	 * Gets a value from mcpatcher.xml.
	 *
	 * @param tag          property name
	 * @param defaultValue default value if not found in .properties file
	 * @return boolean value
	 */
	public static boolean getBoolean(String tag, boolean defaultValue) {
		String value = getString(tag, defaultValue).toLowerCase();
		if (value.equals("false")) {
			return false;
		} else if (value.equals("true")) {
			return true;
		} else {
			return defaultValue;
		}
	}

	/**
	 * Sets a value in mcpatcher.xml.
	 *
	 * @param mod   name of mod
	 * @param tag   property name
	 * @param value property value (must support toString())
	 */
	public static void set(String mod, String tag, Object value) {
		if (config != null) {
			config.setModConfigValue(mod, tag, value.toString());
		}
	}

	/**
	 * Set a global config value in mcpatcher.xml.
	 *
	 * @param tag   property name
	 * @param value property value (must support toString())
	 */
	static void set(String tag, Object value) {
		if (config != null) {
			config.setConfigValue(tag, value.toString());
		}
	}

	/**
	 * Remove a value from mcpatcher.xml.
	 *
	 * @param mod name of mod
	 * @param tag property name
	 */
	public static void remove(String mod, String tag) {
		if (config != null) {
			config.remove(config.getModConfig(mod, tag));
		}
	}

	/**
	 * Remove a global config value from mcpatcher.xml.
	 *
	 * @param tag property name
	 */
	static void remove(String tag) {
		if (config != null) {
			config.remove(config.getConfig(tag));
		}
	}

	/**
	 * Convenience method to close a stream ignoring exceptions.
	 *
	 * @param closeable closeable object
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Convenience method to close a zip file ignoring exceptions.
	 *
	 * @param zip zip file
	 */
	public static void close(ZipFile zip) {
		if (zip != null) {
			try {
				zip.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
