package uk.co.bbc.platformhttpassessment;


import dagger.Component;
import uk.co.bbc.platformhttpassessment.modules.MainModule;

@Component(modules = {MainModule.class})
interface MainComponent {
    Application getApplication();
}

public class Main {
    public static void main(String[] args) {
        // silence apache logging
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        MainComponent mainComponent = DaggerMainComponent.create();
        mainComponent.getApplication().run(args);
    }
}
