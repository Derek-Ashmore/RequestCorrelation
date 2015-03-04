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

/**
 * Tracks the correlation Id for the current request.
 * @author D. Ashmore
 *
 */
public class RequestCorrelationContext {
	
	private static final ThreadLocal<RequestCorrelationContext> CONTEXT = 
			new ThreadLocal<RequestCorrelationContext>();
	
	private String correlationId;
	
	protected RequestCorrelationContext()  {}
	
	public static RequestCorrelationContext getCurrent() {
		RequestCorrelationContext context = CONTEXT.get();
		if (context == null) {
			context = new RequestCorrelationContext();
			CONTEXT.set(context);
		}
		
		return context;
	}
	
	public static void clearCurrent() {
		CONTEXT.remove();
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

}
