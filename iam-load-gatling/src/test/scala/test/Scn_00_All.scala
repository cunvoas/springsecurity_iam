package test

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.http.Headers.Names._
import scala.concurrent.duration._
import bootstrap._
import assertions._
import com.github.cunvoas.iam.test.load._

// https://github.com/excilys/gatling/wiki/Gatling%202
// http://gatling-tool.org/cheat-sheet/
class Scn_00_All extends Simulation {

	val httpProtocol = http
		.baseURL(LoadTestConf.serverBase)
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
		.connection("keep-alive")
		.userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:23.0) Gecko/20100101 Firefox/23.0")
		
	setUp(


	// ADMIN USERS
//	    IamRecordedSimulation.scn.inject	(nothingFor(1 seconds),  ramp(10 users) over (120 seconds)),
    // WS CALLS
		IamVectorService.scn.inject		(nothingFor(10 seconds), ramp(100 users) over (90 seconds)),
        IamVectorServiceRaw.scn.inject     (nothingFor(60 seconds), ramp(3000 users) over (150 seconds))

		)
		.protocols(httpProtocol)
}
