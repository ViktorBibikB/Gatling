package flood.challenge

import io.gatling.core.Predef.Simulation

import scala.concurrent.duration.*
import io.gatling.core.Predef.*
import io.gatling.core.session
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import net.sf.saxon.sapling.Saplings.doc


class ChallegeFloodIo extends Simulation {

  val ramp_users = System.getProperty("ramp_users", "5").toInt
  val ramp_duration = System.getProperty("ramp_duration", "10").toInt
  val duration = System.getProperty("duration", "60").toInt

  val httpProtocol = http
    .baseUrl("https://challenge.flood.io")
    .disableFollowRedirect
    .inferHtmlResources()
    .acceptHeader("image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36")

  val scn = scenario("Path through all steps.")
    .during(duration) {
      randomSwitch(
        80.0 ->
          exec(Request.basePage, Request.userAgePage),
        20.0 ->
          exec(Request.basePage, Request.userAgePage,
            Request.orderPage, Request.noVisibleDataPage, Request.oneTimeTokenPage)
      )
    }

  setUp(scn.inject(rampUsers(ramp_users) during (ramp_duration))).protocols(httpProtocol)

}
