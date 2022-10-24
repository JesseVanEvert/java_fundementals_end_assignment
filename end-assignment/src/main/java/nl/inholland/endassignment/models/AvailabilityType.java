package nl.inholland.endassignment.models;

public enum AvailabilityType {
    YES("Yes"),
    NO("No");

    public final String label;

    private AvailabilityType(String label) {
        this.label = label;
    }
}
