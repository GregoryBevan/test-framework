package com.elgregos.test.arquillian.wildfly;

import java.io.File;
import java.net.InetAddress;
import java.util.Scanner;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import lombok.extern.slf4j.Slf4j;

/**
 * Invoke wildly command line interface
 *
 * @author Grégory
 */
@Slf4j
public class WildFlyCliInvoker {

	private WildFlyCliInvoker() {
	}

	/**
	 * Returns new instance of wildfly CLI invoker
	 *
	 * @return CLI invoker
	 */
	public static WildFlyCliInvoker newInstance() {
		return new WildFlyCliInvoker();
	}

	public void processCliScript(final File file) {
		try (final Scanner scanner = new Scanner(file)) {
			ModelControllerClient client = ModelControllerClient.Factory.create(InetAddress.getByName("localhost"), 9999);
			while (scanner.hasNextLine()) {
				final String command = scanner.nextLine();
				ModelNode op = new ModelNode(command);
				client.execute(op);
			}
		} catch (final Throwable th) {
			log.error("An error occurred while processing CLI script", th);
		}
	}
}
