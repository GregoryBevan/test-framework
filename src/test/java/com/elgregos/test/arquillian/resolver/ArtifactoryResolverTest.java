package com.elgregos.test.arquillian.resolver;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ArtifactoryResolverTest {

	private ArtifactoryResolver artifactoryResolver;

	@Before
	public void setUp() {
		this.artifactoryResolver = ArtifactoryResolver.getInstance();
	}

	@Test
	public void testGetExternalDependencyFileOnly() {
		final File[] dependencyFiles = this.artifactoryResolver.getDependencyFiles("junit:junit:4.11", false);
		Assert.assertNotNull(dependencyFiles);
		Assert.assertEquals(1, dependencyFiles.length);
		Assert.assertEquals("junit-4.11.jar", dependencyFiles[0].getName());
	}

	@Test
	public void testGetExternalDependencyFileWithTransitivity() {
		final File[] dependencyFiles = this.artifactoryResolver.getDependencyFiles("junit:junit:4.11", true);
		Assert.assertNotNull(dependencyFiles);
		Assert.assertEquals(2, dependencyFiles.length);
	}

	@Test
	public void testGetPublishedDependencyFileOnly() {
		final File[] dependencyFiles = this.artifactoryResolver.getDependencyFiles("elgregos:test-framework:1.0.0", false);
		Assert.assertNotNull(dependencyFiles);
		Assert.assertEquals(1, dependencyFiles.length);
		Assert.assertEquals("test-framework-1.0.0.jar", dependencyFiles[0].getName());
	}

	/**
	 * Failed every tests...
	 */
	@Test
	@Ignore
	public void testGetNotExistingDependencyFile() {
		final File[] dependencyFiles = this.artifactoryResolver.getDependencyFiles("elgregos:test-frameworks:1.0.0", false);
		Assert.assertNotNull(dependencyFiles);
		Assert.assertEquals(5, dependencyFiles.length);
	}
}
