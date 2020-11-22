public enum InitialData {
    IN_ORDER("In Order"),
    REVERSE("Reverse Order"),
    RANDOM("Randomize"),
    ONE_OUT_OF_ORDER("One Out of Order"),
    PREVIOUS("Previous Data");

    String title;

    InitialData(String title) {
        this.title = title;
    }
}
