package ppj.lab4;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 17/01/2021
 */
public class CodeBuilder {
    private final StringBuilder builder;

    public CodeBuilder() {
        builder = new StringBuilder();
    }

    /**
     * Dodaje naredbu.
     * Tab se automatski dodaje, ne ga dodavati u komandu.
     * NewLine se takoder automatski dodaje. Ne ga dodavati u komandu.
     *
     * @param command FRISC commanda.
     */
    public void addCommand(String command) {
        builder.append("\t").append(command).append("\n");
    }

    /**
     * Dodaje naredbu s labelom.
     *
     * @param label   labela naredbe.
     * @param command FRISC commanda.
     */
    public void addCommandWithLabel(String label, String command) {
        builder.append(label);
        addCommand(command);
    }

    public void addEmptyLine() {
        builder.append("\n");
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
