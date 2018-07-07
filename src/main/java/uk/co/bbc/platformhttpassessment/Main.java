package uk.co.bbc.platformhttpassessment;


import dagger.Component;
import uk.co.bbc.platformhttpassessment.modules.MainModule;

@Component(modules = {MainModule.class})
interface MainComponent {
    Application getApplication();
}

public class Main {
    public static void main(String[] args) {
        MainComponent mainComponent = DaggerMainComponent.create();
        mainComponent.getApplication().run(args);
    }
}
