package com.prongbang.startroom.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by mdev on 10/11/2017 AD.
 */

public class LoanWithUserAndBook implements Parcelable {

    private int id;
    @ColumnInfo(name = "book_id")
    private int bookId;
    @ColumnInfo(name = "title")
    private String bookTitle;
    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "name")
    private String userName;
    @ColumnInfo(name = "last_name")
    private String lastName;
    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "start_time")
    private Date startTime;
    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "end_time")
    private Date endTime;

    public LoanWithUserAndBook(){}

    protected LoanWithUserAndBook(Parcel in) {
        id = in.readInt();
        bookId = in.readInt();
        bookTitle = in.readString();
        userId = in.readInt();
        userName = in.readString();
        lastName = in.readString();
    }

    public static final Creator<LoanWithUserAndBook> CREATOR = new Creator<LoanWithUserAndBook>() {
        @Override
        public LoanWithUserAndBook createFromParcel(Parcel in) {
            return new LoanWithUserAndBook(in);
        }

        @Override
        public LoanWithUserAndBook[] newArray(int size) {
            return new LoanWithUserAndBook[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(bookId);
        dest.writeString(bookTitle);
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(lastName);
    }
}
