/*
 * Copyright 2002-2014 the original author or authors.
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

package org.springframework.integration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a method is capable of handling a message or message payload.
 * <p>
 * A method annotated with @ServiceActivator may accept a parameter of type
 * {@link org.springframework.messaging.Message} or of the expected
 * Message payload's type. Any type conversion supported by
 * {@link org.springframework.beans.SimpleTypeConverter} will be applied to
 * the Message payload if necessary. Header values can also be passed as
 * Message parameters by using the
 * {@link org.springframework.messaging.handler.annotation.Header @Header} parameter annotation.
 * <p>
 * Return values from the annotated method may be of any type. If the return
 * value is not a Message, a reply Message will be created with that object
 * as its payload.
 *
 * @author Mark Fisher
 * @author Gary Russell
 * @author Artem Bilan
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ServiceActivator {

	String inputChannel() default "";

	String outputChannel() default "";

	String requiresReply() default "";

	String[] adviceChain() default {};

	/*
	 {@code SmartLifecycle} options.
	 Can be specified as 'property placeholder', e.g. {@code ${foo.autoStartup}}.
	 */
	String autoStartup() default "true";

	String phase() default "0";

	/**
	 * @return the {@link Poller} options for a polled endpoint
	 * ({@link org.springframework.integration.scheduling.PollerMetadata}).
	 * This attribute is an {@code array} just to allow an empty default (no poller).
	 * Only one {@link Poller} element is allowed.
	 */
	Poller[] poller() default {};
}
