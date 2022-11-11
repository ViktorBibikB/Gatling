package flood.challenge

import scala.concurrent.duration.*
import io.gatling.core.Predef.*
import io.gatling.core.session
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import net.sf.saxon.sapling.Saplings.doc

object Request {
  
  val min_time = 1
  val max_time = 3

  val headers_2 = Map(
    "Cache-Control" -> "no-cache",
    "Origin" -> "https://challenge.flood.io",
    "Pragma" -> "no-cache",
    "Sec-Fetch-Dest" -> "document",
    "Sec-Fetch-Mode" -> "navigate",
    "Sec-Fetch-Site" -> "same-origin",
    "Sec-Fetch-User" -> "?1",
    "Upgrade-Insecure-Requests" -> "1",
    "sec-ch-ua" -> """Google Chrome";v="107", "Chromium";v="107", "Not=A?Brand";v="24""",
    "sec-ch-ua-mobile" -> "?0",
    "sec-ch-ua-platform" -> "Windows")

  object BasePage {
    val open = exec(
      http("Open ChallengeFlood base page.")
        .get("/")
        .check(regex("""<input name="authenticity_token" type="hidden" value="(.*?)"""").saveAs("auth_token"))
        .check(regex("""name="challenger\[step_id\]" type="hidden" value="(.*?)"""").saveAs("challenger_step_id"))
        .check(regex("""name="challenger\[step_number\]" type="hidden" value="(.*?)"""").saveAs("challenger_step_number"))
        .check(
          status.is(200),
          regex("""<input id="(.*?)"""").is("challenger_step_id")))

    val pressStartButton = exec(http("Press button Start on the base page.")
      .post("/start")
      .headers(headers_2)
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${auth_token}")
      .formParam("challenger[step_id]", "${challenger_step_id}")
      .formParam("challenger[step_number]", "${challenger_step_number}")
      .formParam("commit", "Start")
      .check(status.is(302)))
  }

  object UserAgePage {
    val open = exec(http("Open User Age page.")
      .get("/step/2")
      .check(regex("""<input name="authenticity_token" type="hidden" value="(.*?)"""").saveAs("auth_token"))
      .check(regex("""name="challenger\[step_id\]" type="hidden" value="(.*?)"""").saveAs("challenger_step_id"))
      .check(regex("""name="challenger\[step_number\]" type="hidden" value="(.*?)"""").saveAs("challenger_step_number"))
      .check(
        status.is(200),
        regex("""<input id="(.*?)"""").is("challenger_step_id")))


    val setAgeAndpressButtonNext = exec(http("Set user age and press button next.")
      .post("/start")
      .headers(headers_2)
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${auth_token}")
      .formParam("challenger[step_id]", "${challenger_step_id}")
      .formParam("challenger[step_number]", "${challenger_step_number}")
      .formParam("challenger[age]", "24")
      .formParam("commit", "Next")
      .check(status.is(302)))
  }

  object OrderPage {
    val open = exec(http("Open Order page.")
      .get("/step/3")
      .check(regex("""<input name="authenticity_token" type="hidden" value="(.*?)"""").saveAs("auth_token"))
      .check(regex("""name="challenger\[step_id\]" type="hidden" value="(.*?)"""").saveAs("challenger_step_id"))
      .check(regex("""name="challenger\[step_number\]" type="hidden" value="(.*?)"""").saveAs("challenger_step_number"))
      .check(regex("""<input class="radio_buttons optional" id="(.*?)"""").findAll.saveAs("orderValue"))
      .check(regex("""<input class="radio_buttons optional" id="(.*?)"""").findRandom.saveAs("orderValueRandome"))
      .check(
        status.is(200),
        regex("""<input id="(.*?)"""").is("challenger_step_id")))
      .pause(1)

    val selectMaxOrder = exec(http("Specify the max order number")
      .post("/start")
      .headers(headers_2)
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${auth_token}")
      .formParam("challenger[step_id]", "${challenger_step_id}")
      .formParam("challenger[step_number]", "${challenger_step_number}")
      .formParam("challenger[largest_order]", "216")
      .formParam("challenger[order_selected]", "V21mTnB4U2xYbGNHaklTUlV0UGg1dz09LS1kUVEyS25rSzVMZXVZUmdXUnFoM3l3PT0=--35d64a3eeac6c338502a7eceea91958447412bb9")
      .formParam("commit", "Next")
      .check(status.is(302)))
  }

  object NoVisibleDataPage {
    val open = exec(http("Open No Visible Data Page.")
      .get("/step/4")
      .check(regex("""<input name="authenticity_token" type="hidden" value="(.*?)"""").saveAs("auth_token"))
      .check(regex("""name="challenger\[step_id\]" type="hidden" value="(.*?)"""").saveAs("challenger_step_id"))
      .check(regex("""name="challenger\[step_number\]" type="hidden" value="(.*?)"""").saveAs("challenger_step_number"))
      .check(
        status.is(200),
        regex("""<input id="(.*?)"""").is("challenger_step_id")))

    val tapButtonNext = exec(http("Specify array data.")
      .post("/start")
      .headers(headers_2)
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${auth_token}")
      .formParam("challenger[step_id]", "${challenger_step_id}")
      .formParam("challenger[step_number]", "${challenger_step_number}")
      .formParam("challenger[order_0]", "1667631552")
      .formParam("challenger[order_7]", "1667631552")
      .formParam("challenger[order_7]", "1667631552")
      .formParam("challenger[order_6]", "1667631552")
      .formParam("challenger[order_8]", "1667631552")
      .formParam("challenger[order_12]", "1667631552")
      .formParam("challenger[order_7]", "1667631552")
      .formParam("challenger[order_12]", "1667631552")
      .formParam("challenger[order_8]", "1667631552")
      .formParam("challenger[order_10]", "1667631552")
      .formParam("commit", "Next")
      .check(status.is(302)))
  }

  object OneTimeTokenPage {
    val open = exec(http("Open One time token page.")
      .get("/step/5")
      .check(regex("""<input name="authenticity_token" type="hidden" value="(.*?)"""").saveAs("auth_token"))
      .check(regex("""name="challenger\[step_id\]" type="hidden" value="(.*?)"""").saveAs("challenger_step_id"))
      .check(regex("""name="challenger\[step_number\]" type="hidden" value="(.*?)"""").saveAs("challenger_step_number"))
      .check(
        status.is(200),
        regex("""<input id="(.*?)"""").is("challenger_step_id")))

    val specifyOneTimeToken = exec(http("Specify the One time token")
      .post("/start")
      .headers(headers_2)
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${auth_token}")
      .formParam("challenger[step_id]", "${challenger_step_id}")
      .formParam("challenger[step_number]", "${challenger_step_number}")
      .formParam("challenger[one_time_token]", "2666666686")
      .formParam("commit", "Next")
      .check(status.is(302)))
  }

  val basePage = exec(BasePage.open)
    .pause(min_time, max_time)
    .exec(BasePage.pressStartButton)
    .pause(min_time, max_time)

  val userAgePage = exec(UserAgePage.open)
    .pause(min_time, max_time)
    .exec(UserAgePage.setAgeAndpressButtonNext)
    .pause(min_time, max_time)

  val orderPage = exec(OrderPage.open)
    .pause(min_time, max_time)
    .exec(OrderPage.selectMaxOrder)
    .pause(min_time, max_time)

  val noVisibleDataPage = exec(NoVisibleDataPage.open)
    .pause(min_time, max_time)
    .exec(NoVisibleDataPage.tapButtonNext)
    .pause(min_time, max_time)

  val oneTimeTokenPage = exec(OneTimeTokenPage.open)
    .pause(min_time, max_time)
    .exec(OneTimeTokenPage.specifyOneTimeToken)
    .pause(min_time, max_time)
}
