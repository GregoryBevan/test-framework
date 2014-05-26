package com.elgregos.test.arquillian.wildfly;

import java.io.File;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.scriptsupport.CLI;

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
		final CLI cli = CLI.newInstance();
		cli.connect();
		final CommandContext commandContext = cli.getCommandContext();
		try (final Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				final String command = scanner.nextLine();
				commandContext.handle(command);
			}
		} catch (final Throwable th) {
			log.error("An error occurred while processing CLI script", th);
		}
	}
}
