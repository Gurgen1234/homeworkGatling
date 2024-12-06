package computerdatabase;

import io.gatling.javaapi.http.HttpRequestActionBuilder;
import java.util.Map;

import static io.gatling.javaapi.core.CoreDsl.regex;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class Actions {

  private static Map<CharSequence, String> headers_0 = Map.ofEntries(
      Map.entry("Priority", "u=0, i"),
      Map.entry("Sec-GPC", "1"),
      Map.entry("Upgrade-Insecure-Requests", "1")
  );

  private static Map<CharSequence, String> headers_1 = Map.ofEntries(
      Map.entry("Priority", "u=4"),
      Map.entry("Sec-GPC", "1"),
      Map.entry("Upgrade-Insecure-Requests", "1")
  );

  static private Map<CharSequence, String> headers_8 = Map.ofEntries(
      Map.entry("Origin", "http://webtours.load-test.ru:1080"),
      Map.entry("Priority", "u=4"),
      Map.entry("Sec-GPC", "1"),
      Map.entry("Upgrade-Insecure-Requests", "1")
  );

  public final static HttpRequestActionBuilder GET_MAIN = http("Открытие главной страницы")
      .get("/webtours/")
      .headers(headers_0)
      .resources(
          http("request_1")
              .get("/webtours/header.html")
              .headers(headers_1),
          http("request_4")
              .get("/cgi-bin/welcome.pl")
              .headers(headers_1),
          http("request_5")
              .get("/WebTours/home.html")
              .headers(headers_1)
      ).check(status().is(200));

  public final static HttpRequestActionBuilder GET_USER_SESSION =
      http("request_6")
          .get("/cgi-bin/nav.pl?in=home")
          .headers(headers_1)
          .check(regex("name=\"userSession\" value=\"([^\"]+)\"/>").saveAs("userSession"));

  public final static HttpRequestActionBuilder LOG_IN = http("request_8")
      .post("/cgi-bin/login.pl")
      .headers(headers_8)
      .formParam("userSession", "#{userSession}")
      .formParam("username", "gaga")
      .formParam("password", "qweasd123")
      .formParam("login.x", "0")
      .formParam("login.y", "0")
      .formParam("JSFormSubmit", "off")
      .resources(
          http("request_9")
              .get("/cgi-bin/nav.pl?page=menu&in=home")
              .headers(headers_1),
          http("request_10")
              .get("/cgi-bin/login.pl?intro=true")
              .headers(headers_1)
      ).check(status().is(200));


  public final static HttpRequestActionBuilder WELCOME_PAGE = http("request_15")
      .get("/cgi-bin/welcome.pl?page=search")
      .headers(headers_1)
      .resources(
          http("request_16")
              .get("/cgi-bin/nav.pl?page=menu&in=flights")
              .headers(headers_1)

      ).check(status().is(200));


  public final static HttpRequestActionBuilder FLIGHTS_PAGE = http("request_17")
      .get("/cgi-bin/reservations.pl?page=welcome")
      .headers(headers_8)
      .ignoreProtocolChecks()
      .check(regex("<option value=\"(.*)\">").findRandom().saveAs("depart"))
      .check(regex("<option value=\"(.*)\">").findRandom().saveAs("arrive"))
      .check(status().is(200));
  ;

  public final static HttpRequestActionBuilder FIND_FLIGHT = http("request_21")
      .post("/cgi-bin/reservations.pl")
      .headers(headers_8)
      .formParam("depart", "#{depart}")
      .formParam("departDate", "12/05/2024")
      .formParam("arrive", "#{arrive}")
      .formParam("returnDate", "12/06/2024")
      .formParam("advanceDiscount", "0")
      .formParam("numPassengers", "1")
      .formParam("roundtrip", "on")
      .formParam("seatPref", "None")
      .formParam("seatType", "Coach")
      .formParam("findFlights.x", "65")
      .formParam("findFlights.y", "14")
      .formParam(".cgifields", "roundtrip")
      .formParam(".cgifields", "seatType")
      .formParam(".cgifields", "seatPref")
      .check(regex("\"outboundFlight\" value=\"([^\"]+)\"").findRandom().saveAs("outboundFlight"))
      .check(regex("\"returnFlight\" value=\"([^\"]+)\"").findRandom().saveAs("returnFlight"))
      .check(status().is(200));

  public final static HttpRequestActionBuilder SELECT_FLIGHT = http("request_22")
      .post("/cgi-bin/reservations.pl")
      .headers(headers_8)
      .formParam("outboundFlight", "#{outboundFlight}")
      .formParam("returnFlight", "#{returnFlight}")
      .formParam("numPassengers", "1")
      .formParam("advanceDiscount", "0")
      .formParam("seatType", "Coach")
      .formParam("seatPref", "None")
      .formParam("reserveFlights.x", "37")
      .formParam("reserveFlights.y", "11")
      .check(status().is(200));

  public final static HttpRequestActionBuilder SEND_PAYMENTS = http("request_23")
      .post("/cgi-bin/reservations.pl")
      .headers(headers_8)
      .formParam("firstName", "gaga")
      .formParam("lastName", "gogo")
      .formParam("address1", "street")
      .formParam("address2", "Moscow")
      .formParam("pass1", "gaga gogo")
      .formParam("creditCard", "12345678")
      .formParam("expDate", "12/35")
      .formParam("oldCCOption", "")
      .formParam("numPassengers", "1")
      .formParam("seatType", "Coach")
      .formParam("seatPref", "None")
      .formParam("outboundFlight", "#{outboundFlight}")
      .formParam("advanceDiscount", "0")
      .formParam("returnFlight", "#{returnFlight}")
      .formParam("JSFormSubmit", "off")
      .formParam("buyFlights.x", "48")
      .formParam("buyFlights.y", "14")
      .formParam(".cgifields", "saveCC")
      .check(status().is(200));

  public final static HttpRequestActionBuilder SEND_PAYMENTS_WITHOUT_ROUND_TRIP = http("request_23")
      .post("/cgi-bin/reservations.pl")
      .headers(headers_8)
      .formParam("firstName", "gaga")
      .formParam("lastName", "gogo")
      .formParam("address1", "street")
      .formParam("address2", "Moscow")
      .formParam("pass1", "gaga gogo")
      .formParam("creditCard", "12345678")
      .formParam("expDate", "12/35")
      .formParam("oldCCOption", "")
      .formParam("numPassengers", "1")
      .formParam("seatType", "Coach")
      .formParam("seatPref", "None")
      .formParam("outboundFlight", "#{outboundFlight}")
      .formParam("advanceDiscount", "0")
      .formParam("JSFormSubmit", "off")
      .formParam("buyFlights.x", "48")
      .formParam("buyFlights.y", "14")
      .formParam(".cgifields", "saveCC")
      .check(status().is(200));

  public final static HttpRequestActionBuilder BACK_TO_HOME = http("request_24")
      .get("/cgi-bin/nav.pl?page=menu&in=flights")
      .headers(headers_1)
      .resources(
          http("request_25")
              .get("/cgi-bin/welcome.pl?page=menus")
              .headers(headers_1),
          http("request_26")
              .get("/cgi-bin/login.pl?intro=true")
              .headers(headers_1)
      ).check(status().is(200));
}
