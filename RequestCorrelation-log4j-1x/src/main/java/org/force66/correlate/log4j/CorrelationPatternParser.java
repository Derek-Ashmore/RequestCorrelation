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

import org.apache.log4j.helpers.PatternParser;

/**
 * Adds the "I" representing the correlation Id to the log4j pattern layout.  Just specify this
 * class as the "layout" in your log4j configuration.
 * @author D. Ashmore
 * @see org.apache.log4j.helpers.PatternParser
 */
public class CorrelationPatternParser extends PatternParser {

	public CorrelationPatternParser(String pattern) {
		super(pattern);
	}

	@Override
	protected void finalizeConverter(char c) {
		if (c == 'I') {
			currentLiteral.setLength(0);
			addConverter(new CorrelationPatternConverter());
		}
		else {
			super.finalizeConverter(c);
		}
	}

}
