class Ticket {
    private int ticketId;
    private int trainId;
    private int passengerId;

    public Ticket() {}

    public Ticket(int ticketId, int trainId, int passengerId) {
        this.ticketId = ticketId;
        this.trainId = trainId;
        this.passengerId = passengerId;
    }

    public Ticket(int trainId, int passengerId) {
        this.trainId = trainId;
        this.passengerId = passengerId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }
}

