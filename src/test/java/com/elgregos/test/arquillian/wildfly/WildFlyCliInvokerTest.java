package com.elgregos.test.arquillian.wildfly;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test wilfly cli invoke. Needs wildfly to be started
 *
 * @author Grégory
 */
public class WildFlyCliInvokerTest {

	@Test
	public void testNewInstance() {
		final WildFlyCliInvoker wildFlyCliInvoker = WildFlyCliInvoker.newInstance();
		Assert.assertNotNull(wildFlyCliInvoker);
	}

	@Test
	public void testProcessCliScript() {
		WildFlyCliInvoker.newInstance().processCliScript(new File(this.getClass().getClassLoader().getResource("script.cli").getFile()));
	}

}
