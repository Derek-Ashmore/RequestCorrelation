/*
 * This software is licensed under the Apache License, Version 2.0
 * (the "License") agreement; you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.force66.correlate.log4j;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

/**
 * Log4J pattern layout that uses enhanced Parser to inject the correlation id in log messages.  Use %I
 * in the pattern to provide the correlation id in log messages.
 * Sample pattern:
 * <li>%d{yyyy-MM-dd HH:mm:ss} %I %-5p %c{1}:%L - %m%n</li>
 * @author D. Ashmore
 *
 */
public class CorrelationPatternLayout extends PatternLayout {

	@Override
	protected PatternParser createPatternParser(String pattern) {
		return new CorrelationPatternParser(pattern);
	}

}
