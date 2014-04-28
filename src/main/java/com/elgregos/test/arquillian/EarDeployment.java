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

	public EarDeployment(String earName) {		
		enterpriseArchive = ShrinkWrap.create(EnterpriseArchive.class, earName);
		webArchive = ShrinkWrap.create(WebArchive.class, "web.war");
		webArchive.addClasses(EarDeployment.class,this.getClass().getEnclosingClass());
		webAppDescriptor.version("3.1");
		ejbModule = ShrinkWrap.create(JavaArchive.class, "service.jar");
		ejbJarDescriptor.version("3.2");
		earLibraries = new ArrayList<>();
	}

	public EnterpriseArchive create() {
		webArchive.setWebXML(new StringAsset(webAppDescriptor.exportAsString()));
		return enterpriseArchive.addAsModule(webArchive).addAsModule(ejbModule).addAsLibraries(earLibraries);
	}

}
