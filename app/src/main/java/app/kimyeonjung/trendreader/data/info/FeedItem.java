package app.kimyeonjung.trendreader.data.info;

import java.util.Calendar;
import java.util.Date;

public class FeedItem {
    private String blogTag, faviconUrl, blogName, blogUrl, postTitle, postUrl, postContent;
    private Date updateAt;

    public FeedItem() {
    }

    public String getBlogTag() {
        return blogTag;
    }

    public void setBlogTag(String blogTag) {
        this.blogTag = blogTag;
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

    public void setUpdateAt(String input) {
        //2017년 12월 5일 9시 34분
        String[] temp = input
                .replaceAll("년", "")
                .replaceAll("월", "")
                .replaceAll("일", "")
                .replaceAll("시", "")
                .replaceAll("분", "")
                .split("\\s");

        Calendar calendar = Calendar.getInstance();
        calendar.set(
                Integer.parseInt(temp[0]),
                Integer.parseInt(temp[1]),
                Integer.parseInt(temp[2]),
                Integer.parseInt(temp[3]),
                Integer.parseInt(temp[4])
        );
        this.updateAt = calendar.getTime();
    }
}
