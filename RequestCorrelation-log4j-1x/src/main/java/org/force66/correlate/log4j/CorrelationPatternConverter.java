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

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;
import org.force66.correlate.RequestCorrelationContext;

/**
 * Supplies the current correlation id for Log4J.
 * @author D. Ashmore
 *
 */
public class CorrelationPatternConverter extends PatternConverter {

	public CorrelationPatternConverter() {}

	public CorrelationPatternConverter(FormattingInfo formattingInfo) {
		super(formattingInfo);
	}

	@Override
	protected String convert(LoggingEvent event) {
		return RequestCorrelationContext.getCurrent().getCorrelationId();
	}

}
