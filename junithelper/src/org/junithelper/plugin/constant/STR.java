/* 
 * Copyright 2009-2010 junithelper.org. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */
package org.junithelper.plugin.constant;


/**
 * STR<br>
 * <br>
 * String constant values.<br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public interface STR {

	/**
	 * Windows OS directory separator
	 */
	public static final String WINDOWS_DIR_SEP = "\\\\";

	/**
	 * Default directory separator
	 */
	public static final String DIR_SEP = "/";

	/**
	 * Empty String
	 */
	public static final String EMPTY = "";

	/**
	 * Comma
	 */
	public static final String COMMA = ",";

	/**
	 * Dot
	 */
	public static final String DOT = ".";

	/**
	 * Asterisk
	 */
	public static final String ASTERISK = "*";

	/**
	 * Carriage return
	 */
	public static final String CARRIAGE_RETURN = "\r";

	/**
	 * Line feed
	 */
	public static final String LINE_FEED = "\n";

	/**
	 * Java file extension
	 */
	public static final String JAVA_EXP = ".java";

	/**
	 * Properties file extension
	 */
	public static final String PROPERTIES_EXP = ".properties";

	/**
	 * The suffix of test case name
	 */
	public static final String SUFFIX_OF_TESTCASE = "Test";

	/**
	 * Auto generated todo message
	 */
	public static final String AUTO_GEN_MSG_TODO = " TODO auto-generated by JUnit Helper.";

	/**
	 * Regular expressions
	 * 
	 * @author Kazuhiro Sera
	 */
	public static class RegExp {
		/**
		 * Dot
		 */
		public static final String DOT = "\\.";

		/**
		 * Not white/space values not required
		 */
		public static final String NOT_S_NOREQUIRED = "[^\\s]*?";

		/**
		 * Not white/space values required
		 */
		public static final String NOT_S_REQUIRED = "[^\\s]+?";

		/**
		 * While/space not required
		 */
		public static final String WHITE_AND_SPACE = "\\s*";

		/**
		 * Java file extension
		 */
		public static final String JAVA_EXP = "\\.java";

	}

}
