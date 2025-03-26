class Train {
    private int trainId;
    private String name;
    private String source;
    private String destination;
    private int availableTickets;

    public Train() {}

    public Train(int trainId, String name, String source, String destination, int availableTickets) {
        this.trainId = trainId;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.availableTickets = availableTickets;
    }

    public Train(String name, String source, String destination, int availableTickets) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.availableTickets = availableTickets;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
}
