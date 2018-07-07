package uk.co.bbc.platformhttpassessment;

import javax.inject.Inject;

public class Application {

    private final UrlGetter urlGetter;

    @Inject
    public Application(UrlGetter urlGetter) {
        this.urlGetter = urlGetter;
    }

    public void run(String[] args) {
    }
}
