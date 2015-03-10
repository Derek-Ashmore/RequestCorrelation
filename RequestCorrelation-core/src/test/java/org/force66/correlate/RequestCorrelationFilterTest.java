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
package org.force66.correlate;

import org.force66.mock.servletapi.MockFilterChain;
import org.force66.mock.servletapi.MockFilterConfig;
import org.force66.mock.servletapi.MockRequest;
import org.force66.mock.servletapi.MockResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequestCorrelationFilterTest {
	
	RequestCorrelationFilter filter;
	MockFilterConfig filterConfig;
	MockFilterChain filterChain;
	MockRequest request;
	MockResponse response;

	@Before
	public void setUp() throws Exception {
		filter = new RequestCorrelationFilter();
		filterConfig = new MockFilterConfig();
		
		filterConfig.addInitParameter(RequestCorrelationFilter.INIT_PARM_CORRELATION_ID_HEADER, "requestId");
		
		filterChain = new MockFilterChain();
		filterChain.setOutputData("Hello".getBytes());
		
		request = new MockRequest();
		response = new MockResponse();
	}

	@Test
	public void testInit() throws Exception {
		filter.init(filterConfig);
		Assert.assertTrue("requestId".equals(filter.getCorrelationHeaderName()));
		
		filterConfig.addInitParameter(RequestCorrelationFilter.INIT_PARM_CORRELATION_ID_HEADER, "requestId   ");
		filter.init(filterConfig);
		Assert.assertTrue("requestId".equals(filter.getCorrelationHeaderName()));
		
		filterConfig.removeInitParameter(RequestCorrelationFilter.INIT_PARM_CORRELATION_ID_HEADER);
		filter.init(filterConfig);
		Assert.assertTrue(RequestCorrelationFilter.DEFAULT_CORRELATION_ID_HEADER_NAME.equals(filter.getCorrelationHeaderName()));
	}
	
	@Test
	public void testDoFilter() throws Exception {
		filter.init(filterConfig);
		filter.doFilter(request, response, filterChain);
		Assert.assertTrue(RequestCorrelationContext.getCurrent().getCorrelationId() != null);
		Assert.assertTrue(response.getResponseContent().contains("Hello"));
		
		request.setHeader("requestId", "foo");
		filter.doFilter(request, response, filterChain);
		Assert.assertTrue("foo".equals(RequestCorrelationContext.getCurrent().getCorrelationId()));
	}

}
