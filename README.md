AWS S3 Feed Source Module
=============================

This is an example of a custom source module using the Spring Integration feed inbound channel adapter to download files from a S3 bucket. This is built and packaged for installation in a Spring XD runtime environment using maven. The project includes sample unit and integration tests, including the ability to test the module in an embedded single node container. It also illustrates how to define module options using simple property descriptors.

## Requirements

In order to install the module and run it in your Spring XD installation, you will need to have installed:

* Spring XD version 1.1.x ([Instructions](http://docs.spring.io/spring-xd/docs/current/reference/html/#getting-started))

## Code Tour

This implements a source module which files from a s3 bucket from given AWS API credentials using an existing Spring Integration inbound channel adapter. The example demonstrates the use of the `spring-xd-module-parent` pom and an integration test to test the module registered and deployed in an embedded Spring XD single node container.

## Building with Maven

	$ mvn package

The project's [pom][] declares `spring-xd-module-parent` as its parent. This adds the dependencies needed to test the module and also configures the [Spring Boot Maven Plugin][] to package the module as an uber-jar, packaging any dependencies that are not already provided by the Spring XD container. See the [Modules][] section in the Spring XD Reference for a more detailed explanation of module class loading.

## Building with Gradle

	$./gradlew clean test bootRepackage

The project's [build.gradle][] applies the `spring-xd-module` plugin, providing analagous build and packaging support for gradle. This plugin also applies the [Spring Boot Gradle Plugin][] as well as the [propdeps plugin][]. 

## Using the Custom Module

The uber-jar will be in `[project-build-dir]/s3-source-1.0.0.BUILD-SNAPSHOT.jar`. To install and register the module to your Spring XD distribution, use the `module upload` Spring XD shell command. Start Spring XD and the shell:


	_____                           __   _______
	/  ___|          (-)             \ \ / /  _  \
	\ `--. _ __  _ __ _ _ __   __ _   \ V /| | | |
 	`--. \ '_ \| '__| | '_ \ / _` |   / ^ \| | | |
	/\__/ / |_) | |  | | | | | (_| | / / \ \ |/ /
	\____/| .__/|_|  |_|_| |_|\__, | \/   \/___/
    	  | |                  __/ |
      	|_|                 |___/
	eXtreme Data
	1.1.0.BUILD-SNAPSHOT | Admin Server Target: http://localhost:9393
	Welcome to the Spring XD shell. For assistance hit TAB or type "help".
	xd:>module upload --file [path-to]/s3-source-1.0.0.BUILD-SNAPSHOT.jar --name s3Source --type source
	Successfully uploaded module 'source:s3Source'
	xd:>


You can also get information about the available module options:

	xd:>module info source:s3Source

	Information about source module 's3Source':

  	Option Name         Description                                                Default                                   Type
  	------------------  ---------------------------------------------------------  -------  ---------
    	fixedRate           the fixed rate polling interval specified in milliseconds  5000                                      int
    	accessKey           the accessKey for AWS                                      RANDOMSTRING                      java.lang.String
    	bucket              the bucket for S3                                          testboom                                  java.lang.String
    	remoteDirectory     the remoteDirectory for S3                                 /                                         java.lang.String
    	maxMessagesPerPoll  the maximum number of messages per poll                    100                                       int
    	secretKey           the secretKey for AWS                                      RANDOMString  java.lang.String
    	localDirectory      the localDirectory                                         /tmp/nec                                  java.lang.String
    	outputType          how this module should emit messages it produces           <none>                                    org.springframework.util.MimeType

Now create and deploy a stream:

	xd:>stream create S3Test --definition "s3Source --accessKey=[accessKey] --secretKey=[secretKey] --bucket=[bucket] --remoteDirectory=[remoteDirectory] --localDirectory=[localDirectory] | log" --deploy


You should see the stream output in the Spring XD log 



