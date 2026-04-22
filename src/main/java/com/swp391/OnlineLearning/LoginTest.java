import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LoginTest {
    public static void main(String[] args) throws Exception {
        String formData = "username=" + URLEncoder.encode("admin@example.com", StandardCharsets.UTF_8) +
                          "&password=" + URLEncoder.encode("thehieu03", StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .build();

        HttpClient client = HttpClient.newBuilder()
                // Don't follow redirects so we can see the 302 Location
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Location: " + response.headers().firstValue("Location").orElse("N/A"));
        
        // Expert test
        String expertForm = "username=" + URLEncoder.encode("expert@example.com", StandardCharsets.UTF_8) +
                          "&password=" + URLEncoder.encode("thehieu03", StandardCharsets.UTF_8);
        HttpRequest req2 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(expertForm))
                .build();
        HttpResponse<String> res2 = client.send(req2, HttpResponse.BodyHandlers.ofString());
        System.out.println("EXPERT Status: " + res2.statusCode());
        System.out.println("EXPERT Location: " + res2.headers().firstValue("Location").orElse("N/A"));
        
        // Marketing test
        String mktForm = "username=" + URLEncoder.encode("marketing@example.com", StandardCharsets.UTF_8) +
                          "&password=" + URLEncoder.encode("thehieu03", StandardCharsets.UTF_8);
        HttpRequest req3 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(mktForm))
                .build();
        HttpResponse<String> res3 = client.send(req3, HttpResponse.BodyHandlers.ofString());
        System.out.println("MKT Status: " + res3.statusCode());
        System.out.println("MKT Location: " + res3.headers().firstValue("Location").orElse("N/A"));
    }
}
