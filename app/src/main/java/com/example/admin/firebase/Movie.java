package com.example.admin.firebase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 11/30/2017.
 */

public class Movie implements Parcelable{
    String name, year,director;

    public Movie() {
    }





    public Movie(String name, String year, String director) {

        this.name = name;
        this.year = year;
        this.director = director;


    }
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    protected Movie(Parcel in) {
        name = in.readString();
        year = in.readString();
        director = in.readString();

    }
    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(year);
        parcel.writeString(director);

    }
}
