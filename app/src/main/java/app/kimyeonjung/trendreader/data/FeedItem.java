package app.kimyeonjung.trendreader.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FeedItem extends RealmObject implements Parcelable {

    @PrimaryKey
    private String postUrl;

    private String faviconUrl, blogName, blogUrl, postTitle, postContent;
    private Date updateAt, bookMarkAt = new Date();
    private boolean isBookMarked = false;

    public FeedItem() {
    }

    public String getFaviconUrl() {
        return faviconUrl;
    }

    public void setFaviconUrl(String faviconUrl) {
        this.faviconUrl = faviconUrl;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isBookMarked() {
        return isBookMarked;
    }

    public void setBookMarked(boolean bookMarked) {
        isBookMarked = bookMarked;
    }

    public String getAll() {
        return blogName + postTitle + postContent;
    }

    public Date getBookMarkAt() {
        return bookMarkAt;
    }

    public void setBookMarkAt(Date bookMarkAt) {
        this.bookMarkAt = bookMarkAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.postUrl);
        dest.writeString(this.faviconUrl);
        dest.writeString(this.blogName);
        dest.writeString(this.blogUrl);
        dest.writeString(this.postTitle);
        dest.writeString(this.postContent);
        dest.writeLong(this.updateAt != null ? this.updateAt.getTime() : -1);
        dest.writeLong(this.bookMarkAt != null ? this.bookMarkAt.getTime() : -1);
        dest.writeByte(this.isBookMarked ? (byte) 1 : (byte) 0);
    }

    private FeedItem(Parcel in) {
        this.postUrl = in.readString();
        this.faviconUrl = in.readString();
        this.blogName = in.readString();
        this.blogUrl = in.readString();
        this.postTitle = in.readString();
        this.postContent = in.readString();
        long tmpUpdateAt = in.readLong();
        this.updateAt = tmpUpdateAt == -1 ? null : new Date(tmpUpdateAt);
        long tmpBookMarkAt = in.readLong();
        this.bookMarkAt = tmpBookMarkAt == -1 ? null : new Date(tmpBookMarkAt);
        this.isBookMarked = in.readByte() != 0;
    }

    public static final Creator<FeedItem> CREATOR = new Creator<FeedItem>() {
        @Override
        public FeedItem createFromParcel(Parcel source) {
            return new FeedItem(source);
        }

        @Override
        public FeedItem[] newArray(int size) {
            return new FeedItem[size];
        }
    };
}
