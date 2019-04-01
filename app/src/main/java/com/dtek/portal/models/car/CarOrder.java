package com.dtek.portal.models.car;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarOrder implements Parcelable{

    @SerializedName("Id")
    @Expose
    private int id; // номер заявки

    @SerializedName("Title")
    @Expose
    private String title; // имя заявки

    @SerializedName("Created")
    @Expose
    private long createdTime; // время создания заявки

    @SerializedName("Route")
    @Expose
    private String route; // маршрут (начало - конец)

    @SerializedName("State")
    @Expose
    private String state; // статус заявки

    @SerializedName("OrderCity")
    @Expose
    private String orderCity; // город заказа

    @SerializedName("ContactPhone")
    @Expose
    private String contactPhone; // контактный телефон

    @SerializedName("Direction")
    @Expose
    private String direction; // Направление

    @SerializedName("DateStart")
    @Expose
    private long dateStart; // Дата начала маршрута

    @SerializedName("DateFinish")
    @Expose
    private long dateFinish; // Дата конца маршрута

    @SerializedName("StartPoint")
    @Expose
    private String startPoint; // Начальный адрес

    @SerializedName("StopPoint")
    @Expose
    private String stopPoint; // Конечный адрес

    @SerializedName("UsersComments")
    @Expose
    private String usersComments; // Примечание

    @SerializedName("Me")
    @Expose
    private boolean mePassenger; // для инициатор

    @SerializedName("Other")
    @Expose
    private boolean otherPassenger; // для пассажиров

    @SerializedName("NumPessangers")
    @Expose
    private int numPassengers; // общее колличество пассажиров с/без инициатором

    @SerializedName("Passengers")
    @Expose
    private List<Passenger> mPassengerList; // массив пассажиров без инициатора

    @SerializedName("Points")
    @Expose
    private List<Point> mPointList; // массив доп. пунктов

    @SerializedName("Driver")
    @Expose
    private String driver; // Водитель

    @SerializedName("Car")
    @Expose
    private String car; // Автомобиль

    @SerializedName("Notes")
    @Expose
    private String notes; // Примечание диспетчера

    @SerializedName("Rating")
    @Expose
    private String rating; // Оценка прием

    @SerializedName("RatingComment")
    @Expose
    private String ratingComment; // Комментарий оценки прием

    @SerializedName("Evaluation")
    @Expose
    private String evaluation; // Оценка отправка

    @SerializedName("Comment")
    @Expose
    private String comment; // Комментарий оценки отправка

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderCity() {
        return orderCity;
    }

    public void setOrderCity(String orderCity) {
        this.orderCity = orderCity;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public long getDateStart() {
        return dateStart;
    }

    public void setDateStart(long dateStart) {
        this.dateStart = dateStart;
    }

    public long getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(long dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getStopPoint() {
        return stopPoint;
    }

    public void setStopPoint(String stopPoint) {
        this.stopPoint = stopPoint;
    }

    public String getUsersComments() {
        return usersComments;
    }

    public void setUsersComments(String usersComments) {
        this.usersComments = usersComments;
    }

    public boolean isMePassenger() {
        return mePassenger;
    }

    public void setMePassenger(boolean mePassenger) {
        this.mePassenger = mePassenger;
    }

    public boolean isOtherPassenger() {
        return otherPassenger;
    }

    public void setOtherPassenger(boolean otherPassenger) {
        this.otherPassenger = otherPassenger;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }

    public List<Passenger> getPassengerList() {
        return mPassengerList;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.mPassengerList = passengerList;
    }

    public List<Point> getPointList() {
        return mPointList;
    }

    public void setPointList(List<Point> pointList) {
        this.mPointList = pointList;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRatingComment() {
        return ratingComment;
    }

    public void setRatingComment(String ratingComment) {
        this.ratingComment = ratingComment;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeLong(this.createdTime);
        dest.writeString(this.route);
        dest.writeString(this.state);
        dest.writeString(this.orderCity);
        dest.writeString(this.contactPhone);
        dest.writeString(this.direction);
        dest.writeLong(this.dateStart);
        dest.writeLong(this.dateFinish);
        dest.writeString(this.startPoint);
        dest.writeString(this.stopPoint);
        dest.writeString(this.usersComments);
        dest.writeByte(this.mePassenger ? (byte) 1 : (byte) 0);
        dest.writeByte(this.otherPassenger ? (byte) 1 : (byte) 0);
        dest.writeInt(this.numPassengers);
        dest.writeTypedList(this.mPassengerList);
        dest.writeTypedList(this.mPointList);
        dest.writeString(this.driver);
        dest.writeString(this.car);
        dest.writeString(this.notes);
        dest.writeString(this.rating);
        dest.writeString(this.ratingComment);
        dest.writeString(this.evaluation);
        dest.writeString(this.comment);
    }

    public CarOrder() {
    }

    public CarOrder(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.createdTime = in.readLong();
        this.route = in.readString();
        this.state = in.readString();
        this.orderCity = in.readString();
        this.contactPhone = in.readString();
        this.direction = in.readString();
        this.dateStart = in.readLong();
        this.dateFinish = in.readLong();
        this.startPoint = in.readString();
        this.stopPoint = in.readString();
        this.usersComments = in.readString();
        this.mePassenger = in.readByte() != 0;
        this.otherPassenger = in.readByte() != 0;
        this.numPassengers = in.readInt();
        this.mPassengerList = in.createTypedArrayList(Passenger.CREATOR);
        this.mPointList = in.createTypedArrayList(Point.CREATOR);
        this.driver = in.readString();
        this.car = in.readString();
        this.notes = in.readString();
        this.rating = in.readString();
        this.ratingComment = in.readString();
        this.evaluation = in.readString();
        this.comment = in.readString();
    }

    public static final Creator<CarOrder> CREATOR = new Creator<CarOrder>() {
        @Override
        public CarOrder createFromParcel(Parcel source) {
            return new CarOrder(source);
        }

        @Override
        public CarOrder[] newArray(int size) {
            return new CarOrder[size];
        }
    };

    @Override
    public String toString() {
        return "CarOrder{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdTime=" + createdTime +
                ", route='" + route + '\'' +
                ", state='" + state + '\'' +
                ", orderCity='" + orderCity + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", direction='" + direction + '\'' +
                ", dateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                ", startPoint='" + startPoint + '\'' +
                ", stopPoint='" + stopPoint + '\'' +
                ", usersComments='" + usersComments + '\'' +
                ", mePassenger=" + mePassenger +
                ", otherPassenger=" + otherPassenger +
                ", numPassengers=" + numPassengers +
                ", mPassengerList=" + mPassengerList +
                ", mPointList=" + mPointList +
                ", driver='" + driver + '\'' +
                ", car='" + car + '\'' +
                ", notes='" + notes + '\'' +
                ", rating='" + rating + '\'' +
                ", ratingComment='" + ratingComment + '\'' +
                ", evaluation='" + evaluation + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
