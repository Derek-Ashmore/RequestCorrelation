RequestCorrelation
==================

This project is a Java library that facilitates establishing and tracking correlation ids for micro-services.

The objectives are as follows:

* Ensure that a correlation id is assigned for each transaction (will be generated if not on service request).
* Ensure that the correlation id is documented on all log messages so that it can be correlated across services.
* Insulate micro-service code from having to be concerned with correlation ids at all.

This product has two components:

* Servlet filter to maintain the correlation id
* Extensions (Log4J and Logback) to ensure correlation id publishing in the log

System Requirements
-------------------

* Java JDK 6.0 or above (it was compiled under JDK 7 using 1.6 as the target source).
* Apache Commons Lang version 3.0 or above  
* Either Log4J V1.x or Logback logging configuration  

Installation Instructions  
-------------------------
RequestCorrelation is easy to install whether you use maven or not.  There are two artifacts: one jar for RequestCorrelation-core and another to support either log4J v1.x or Logback logging configurations.

### Maven Users  
Maven users must list two dependencies:  
* RequestCorrelation-core -- [dependency information](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22RequestCorrelation-core%22).  
* RequestCorrelation [log4j](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22RequestCorrelation-log4j-1x%22) or [logback](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22RequestCorrelation-logback%22)  

### Non-Maven Users  
Include the following jars in your class path:  
* Download the RequestCorrelation-core jar and either the jog4j or logback companion jars from [Github](https://github.com/Derek-Ashmore/RequestCorrelation/releases) and put them in your class path.  
* Insure Apache Commons Lang version 3.0 or above is in your class path.  


Filter configuration
---------------------------
The correlation id must be established or set at service entry.  For RESTful or SOAP service requests, this is accomplished by servlet filter.  

A sample web.xml filter configuration is as follows:
```

	<filter>
		<filter-name>correlationIdFilter</filter-name>
		<filter-class>org.force66.correlate.RequestCorrelationFilter</filter-class>
		<init-param>  <!-- Optional: 'requestCorrelationId' is the default -->
			<param-name>correlation.id.header</param-name>
			<param-value>CorrelationId</param-value>
		</init-param>
	</filter>

	<filter-mapping>
		<filter-name>correlationIdFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
```

For work that is initiated either by a message receiver or batch job, ensure that a line of code like the following gets executed in the thread performing the work (the correlation id is stored using a ThreadLocal):  
```  

RequestCorrelationContext.getCurrent().setCorrelationId(correlationId);  
	
```

Log4J configuration
--------------------------

If you specify the layout class as org.force66.correlate.log4j.CorrelationPatternLayout, then you
can use the pattern marker %I to insert the correlation id on the log message
```

log4j.rootLogger=INFO, stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.force66.correlate.log4j.CorrelationPatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %I %-5p %c{1}:%L - %m%n

```


Logback configuration
---------------------
If you specify the custom encoder org.force66.correlate.logback.CorrelationPatternLayoutEncoder,
then you can use the %id or %correlationId markers to insert the correlation id in the log message.

```XML


	<!-- Send debug messages to System.out -->
	<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
		<encoder class="org.force66.correlate.logback.CorrelationPatternLayoutEncoder">
			<pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{5} %id - %msg%n</pattern>
		</encoder>
	</appender>


```

