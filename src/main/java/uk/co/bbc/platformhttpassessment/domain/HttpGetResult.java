package uk.co.bbc.platformhttpassessment.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HttpGetResult {
    @JsonProperty("Url")
    private final String url;
    @JsonProperty("Status_code")
    private final Integer status;
    @JsonProperty("Content_length")
    private final Long contentLength;
    @JsonProperty("Date")
    private final String dateTime;
    @JsonProperty("Error")
    private final String error;

    public HttpGetResult(String url, Integer status, Long contentLength, String dateTime, String error) {
        this.url = url;
        this.status = status;
        this.contentLength = contentLength;
        this.dateTime = dateTime;
        this.error = error;
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
        return error;
    }

    public static class Builder {
        String url;
        Integer statusCode;
        Long contentLength;
        String date;
        String error;

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

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public HttpGetResult build() {
            return new HttpGetResult(url, statusCode, contentLength, date, error);
        }
    }
}
