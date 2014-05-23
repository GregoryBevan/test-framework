package com.elgregos.test.arquillian.wildfly;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.cli.scriptsupport.CLI;

/**
 * Invoke wildly command line interface
 *
 * @author Grégory
 */
public class WildFlyCliInvoker {

	private CommandContext commandContext;

	private WildFlyCliInvoker() {
		final CLI cli = CLI.newInstance();
		cli.connect();
		this.commandContext = cli.getCommandContext();
	}

	/**
	 * Returns new instance of wildfly CLI invoker
	 *
	 * @return CLI invoker
	 */
	public static WildFlyCliInvoker newInstance() {
		return new WildFlyCliInvoker();
	}

	public void processCliScript(final File script) throws WildFlyCliInvokerException {
		String command = "";
		try (final Scanner scanner = new Scanner(script)) {
			while (scanner.hasNextLine()) {
				command = scanner.nextLine();
				this.commandContext.handle(command);
			}
		} catch (final FileNotFoundException fnfe) {
			throw new WildFlyCliInvokerException("Cli script has not been found. Check the file path", fnfe);
		} catch (final CommandLineException cle) {
			throw new WildFlyCliInvokerException("An error occurred while executing command : " + command, cle);
		}
	}
}
