/*
 * Copyright 2015 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package my.custom.transformer;

import static org.springframework.xd.dirt.test.process.SingleNodeProcessingChainSupport.chain;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.xd.dirt.server.singlenode.SingleNodeApplication;
import org.springframework.xd.dirt.test.SingleNodeIntegrationTestSupport;
import org.springframework.xd.dirt.test.SingletonModuleRegistry;
import org.springframework.xd.dirt.test.process.SingleNodeProcessingChain;
import org.springframework.xd.module.ModuleType;
import org.springframework.xd.test.RandomConfigurationSupport;

public class SourceModuleIntegrationTest {
	private static SingleNodeApplication application;

	private static int RECEIVE_TIMEOUT = 6000;

	/**
	 * Start the single node container, binding random unused ports, etc. to not conflict with any other instances
	 * running on this host. Configure the ModuleRegistry to include the project module.
	 */
	@BeforeClass
	public static void setUp() {
		RandomConfigurationSupport randomConfigSupport = new RandomConfigurationSupport();
		application = new SingleNodeApplication().run();
		SingleNodeIntegrationTestSupport singleNodeIntegrationTestSupport = new SingleNodeIntegrationTestSupport(application);
		singleNodeIntegrationTestSupport.addModuleRegistry(new SingletonModuleRegistry(ModuleType.processor, "unZip"));
	}
	
	@Test
	public void test() throws IOException {
		
		SingleNodeProcessingChain chain = chain(application, "unZipStream", String.format("unZip") );

		final Resource resource = application.adminContext().getResource("classpath:testzipdata/single.zip");
		final InputStream is = resource.getInputStream();

		byte[] zipdata = IOUtils.toByteArray(is);

		//final Message<byte[]> message = MessageBuilder.withPayload(zipdata).build();
		
		chain.sendPayload(zipdata);
		
		Object payload = chain.receivePayload(RECEIVE_TIMEOUT);
		
		System.out.println("payload: "+payload);
		
		//assertTrue(payload instanceof String);

		chain.destroy();
	}

}
