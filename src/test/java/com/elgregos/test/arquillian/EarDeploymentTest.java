package com.elgregos.test.arquillian;

import static org.junit.Assert.assertNotNull;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class EarDeploymentTest {

	@Test
	public void testCreate() {
		EnterpriseArchive enterpriseArchive = new EarDeployment("test.ear", EarDeploymentTest.class) {
			{
				earLibraries.add(ShrinkWrap.create(JavaArchive.class, "lib1.jar"));

			}
		}.create();
		assertNotNull(enterpriseArchive);
	}

}
