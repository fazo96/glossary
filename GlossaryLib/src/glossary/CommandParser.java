package glossary;

/**
 * An instance of CommandParser is able to parse Glossary commands (in form of a
 * string) and provides callbacks for what to do when they are executed.
 *
 * @author fazo
 */
public abstract class CommandParser {

    /**
     * Execute a command if it is valid.
     *
     * @param command the command to execute.
     */
    public void parse(String command) {
        String[] c = command.split(":", 1);
        if (c.length == 2) {
            if (c[0].equals("DELETE")) {
                // Delete a term
                onDelete(c[1].trim().toLowerCase());
            } else {
                // Upsert a term and its meaning
                onUpsert(c[0].trim().toLowerCase(), c[1].trim());
            }
            onValidCommand(command);
        }
    }

    /**
     * This function is called after other event functions when a valid command
     * is received from a client
     *
     * @param command the command issued that was valid
     */
    public abstract void onValidCommand(String command);

    /**
     * This function is called when the CommandParser was asked to parse a
     * DELETE command with the given term
     *
     * @param term the term that has to be deleted
     */
    public abstract void onDelete(String term);

    /**
     * This function is called when the CommandParser was asked to parse an
     * UPSERT command with the given term
     *
     * @param term the term that has to be "upserted"
     * @param meaning the meaning of the term to be "upserted"
     */
    public abstract void onUpsert(String term, String meaning);
}
