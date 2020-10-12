package ppj.lab;

class RuleName {
    String state;
    String regex;

    RuleName(String state, String regex) {
        this.regex = regex;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleName ruleName = (RuleName) o;

        if (! state.equals(ruleName.state)) return false;
        return regex.equals(ruleName.regex);
    }

    @Override
    public int hashCode() {
        int result = state.hashCode();
        result = 31 * result + regex.hashCode();
        return result;
    }
}
