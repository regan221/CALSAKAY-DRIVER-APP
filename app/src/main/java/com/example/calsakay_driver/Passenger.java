package com.example.calsakay_driver;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Passenger implements Serializable, Parcelable {
    private int passengerId;
    private String rideTraceId;
    private String firstname;
    private String lastname;
    private Date birthday;
    private String gender;
    private String job;
    private String mobileNumber;
    private String companyName;
    private String companyAddress;
    private String companyNumber;
    private String email;
    private Bitmap image;
    private Date joined;
    private String dropoffLocation;
    private int dropOffLocationId;
    private String pickupLocation;
    private int pickupLocationId;
    private Date time;

    protected Passenger(Parcel in) {
        passengerId = in.readInt();
        rideTraceId = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        gender = in.readString();
        job = in.readString();
        mobileNumber = in.readString();
        companyName = in.readString();
        companyAddress = in.readString();
        companyNumber = in.readString();
        email = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
        dropoffLocation = in.readString();
        dropOffLocationId = in.readInt();
        pickupLocation = in.readString();
        pickupLocationId = in.readInt();
    }

    public static final Creator<Passenger> CREATOR = new Creator<Passenger>() {
        @Override
        public Passenger createFromParcel(Parcel in) {
            return new Passenger(in);
        }

        @Override
        public Passenger[] newArray(int size) {
            return new Passenger[size];
        }
    };

    public String getRideTraceId() {
        return rideTraceId;
    }

    public Passenger(int passengerId, String rideTraceId, String firstname, String lastname, Date birthday, String gender, String job, String mobileNumber, String companyName, String companyAddress, String companyNumber, String email, Bitmap image, Date joined, String dropoffLocation, int dropOffLocationId, String pickupLocation, int pickupLocationId, Date time) {
        this.passengerId = passengerId;
        this.rideTraceId = rideTraceId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.gender = gender;
        this.job = job;
        this.mobileNumber = mobileNumber;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyNumber = companyNumber;
        this.email = email;
        this.image = image;
        this.joined = joined;
        this.dropoffLocation = dropoffLocation;
        this.dropOffLocationId = dropOffLocationId;
        this.pickupLocation = pickupLocation;
        this.pickupLocationId = pickupLocationId;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", job='" + job + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyNumber='" + companyNumber + '\'' +
                ", email='" + email + '\'' +
                ", image=" + image +
                ", joined=" + joined +
                ", dropoffLocation='" + dropoffLocation + '\'' +
                ", dropOffLocationId=" + dropOffLocationId +
                ", pickupLocation='" + pickupLocation + '\'' +
                ", pickupLocationId=" + pickupLocationId +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getAge(){
        LocalDate ldBirthday = Instant.ofEpochMilli(birthday.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate today = LocalDate.now();

        return Period.between(ldBirthday, today).getYears();
    }

    public long dateDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        return elapsedMinutes;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getWaitingTime(){
        LocalDate today = LocalDate.now();
        Date dateToday = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return dateDifference(time, dateToday);
    }

    public int getPassengerId() {
        return passengerId;
    }

    public Date getTime() {
        return time;
    }

    public String getJob() {
        return job;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public String getEmail() {
        return email;
    }

    public Bitmap getImage() {
        return image;
    }

    public Date getJoined() {
        return joined;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public int getDropOffLocationId() {
        return dropOffLocationId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public int getPickupLocationId() {
        return pickupLocationId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(passengerId);
        parcel.writeString(rideTraceId);
        parcel.writeString(firstname);
        parcel.writeString(lastname);
        parcel.writeString(gender);
        parcel.writeString(job);
        parcel.writeString(mobileNumber);
        parcel.writeString(companyName);
        parcel.writeString(companyAddress);
        parcel.writeString(companyNumber);
        parcel.writeString(email);
        parcel.writeParcelable(image, i);
        parcel.writeString(dropoffLocation);
        parcel.writeInt(dropOffLocationId);
        parcel.writeString(pickupLocation);
        parcel.writeInt(pickupLocationId);
    }
}
