package com.elgregos.test.arquillian;

import java.util.ArrayList;
import java.util.List;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.webapp30.WebAppDescriptor;

public abstract class EarDeployment {

	protected final EnterpriseArchive enterpriseArchive;

	protected final WebArchive webArchive;

	protected List<JavaArchive> javaArchives;

	private WebAppDescriptor webAppDescriptor = Descriptors.create(WebAppDescriptor.class);

	public EarDeployment(String earName) {
		enterpriseArchive = ShrinkWrap.create(EnterpriseArchive.class, earName);
		webArchive = ShrinkWrap.create(WebArchive.class);
		javaArchives = new ArrayList<>();
		webAppDescriptor.version("3.1");
	}

	public EnterpriseArchive getInstance() {
		enterpriseArchive.addAsModule(webArchive);
		enterpriseArchive.addAsLibraries(javaArchives);		
		return enterpriseArchive;
	}

}
