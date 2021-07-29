package torimia.client.crosswordclient.version1.dto;

/**
 * If you want to add new region, you need also add a new dictionary(looking for the instruction in README)
 */
public enum Region {
    RU("ru"), EN("en");

    private final String value;

    Region(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
