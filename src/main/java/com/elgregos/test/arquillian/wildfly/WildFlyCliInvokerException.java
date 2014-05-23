/**
 *
 */
package com.elgregos.test.arquillian.wildfly;

/**
 * Exception that occurres while invoking Wilfly CLI
 *
 * @author Grégory
 *
 */
public class WildFlyCliInvokerException extends Exception {

	private static final long serialVersionUID = 1L;

	public WildFlyCliInvokerException(final String message, final Exception cause) {
		super(message, cause);
	}

}
