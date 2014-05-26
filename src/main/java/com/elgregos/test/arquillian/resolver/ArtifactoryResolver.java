package com.elgregos.test.arquillian.resolver;

import java.io.File;

import org.jboss.shrinkwrap.resolver.api.maven.ConfigurableMavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenFormatStage;
import org.jboss.shrinkwrap.resolver.api.maven.MavenStrategyStage;

public class ArtifactoryResolver {

	private static final ArtifactoryResolver INSTANCE = new ArtifactoryResolver();

	private static final String REPOSITORY_NAME = "Artifactory";
	private static final String REPOSITORY_LAYOUT = "default";
	private static final String REPOSITORY_URL = "http://elgregos.com/artifactory/elgregos";

	private final ConfigurableMavenResolverSystem artifactoryResolver;

	private ArtifactoryResolver() {
		this.artifactoryResolver = Maven.configureResolver().withRemoteRepo(REPOSITORY_NAME, REPOSITORY_URL, REPOSITORY_LAYOUT);

	}

	/**
	 * Returns Artifactory resolver unique instance
	 *
	 * @return Artifactory Resolver
	 */
	public static ArtifactoryResolver getInstance() {
		return INSTANCE;
	}

	/**
	 * Returns array of dependency files
	 *
	 * @param dependency Dependency with pattern 'group:artifact:version'
	 * @param transitive Boolean for transitive resolution or not
	 * @return Dependency files
	 */
	public File[] getDependencyFiles(final String dependency, final boolean transitive) {
		final MavenStrategyStage resolve = this.artifactoryResolver.resolve(dependency);
		final MavenFormatStage mavenFormatStage = transitive ? resolve.withTransitivity() : resolve.withoutTransitivity();
		return mavenFormatStage.asFile();
	}

}
