package uk.co.bbc.platformhttpassessment.domain;

public class HttpGetResult {
    private String url;
    private int status;
    private long contentLength;
    private String dateTime;

    public HttpGetResult(String url, int status, long contentLength, String dateTime) {
        this.url = url;
        this.status = status;
        this.contentLength = contentLength;
        this.dateTime = dateTime;
    }

    public String getUrl() {
        return url;
    }

    public int getStatus() {
        return status;
    }

    public long getContentLength() {
        return contentLength;
    }

    public String getDateTime() {
        return dateTime;
    }
}
