package app.kimyeonjung.trendreader.data.feed;

import java.util.Date;

import io.realm.RealmObject;

public class FeedItem extends RealmObject{
    
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

    String getFaviconUrl() {
        return faviconUrl;
    }

    void setFaviconUrl(String faviconUrl) {
        this.faviconUrl = faviconUrl;
    }

    public String getBlogName() {
        return blogName;
    }

    void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    String getPostTitle() {
        return postTitle;
    }

    void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostUrl() {
        return postUrl;
    }

    void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    String getPostContent() {
        return postContent;
    }

    void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    Date getUpdateAt() {
        return updateAt;
    }

    void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getAll() {
        return blogTag + blogName + postTitle + postContent;
    }
}
