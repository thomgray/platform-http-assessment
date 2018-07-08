package uk.co.bbc.platformhttpassessment.domain;

public class HttpGetResult {
    private final String url;
    private final Integer status;
    private final Long contentLength;
    private final String dateTime;

    public HttpGetResult(String url, Integer status, Long contentLength, String dateTime) {
        this.url = url;
        this.status = status;
        this.contentLength = contentLength;
        this.dateTime = dateTime;
    }

    public String getUrl() {
        return url;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getError() {
        return null;
    }

    public static class Builder {
        String url;
        Integer statusCode;
        Long contentLength;
        String date;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder contentLength(Long contentLength) {
            this.contentLength = contentLength;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public HttpGetResult build() {
            return new HttpGetResult(url, statusCode, contentLength, date);
        }
    }
}
