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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.force66.correlate.RequestCorrelationContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CorrelationPatternLayoutTest {
	
	ByteArrayOutputStream outStream;
	PrintStream systemOut;
	Logger logger;

	@Before
	public void setUp() throws Exception {
		outStream = new ByteArrayOutputStream();
		systemOut = System.out;
		System.setOut(new PrintStream(outStream));
		logger = Logger.getLogger(CorrelationPatternLayoutTest.class);
		
		RequestCorrelationContext.getCurrent().setCorrelationId("testId");
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(systemOut);
	}

	@Test
	public void testBasic() throws Exception {
		logger.info("Hi there!");
		systemOut.println(outStream.toString());
		Assert.assertTrue(outStream.toString().contains(" testId "));
	}

}
