package two.factor.auth;

import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.ModelAndView;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.verify.VerifyResponse;
import com.nexmo.client.verify.VerifyStatus;
import com.nexmo.client.verify.CheckResponse;

public class App {

  static String API_KEY = "YOUR_API_KEY";
  static String API_SECRET = "YOUR_API_SECRET";
  static String requestId = "";

  public static void main(String[] args) {
    port(3000);
    staticFiles.location("/public");

    NexmoClient client = NexmoClient.builder().apiKey(API_KEY).apiSecret(API_SECRET).build();

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      return new ModelAndView(model, "register.hbs");
    }, new HandlebarsTemplateEngine());

    post("/register", (request, response) -> {
      String number = request.queryParams("number");

      VerifyResponse verifyResponse = client.getVerifyClient().verify(number, "NEXMO");
      if (verifyResponse.getStatus() == VerifyStatus.OK) {
        requestId = verifyResponse.getRequestId();
        System.out.printf("RequestID: %s", requestId);

      } else {
        System.out.printf("ERROR! %s: %s", verifyResponse.getStatus(), verifyResponse.getErrorText());
      }

      Map<String, Object> model = new HashMap<>();
      return new ModelAndView(model, "verify.hbs");
    }, new HandlebarsTemplateEngine());

    post("/check", (request, response) -> {
      CheckResponse checkResponse = client.getVerifyClient().check(requestId, request.queryParams("code"));

      Map<String, Object> model = new HashMap<>();

      if (checkResponse.getStatus() == VerifyStatus.OK) {
        model.put("status", "Registration Successful");
        System.out.println("Verification successful");
      } else {
        model.put("status", "Verification Failed");
        System.out.println("Verification failed: " + checkResponse.getErrorText());
      }

      return new ModelAndView(model, "result.hbs");
    }, new HandlebarsTemplateEngine());

  }
}
