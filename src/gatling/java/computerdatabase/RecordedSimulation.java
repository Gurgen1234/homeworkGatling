package computerdatabase;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static computerdatabase.CommonScenario.BUY_TICKET;
import static io.gatling.javaapi.core.CoreDsl.constantConcurrentUsers;
import static io.gatling.javaapi.core.CoreDsl.rampConcurrentUsers;
import static io.gatling.javaapi.http.HttpDsl.http;

public class RecordedSimulation extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://webtours.load-test.ru:1090")
      .inferHtmlResources()
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
      .doNotTrackHeader("1")
      .userAgentHeader(
          "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:133.0) Gecko/20100101 Firefox/133.0");

  {
    setUp(BUY_TICKET.injectClosed(
        rampConcurrentUsers(0).to(2).during(40),
        constantConcurrentUsers(2).during(80),
        rampConcurrentUsers(2).to(4).during(40),
        constantConcurrentUsers(4).during(80),
        rampConcurrentUsers(4).to(6).during(40),
        constantConcurrentUsers(6).during(80),
        rampConcurrentUsers(6).to(8).during(40),
        constantConcurrentUsers(8).during(80),
        rampConcurrentUsers(8).to(10).during(40),
        constantConcurrentUsers(10).during(80),
        rampConcurrentUsers(10).to(12).during(40),
        constantConcurrentUsers(12).during(80),
        rampConcurrentUsers(12).to(14).during(40),
        constantConcurrentUsers(14).during(80),
        rampConcurrentUsers(14).to(16).during(40),
        constantConcurrentUsers(16).during(80),
        rampConcurrentUsers(16).to(18).during(40),
        constantConcurrentUsers(  18).during(80),
        rampConcurrentUsers(18).to(20).during(40),
        constantConcurrentUsers(20).during(80)

    ).protocols(httpProtocol));
    BUY_TICKET.injectClosed(constantConcurrentUsers(18).during(3600))
              .protocols(httpProtocol);
  }
}
