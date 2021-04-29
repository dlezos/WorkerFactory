package ltd.lezos.work;

import ltd.lezos.worker.ResourceCreator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SomeHTTPWork extends SomeWork {
    ResourceCreator creator;
    static final String URL = "http://www.w3schools.com/xml/tempconvert.asmx/CelsiusToFahrenheit";
    long celcius = 0;

    public SomeHTTPWork() {
    }

    public SomeHTTPWork(long l) {
        super(l);
        celcius = l;
    }

    public SomeHTTPWork(ResourceCreator creator) {
        this.creator = creator;
    }

    public void run() {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .POST(HttpRequest.BodyPublishers.ofString("Celcius="+celcius))
                    .build();

            HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            //This seems not to be working any more so body is always null
            System.out.println(getId()+"="+(response.body()!=null?response.body():(celcius*2.1+32)));
        } catch (Exception e) {
            System.out.println("Exception in "+getId()+" "+e.getMessage());
        }
    }
}
