package com.elgregos.test.arquillian;

import java.util.ArrayList;
import java.util.List;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.ejbjar32.EjbJarDescriptor;
import org.jboss.shrinkwrap.descriptor.api.webapp30.WebAppDescriptor;

public abstract class EarDeployment {

	protected EnterpriseArchive enterpriseArchive;

	protected WebArchive webArchive;

	protected List<JavaArchive> earLibraries;

	protected JavaArchive ejbModule;

	protected WebAppDescriptor webAppDescriptor = Descriptors.create(WebAppDescriptor.class);

	protected EjbJarDescriptor ejbJarDescriptor = Descriptors.create(EjbJarDescriptor.class);

	public EarDeployment(final String earName) {
		this.enterpriseArchive = ShrinkWrap.create(EnterpriseArchive.class, earName);
		this.webArchive = ShrinkWrap.create(WebArchive.class, "web.war");
		this.webAppDescriptor.version("3.1");
		this.ejbModule = ShrinkWrap.create(JavaArchive.class, "service.jar");
		this.ejbJarDescriptor.version("3.2");
		this.earLibraries = new ArrayList<>();
		final JavaArchive testClassesJar = ShrinkWrap.create(JavaArchive.class, "test.jar").addClasses(EarDeployment.class,
				this.getClass().getEnclosingClass());
		this.earLibraries.add(testClassesJar);
	}

	public EnterpriseArchive create() {
		this.webArchive.setWebXML(new StringAsset(this.webAppDescriptor.exportAsString()));
		return this.enterpriseArchive.addAsModule(this.webArchive).addAsModule(this.ejbModule).addAsLibraries(this.earLibraries);
	}

}
