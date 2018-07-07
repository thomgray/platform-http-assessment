package uk.co.bbc.platformhttpassessment;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        for (String arg :
                args) {
            System.out.println(arg);
        }
        HttpClient client = HttpClients.createDefault();
        List<HttpGetResult> results = new ArrayList<>();

    }
}
