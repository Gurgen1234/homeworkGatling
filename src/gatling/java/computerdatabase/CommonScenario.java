package computerdatabase;

import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;

import static computerdatabase.Actions.SEND_PAYMENTS;
import static computerdatabase.Actions.SEND_PAYMENTS_WITHOUT_ROUND_TRIP;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.scenario;

public class CommonScenario {


  public static final ScenarioBuilder BUY_TICKET = scenario("Покупка биллета")
      .exec(Actions.GET_MAIN)
      .exec(Actions.GET_USER_SESSION)
      .exec(Actions.LOG_IN)
      .exec(Actions.WELCOME_PAGE)
      .exec(Actions.FLIGHTS_PAGE)
      .exec(Actions.FIND_FLIGHT)
      .exec(Actions.SELECT_FLIGHT)
      .randomSwitch().on(
          new Choice.WithWeight(50.0, exec(SEND_PAYMENTS)),
          new Choice.WithWeight(50.0, exec(SEND_PAYMENTS_WITHOUT_ROUND_TRIP))
      )
      .exec(SEND_PAYMENTS)
      .exec(Actions.BACK_TO_HOME);

}
