package org.junithelper.core.generator.impl;

import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.TestingTarget;
import org.junithelper.core.config.extension.ExtInstantiation;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.TestMethodMeta;
import org.junithelper.core.util.Assertion;

final class DefaultGeneratorUtil {

	private DefaultGeneratorUtil() {
	}

	static boolean isPublicMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
		return methodMeta.accessModifier == AccessModifier.Public && target.isPublicMethodRequired;
	}

	static boolean isProtectedMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
		return methodMeta.accessModifier == AccessModifier.Protected && target.isProtectedMethodRequired;
	}

	static boolean isPackageLocalMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
		return methodMeta.accessModifier == AccessModifier.PackageLocal && target.isPackageLocalMethodRequired;
	}

	static void appendExtensionSourceCode(StringBuilder buf, String code) {

		Assertion.mustNotBeNull(code, "code");

		String[] separatedListBySemicolon = code.split(StringValue.Semicolon);
		for (String separatedBySemicolon : separatedListBySemicolon) {
			if (separatedBySemicolon != null && separatedBySemicolon.trim().length() > 0) {
				separatedBySemicolon = separatedBySemicolon.trim().replaceAll(StringValue.CarriageReturn, "");
				String[] lines = separatedBySemicolon.split(StringValue.LineFeed);
				for (String line : lines) {
					if (line != null && line.trim().length() > 0) {
						appendTabs(buf, 2);
						buf.append(line.trim());
						if (!line.endsWith("{") && !line.endsWith("}")) {
							buf.append(StringValue.Semicolon);
						}
						appendCRLF(buf);
					}
				}
			}
		}
	}

	static void appendExtensionPostAssignSourceCode(StringBuilder buf, String code, String[] fromList, String to) {

		Assertion.mustNotBeNull(code, "code");
		Assertion.mustNotBeNull(fromList, "fromList");
		Assertion.mustNotBeNull(to, "to");

		String[] separatedListBySemicolon = code.split(StringValue.Semicolon);
		for (String separatedBySemicolon : separatedListBySemicolon) {
			if (separatedBySemicolon != null && separatedBySemicolon.trim().length() > 0) {
				separatedBySemicolon = separatedBySemicolon.trim().replaceAll(StringValue.CarriageReturn, "");
				String[] lines = separatedBySemicolon.split(StringValue.LineFeed);
				for (String line : lines) {
					if (line != null && line.trim().length() > 0) {
						appendTabs(buf, 2);
						line = line.trim();
						for (String from : fromList) {
							line = line.replaceAll(from, to);
						}
						buf.append(line);
						if (!line.endsWith("{") && !line.endsWith("}")) {
							buf.append(StringValue.Semicolon);
						}
						appendCRLF(buf);
					}
				}
			}
		}
	}

	static String getInstantiationSourceCode(Configuration config, TestMethodMeta testMethodMeta) {

		Assertion.mustNotBeNull(config, "config");
		Assertion.mustNotBeNull(testMethodMeta, "testMethodMeta");

		// -----------
		// Extension
		if (config.isExtensionEnabled && config.extConfiguration.extInstantiations != null) {
			for (ExtInstantiation ins : config.extConfiguration.extInstantiations) {
				if (isCanonicalClassNameUsed(ins.canonicalClassName, testMethodMeta.classMeta.name,
						testMethodMeta.classMeta)) {
					// add import list
					for (String newImport : ins.importList) {
						testMethodMeta.classMeta.importedList.add(newImport);
					}
					// instantiation code
					// e.g. Sample target = new Sample();
					StringBuilder buf = new StringBuilder();
					if (ins.preAssignCode != null && ins.preAssignCode.trim().length() > 0) {
						appendExtensionSourceCode(buf, ins.preAssignCode);
					}
					buf.append(StringValue.Tab);
					buf.append(StringValue.Tab);
					buf.append(testMethodMeta.classMeta.name);
					buf.append(" target = ");
					buf.append(ins.assignCode.trim());
					buf.append(StringValue.CarriageReturn);
					buf.append(StringValue.LineFeed);
					if (ins.postAssignCode != null && ins.postAssignCode.trim().length() > 0) {
						appendExtensionPostAssignSourceCode(buf, ins.postAssignCode, new String[] { "\\{instance\\}" },
								"target");
					}
					return buf.toString();
				}
			}
		}
		// TODO better implementation
		return new DefaultConstructorGenerator().getFirstInstantiationSourceCode(testMethodMeta.classMeta);
	}

	static void appendIfNotExists(StringBuilder buf, String src, String importLine) {

		Assertion.mustNotBeNull(buf, "buf");
		Assertion.mustNotBeNull(src, "src");
		Assertion.mustNotBeNull(importLine, "importLine");

		String oneline = src.replaceAll(RegExp.CRLF, StringValue.Space);
		importLine = importLine.replace(StringValue.CarriageReturn + StringValue.LineFeed, StringValue.Empty);
		String importLineRegExp = importLine.replaceAll("\\s+", "\\\\s+").replaceAll("\\.", "\\\\.")
				.replaceAll("\\*", "\\\\*");
		if (!oneline.matches(RegExp.Anything_ZeroOrMore_Min + importLineRegExp + RegExp.Anything_ZeroOrMore_Min)) {
			buf.append(importLine);
			buf.append(StringValue.CarriageReturn);
			buf.append(StringValue.LineFeed);
		}

	}

	static boolean isCanonicalClassNameUsed(String expectedCanonicalClassName, String usedClassName,
			ClassMeta targetClassMeta) {

		Assertion.mustNotBeNull(expectedCanonicalClassName, "expectedCanonicalClassName");
		Assertion.mustNotBeNull(usedClassName, "usedClassName");
		Assertion.mustNotBeNull(targetClassMeta, "targetClassMeta");

		if (usedClassName.equals(expectedCanonicalClassName)
				|| usedClassName.equals(expectedCanonicalClassName.replace("java.lang.", ""))) {
			// canonical class name
			// e.g.
			// "com.example.ArgBean"
			return true;
		} else {
			// imported type
			// e.g.
			// (same package)
			// import com.example.*;
			// import com.example.ArgBean;
			// "ArgBean"
			String[] extSplitted = expectedCanonicalClassName.split("\\.");
			String extClassName = extSplitted[extSplitted.length - 1];
			if (usedClassName.equals(extClassName)) {
				String extInSamplePackage = targetClassMeta.packageName + "." + extClassName;
				if (extInSamplePackage.equals(expectedCanonicalClassName)) {
					return true;
				} else {
					for (String imported : targetClassMeta.importedList) {
						String target = expectedCanonicalClassName.replaceFirst(extClassName, "");
						if (imported.matches(expectedCanonicalClassName) || imported.matches(target + ".+")) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	static void appendCRLF(StringBuilder buf) {
		buf.append(StringValue.CarriageReturn);
		buf.append(StringValue.LineFeed);
	}

	static void appendTabs(StringBuilder buf, int times) {
		Assertion.mustNotBeNull(buf, "buf");
		Assertion.mustBeGreaterThanOrEqual(times, 0, "times");
		for (int i = 0; i < times; i++) {
			buf.append(StringValue.Tab);
		}
	}

}
