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
 * Indicates that a method is capable of resolving to a channel or channel name
 * based on a message, message header(s), or both.
 * <p>
 * A method annotated with @Router may accept a parameter of type
 * {@link org.springframework.messaging.Message} or of the expected
 * Message payload's type. Any type conversion supported by
 * {@link org.springframework.beans.SimpleTypeConverter} will be applied to
 * the Message payload if necessary. Header values can also be passed as
 * Message parameters by using the
 * {@link org.springframework.messaging.handler.annotation.Header @Header} parameter annotation.
 * <p>
 * Return values from the annotated method may be either a Collection or Array
 * whose elements are either
 * {@link org.springframework.messaging.MessageChannel channels} or
 * Strings. In the latter case, the endpoint hosting this router will attempt
 * to resolve each channel name with the Channel Registry or with
 * {@link #channelMappings()}, if provided.
 *
 * @author Mark Fisher
 * @author Artem Bilan
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Router {

	String inputChannel() default "";

	String defaultOutputChannel() default "";

	/**
	 * The 'key=value' pairs to represent channelMapping entries
	 * @return the channelMappings
	 * @see org.springframework.integration.router.AbstractMappingMessageRouter#setChannelMapping(String, String)
	 */
	String[] channelMappings() default {};

	String prefix() default "";

	String suffix() default "";

	String resolutionRequired() default "";

	String applySequence() default "";

	String ignoreSendFailures() default "";

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
