package model;


import java.util.Objects;

public class ChainOfCustody {

    private String id;
    private String trackingId;
    private double  weightOfParcel;
    private String sortingCenterDestination;
    private String distributionOfficeCode;
    private String distributionZone;
    private String deliveryMan;
    private String codeOwner;
    private String documentId;
    private String text;
    private String status;
    private Event event;

    public ChainOfCustody() {
    }

    public ChainOfCustody(String status, String id, String trackingId, double  weightOfParcel, String sortingCenterDestination, String distributionOfficeCode, String distributionZone, String deliveryMan, String codeOwner, String documentId, String text, Event event) {

        this.id = id;
        this.trackingId = trackingId;
        this.weightOfParcel = weightOfParcel;
        this.sortingCenterDestination = sortingCenterDestination;
        this.distributionOfficeCode = distributionOfficeCode;
        this.distributionZone = distributionZone;
        this.deliveryMan = deliveryMan;
        this.codeOwner = codeOwner;
        this.documentId = documentId;
        this.text = text;
        this.status = status;
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public double  getWeightOfParcel() {
        return weightOfParcel;
    }

    public void setWeightOfParcel(double  weightOfParcel) {
        this.weightOfParcel = weightOfParcel;
    }

    public String getSortingCenterDestination() {
        return sortingCenterDestination;
    }

    public void setSortingCenterDestination(String sortingCenterDestination) {
        this.sortingCenterDestination = sortingCenterDestination;
    }

    public String getDistributionOfficeCode() {
        return distributionOfficeCode;
    }

    public void setDistributionOfficeCode(String distributionOfficeCode) {
        this.distributionOfficeCode = distributionOfficeCode;
    }

    public String getDistributionZone() {
        return distributionZone;
    }

    public void setDistributionZone(String distributionZone) {
        this.distributionZone = distributionZone;
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public String getCodeOwner() {
        return codeOwner;
    }

    public void setCodeOwner(String codeOwner) {
        this.codeOwner = codeOwner;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChainOfCustody that = (ChainOfCustody) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(trackingId, that.trackingId) &&
                Objects.equals(weightOfParcel, that.weightOfParcel) &&
                Objects.equals(sortingCenterDestination, that.sortingCenterDestination) &&
                Objects.equals(distributionOfficeCode, that.distributionOfficeCode) &&
                Objects.equals(distributionZone, that.distributionZone) &&
                Objects.equals(deliveryMan, that.deliveryMan) &&
                Objects.equals(codeOwner, that.codeOwner) &&
                Objects.equals(documentId, that.documentId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, trackingId, weightOfParcel, sortingCenterDestination, distributionOfficeCode, distributionZone, deliveryMan, codeOwner, documentId);
    }
}
