package liquibase.command.core;

import liquibase.command.*;
import liquibase.integration.commandline.Main;

public class DiffChangeLogCommandStep extends AbstractCliWrapperCommandStep {
    public static final CommandArgumentDefinition<String> REFERENCE_USERNAME_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_PASSWORD_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_URL_ARG;
    public static final CommandArgumentDefinition<String> USERNAME_ARG;
    public static final CommandArgumentDefinition<String> PASSWORD_ARG;
    public static final CommandArgumentDefinition<String> URL_ARG;
    public static final CommandArgumentDefinition<String> CHANGELOG_FILE_ARG;

    static {
        CommandStepBuilder builder = new CommandStepBuilder(DiffChangeLogCommandStep.class);
        REFERENCE_URL_ARG = builder.argument("referenceUrl", String.class).required()
            .description("The JDBC reference database connection URL").build();
        REFERENCE_USERNAME_ARG = builder.argument("referenceUsername", String.class)
            .description("The reference database username").build();
        REFERENCE_PASSWORD_ARG = builder.argument("referencePassword", String.class)
            .description("The reference database password").build();
        URL_ARG = builder.argument("url", String.class).required()
            .description("The JDBC target database connection URL").build();
        USERNAME_ARG = builder.argument("username", String.class)
            .description("The target database username").build();
        PASSWORD_ARG = builder.argument("username", String.class)
            .description("The target database password").build();
        CHANGELOG_FILE_ARG = builder.argument("changeLogFile", String.class).required()
            .description("Changelog file to write results").build();
    }

    @Override
    public String[] getName() {
        return new String[] {"diffChangeLog"};
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();

        String[] args = createArgs(commandScope);
        int statusCode = Main.run(args);
        addStatusMessage(resultsBuilder, statusCode);
        resultsBuilder.addResult("statusCode", statusCode);
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Compare two databases to produce changesets and write them to a changelog file");
        commandDefinition.setLongDescription("Compare two databases to produce changesets and write them to a changelog file");
    }
}
