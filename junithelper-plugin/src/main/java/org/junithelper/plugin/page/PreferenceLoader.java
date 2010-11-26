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
package org.junithelper.plugin.page;

import org.eclipse.jface.preference.IPreferenceStore;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.constant.Preference;
import org.junithelper.plugin.util.MockGenUtil;

/**
 * PreferenceLoader
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public class PreferenceLoader {

	public String commonSrcMainJavaDir;
	public String commonTestMainJavaDir;

	public boolean isJUnitVersion3;
	public boolean isJUnitVersion4;
	public boolean isUsingJUnitHelperRuntime;

	public boolean isTestClassGenEnabled;

	public boolean isTestMethodGenEnabled;
	public boolean isTestMethodGenArgsEnabled;
	public boolean isTestMethodGenReturnEnabled;
	public boolean isTestMethodGenExceptions;
	public boolean isTestMethodGenNotBlankEnabled;
	public boolean isTestMethodGenEnabledSupportJMock2;
	public boolean isTestMethodGenEnabledSupportEasyMock;
	public boolean isTestMethodGenEnabledSupportMockito;
	public boolean isTestMethodGenEnabledSupportJMockit;
	public boolean isTestMethodGenIncludePublic;
	public boolean isTestMethodGenIncludeProtected;
	public boolean isTestMethodGenIncludePackageLocal;
	public boolean isTestMethodGenExecludeAccessors;

	public String testMethodDelimiter;
	public String testMethodArgsPrefix;
	public String testMethodArgsDelimiter;
	public String testMethodReturnPrefix;
	public String testMethodReturnDelimiter;
	public String testMethodExceptionPrefix;
	public String testMethodExceptionDelimiter;

	public String classToExtend;

	/**
	 * Constructor
	 * 
	 * @param store
	 *            preference store
	 */
	public PreferenceLoader(IPreferenceStore store) {
		initialize(store);
	}

	/**
	 * Initialize the preference info.
	 * 
	 * @param store
	 *            preference store
	 */
	public void initialize(IPreferenceStore store) {

		// loading preference store
		if (store == null) {
			store = Activator.getDefault().getPreferenceStore();
		}

		// source code directory
		commonSrcMainJavaDir = store.getString(Preference.Common.srcMainPath);
		commonTestMainJavaDir = store.getString(Preference.Common.srcTestPath);

		// test class gen
		isTestClassGenEnabled = store
				.getBoolean(Preference.TestClassGen.enabled);

		// test method gen
		isTestMethodGenEnabled = store
				.getBoolean(Preference.TestMethodGen.enabled);
		isTestMethodGenArgsEnabled = store
				.getBoolean(Preference.TestMethodGen.enabledArgs);
		isTestMethodGenReturnEnabled = store
				.getBoolean(Preference.TestMethodGen.enabledReturn);
		isTestMethodGenExceptions = store
				.getBoolean(Preference.TestMethodGen.enabledException);

		// test method template implementation gen
		isTestMethodGenNotBlankEnabled = store
				.getBoolean(Preference.TestMethodGen.enabledTestMethodSampleImpl);

		// access modifier
		isTestMethodGenIncludePublic = store
				.getBoolean(Preference.TestMethodGen.includePublic);
		isTestMethodGenIncludeProtected = store
				.getBoolean(Preference.TestMethodGen.includeProtected);
		isTestMethodGenIncludePackageLocal = store
				.getBoolean(Preference.TestMethodGen.includePackageLocal);

		// accessors
		isTestMethodGenExecludeAccessors = store
				.getBoolean(Preference.TestMethodGen.excludesAccessors);

		// mock object frameworks
		isTestMethodGenEnabledSupportJMock2 = MockGenUtil.isUsingJMock2(store);
		isTestMethodGenEnabledSupportEasyMock = MockGenUtil
				.isUsingEasyMock(store);
		isTestMethodGenEnabledSupportMockito = MockGenUtil
				.isUsingMockito(store);
		isTestMethodGenEnabledSupportJMockit = MockGenUtil
				.isUsingJMockit(store);

		// junit version
		String version = store.getString(Preference.TestClassGen.junitVersion);
		isJUnitVersion3 = version.equals(Preference.TestClassGen.junitVersion3);
		isJUnitVersion4 = version.equals(Preference.TestClassGen.junitVersion4);

		// using junit helper runtime
		isUsingJUnitHelperRuntime = store
				.getBoolean(Preference.TestClassGen.usingJunitHelperRuntimeLib);

		// test method signature
		testMethodDelimiter = store
				.getString(Preference.TestMethodGen.delimiter);
		testMethodArgsPrefix = store
				.getString(Preference.TestMethodGen.argsPrefix);
		testMethodArgsDelimiter = store
				.getString(Preference.TestMethodGen.argsDelimiter);
		testMethodReturnPrefix = store
				.getString(Preference.TestMethodGen.returnPrefix);
		testMethodReturnDelimiter = store
				.getString(Preference.TestMethodGen.returnDelimiter);
		testMethodExceptionPrefix = store
				.getString(Preference.TestMethodGen.exceptionPrefix);
		testMethodExceptionDelimiter = store
				.getString(Preference.TestMethodGen.exceptionDelimiter);

		// class to extend
		classToExtend = store.getString(Preference.TestClassGen.classToExtend);

	}

}