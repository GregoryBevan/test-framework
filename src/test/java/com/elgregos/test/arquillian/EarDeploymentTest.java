package com.elgregos.test.arquillian;

import static org.junit.Assert.assertNotNull;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class EarDeploymentTest {

	@Test
	public void testGetInstance() {
		EnterpriseArchive enterpriseArchive = new EarDeployment("test.ear") {
			{
				javaArchives.add(ShrinkWrap.create(JavaArchive.class, "lib1.jar"));

			}
		}.getInstance();
		assertNotNull(enterpriseArchive);
	}

}
