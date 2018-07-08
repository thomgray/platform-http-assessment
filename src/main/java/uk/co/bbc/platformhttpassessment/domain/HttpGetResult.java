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

//    public static class Builder {
//        String url;
//        int statusCode;
//        long contentLength;
//        String date;
//
//        public Builder url(String url) {
//            this.url = url;
//            return this;
//        }
//
//        public Builder statusCode(int statusCode) {
//            this.statusCode = statusCode;
//            return this;
//        }
//
//        public Builder contentLength(long contentLength) {
//            this.contentLength = contentLength;
//            return this;
//        }
//
//        public Builder date(String date) {
//            this.date = date;
//            return this;
//        }
//
//        public HttpGetResult build() {
//            return new HttpGetResult(url, statusCode, contentLength, date);
//        }
//    }
}
