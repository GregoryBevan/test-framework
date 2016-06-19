package com.elgregos.test.arquillian;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.protocol.servlet.arq514hack.descriptors.api.web.WebAppDescriptor;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;

import com.elgregos.test.arquillian.resolver.ArtifactoryResolver;

public abstract class EarDeployment {

	protected EnterpriseArchive enterpriseArchive;

	protected WebArchive webArchive;

	protected List<JavaArchive> earLibraries;

	protected List<String> transitiveDependencies;

	protected JavaArchive ejbModule;

	protected WebAppDescriptor webAppDescriptor = Descriptors.create(WebAppDescriptor.class);


	public EarDeployment(final String earName) {
		this.enterpriseArchive = ShrinkWrap.create(EnterpriseArchive.class, earName);
		this.webArchive = ShrinkWrap.create(WebArchive.class, "web.war");
		this.webAppDescriptor.version("3.1");
		this.ejbModule = ShrinkWrap.create(JavaArchive.class, "service.jar");
		this.ejbModule.addClass(DummyForEJBDeployment.class);
		this.earLibraries = new ArrayList<>();
		final JavaArchive testClassesJar = ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addClasses(EarDeployment.class, this.getClass().getEnclosingClass()).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		this.earLibraries.add(testClassesJar);
	}

	public EarDeployment addGradleDependency(final String dependency, final boolean transitive) {
		final File[] dependencies = ArtifactoryResolver.getInstance().getDependencyFiles(dependency, transitive);
		for (final File dependencyFile : dependencies) {
			this.earLibraries.add(ShrinkWrap.createFromZipFile(JavaArchive.class, dependencyFile));
		}
		return this;
	}

	public EnterpriseArchive create() {
		this.webArchive.setWebXML(new StringAsset(this.webAppDescriptor.exportAsString()));
		return this.enterpriseArchive.addAsModule(this.webArchive).addAsModule(this.ejbModule).addAsLibraries(this.earLibraries);
	}

}
