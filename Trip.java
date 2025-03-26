class Trip {
    private int tripId;
    private String tripDate;
    private int trainId;

    public Trip() {}

    public Trip(int tripId, String tripDate, int trainId) {
        this.tripId = tripId;
        this.tripDate = tripDate;
        this.trainId = trainId;
    }

    public Trip(String tripDate, int trainId) {
        this.tripDate = tripDate;
        this.trainId = trainId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }
}
