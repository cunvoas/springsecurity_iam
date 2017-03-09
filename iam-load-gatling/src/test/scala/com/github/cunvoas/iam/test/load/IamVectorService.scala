package com.github.cunvoas.iam.test.load

import io.gatling.core.Predef._
import io.gatling.core.session.Expression
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.http.Headers.Names._
import io.gatling.http.Headers.Values._
import scala.concurrent.duration._
import bootstrap._
import assertions._

object IamVectorService {

    val headers_3 = Map(
        """Content-Type""" -> """text/xml;charset=UTF-8""",
        """SOAPAction""" -> """""""")

    val scn = scenario("Scenario ServiceVectorIam")
        .exec(http("ServiceDirectory")
            .get("""/iam-webapp/services/iam/soap?wsdl="""))
        .pause(209 milliseconds)
        .exec(http("WSDL_ServiceVectorIam")
            .get("""/iam-webapp/services/iam/soap?wsdl=ServiceVectorIam.wsdl"""))
        .pause(15)
        .feed(csv("ws_users.csv").circular)
        .exec(http("request_3")
            .post("""/iam-webapp/services/iam/soap""")
            .headers(headers_3)
            .body(RawFileBody("IamVectorService_request_3.txt")))
        .pause(27)
        .exec(http("request_4")
            .post("""/iam-webapp/services/iam/soap""")
            .headers(headers_3)
            .body(RawFileBody("IamVectorService_request_4.txt")))
        .pause(20)
        .exec(http("request_5")
            .post("""/iam-webapp/services/iam/soap""")
            .headers(headers_3)
            .body(RawFileBody("IamVectorService_request_5.txt")))
        .pause(25)
        .exec(http("request_6")
            .post("""/iam-webapp/services/iam/soap""")
            .headers(headers_3)
            .body(RawFileBody("IamVectorService_request_6.txt"))
            .check(status.is(500)))
        .pause(6)
        .exec(http("request_7")
            .post("""/iam-webapp/services/iam/soap""")
            .headers(headers_3)
            .body(RawFileBody("IamVectorService_request_7.txt")))

}