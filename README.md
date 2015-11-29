File Unzip Module
=============================

This is an example of a custom source module using the Spring Integration Zip Transformer to unzip files. This is built and packaged for installation in a Spring XD runtime environment using maven. The project includes sample unit and integration tests, including the ability to test the module in an embedded single node container. It also illustrates how to define module options using simple property descriptors.

## Requirements

In order to install the module and run it in your Spring XD installation, you will need to have installed:

* Spring XD version 1.1.x ([Instructions](http://docs.spring.io/spring-xd/docs/current/reference/html/#getting-started))

## Code Tour

This implements a processor module which unzips files from a file system location using an existing Spring Integration transformer. The example demonstrates the use of the `spring-xd-module-parent` pom and an integration test to test the module registered and deployed in an embedded Spring XD single node container.

## Building with Maven

	$ mvn package

The project's [pom][] declares `spring-xd-module-parent` as its parent. This adds the dependencies needed to test the module and also configures the [Spring Boot Maven Plugin][] to package the module as an uber-jar, packaging any dependencies that are not already provided by the Spring XD container. See the [Modules][] section in the Spring XD Reference for a more detailed explanation of module class loading.

## Building with Maven

	$mvn package

The project's [build.gradle][] applies the `spring-xd-module` plugin, providing analagous build and packaging support for gradle. This plugin also applies the [Spring Boot Gradle Plugin][] as well as the [propdeps plugin][]. 

## Using the Custom Module

The uber-jar will be in `[project-build-dir]/zip-transformer-1.0.0.BUILD-SNAPSHOT.jar`. To install and register the module to your Spring XD distribution, use the `module upload` Spring XD shell command. Start Spring XD and the shell:


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
	xd:>module upload --file [path to]/zip-transformer-1.0.0.BUILD-SNAPSHOT.jar --name unzipper --type processor
	Successfully uploaded module 'processor:unzipper'
	xd:>


You can also get information about the available module options:

	xd:>module info processor:unzipper

	Information about source module 'unzipper':

  	Option Name         Description                                                Default                                   Type
  	------------------  ---------------------------------------------------------  -------  ---------
  	outputType   how this module should emit messages it produces       <none>   org.springframework.util.MimeType
  	inputType    how this module should interpret messages it consumes  <none>   org.springframework.util.MimeType


Now create and deploy a stream (it requires a file source):

	xd:>stream create unzipTest --definition "file --dir=/var/log/supervisor/ --pattern=compressed.in-*.zip   | unzipper | log" --deploy


You should see the stream output in the Spring XD log 



