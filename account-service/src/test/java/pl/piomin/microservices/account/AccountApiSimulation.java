package pl.piomin.microservices.account;

import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.holdFor;
import static io.gatling.javaapi.core.CoreDsl.reachRps;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class AccountApiSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = setupProtocolForSimulation();

    private static HttpProtocolBuilder setupProtocolForSimulation() {
        return http.baseUrl("http://127.0.0.2:3334")
                .acceptHeader("application/json")
                .maxConnectionsPerHost(2000)
                .userAgentHeader("Gatling/Performance Test")
                .header("Connection", "keep-alive");
    }

    public AccountApiSimulation() {
        var populations =
                List.of(createScenario()
                        .injectOpen(
                                atOnceUsers(10000)
                        )
                        .protocols(httpProtocol));
        setUp(populations)
                .assertions(
                        global().responseTime().max().lte(1000),
                        global().successfulRequests().percent().gt(90d)
                )
                .throttle(reachRps(800).in(Duration.ofSeconds(10)),
                        holdFor(Duration.ofSeconds(60)));
    }

    private ScenarioBuilder createScenario() {
        return scenario("Get account by ID")
                .exec(http("GET /").get("/")
                        .check(status().is(200)));
    }

    private OpenInjectionStep postEndpointInjectionProfile() {
        int totalDesiredUserCount = 800;
        int from = 200;
        int spike = 1000;
        int during = 60;

        long spikeDuring = 10;
        return constantUsersPerSec(10).during(Duration.ofSeconds(spikeDuring));
    }
}
