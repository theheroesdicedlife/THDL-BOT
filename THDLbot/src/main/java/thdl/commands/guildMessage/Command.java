package thdl.commands.guildMessage;


import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


public interface Command
{

	/**
	 * Test, if the command can be used without the occurance of errors
	 * 
	 * @param args
	 *            The parameters the command gets with its call
	 * @param e
	 *            The event, which is causing the usage of the command
	 * @return
	 * 		Is the test ok or not
	 */
	boolean called(String args[], GuildMessageReceivedEvent e);

	/**
	 * Using of the command. There should be no tests, which can cause the cancel of
	 * the process.
	 * After the usage, the command should be successful.
	 * 
	 * @param args
	 *            The parameters the command gets with its call
	 * @param e
	 *            The event, which is causing the usage of the command
	 * @throws Exception
	 *             Possible Exceptions thrown by Methods while using.
	 */
	void action(String args[], GuildMessageReceivedEvent e) throws Exception;

	/**
	 * Writes a message in the log, that says that the command was a success or not
	 * Also it is reseting all the object-variables, for the next usage of the
	 * implementing Command-Object
	 * 
	 * @param success
	 *            Was the usage successful or not
	 * @param event
	 *            The event, which is causing the usage of the command
	 */
	void executed(boolean success, GuildMessageReceivedEvent event);

	/**
	 * Returns a help-Text for the implementing Command-Object.
	 * 
	 * @return
	 */
	String help();
}
