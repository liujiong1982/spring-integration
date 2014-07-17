/*
 * Copyright 2002-2013 the original author or authors.
 *
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

package org.springframework.integration.config.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Mark Fisher
 * @author Gunnar Hillert
 * @author Gary Russell
 * @author Artem Bilan
 *
 * @since 2.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EnricherParserTests4 {

	@Autowired
	private ApplicationContext context;

	@Test
	public void integrationTest() {
		SubscribableChannel requests = context.getBean("requests", SubscribableChannel.class);

		class Foo extends AbstractReplyProducingMessageHandler {

			@Override
			protected Object handleRequestMessage(Message<?> requestMessage) {
				return null;
			}
		}

		Foo foo = new Foo();
		foo.setOutputChannel(context.getBean("replies", MessageChannel.class));
		requests.subscribe(foo);
		Target original = new Target();
		Message<?> request = MessageBuilder.withPayload(original).setHeader("sourceName", "test").setHeader("notOverwrite", "test").build();
		context.getBean("input", MessageChannel.class).send(request);
		Message<?> reply = context.getBean("output", PollableChannel.class).receive(0);
		Target enriched = (Target) reply.getPayload();
		assertEquals("Could not determine the name", enriched.getName());
		assertEquals(11, enriched.getAge());
		assertTrue(enriched.isMarried());

		MessageHeaders headers = reply.getHeaders();
		assertEquals("Could not determine the foo", headers.get("foo"));
		assertEquals("Could not determine the testBean", headers.get("testBean"));
		assertEquals("Could not determine the sourceName", headers.get("sourceName"));
		assertEquals("Could not determine the notOverwrite", headers.get("notOverwrite"));
	}
	

	private static class Source {

		private final String sourceName;

		Source(String sourceName) {
			this.sourceName = sourceName;
		}

		@SuppressWarnings("unused")
		public String getSourceName() {
			return sourceName;
		}
	}

	public static class Target implements Cloneable {

		private volatile String name;

		private volatile int age;

		private volatile Gender gender;

		private volatile boolean married;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public Gender getGender() {
			return gender;
		}

		public void setGender(Gender gender) {
			this.gender = gender;
		}

		public boolean isMarried() {
			return married;
		}

		public void setMarried(boolean married) {
			this.married = married;
		}

		@Override
		public Object clone() {
			Target copy = new Target();
			copy.setName(this.name);
			copy.setAge(this.age);
			copy.setGender(this.gender);
			copy.setMarried(this.married);
			return copy;
		}
	}

	public static enum Gender {
		MALE, FEMALE
	}

}
