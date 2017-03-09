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

object IamVectorServiceRaw {

    val headers_3 = Map(
        """Content-Type""" -> """text/xml;charset=UTF-8""",
        """SOAPAction""" -> """""""")

    val scn = scenario("Scenario ServiceVectorIam Raw")
        .exec(http("WSDL_ServiceVectorIam")
            .get("""/iam-webapp/services/iam/soap?wsdl=ServiceVectorIam.wsdl"""))
        .pause(15)
        .feed(csv("ws_users.csv").circular)
        .exec(http("raw_vector")
            .post("""/iam-webapp/services/iam/soap""")
            .headers(headers_3)
            .body(ELFileBody("IamVectorService_RAW.tpl")))
        .pause(20)
}