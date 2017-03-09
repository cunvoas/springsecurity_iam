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
import test.LoadTestConf

object IamRecordedSimulation {

	val headers_1 = Map("""Accept""" -> """text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""")

	val headers_2 = Map(
		"""Accept""" -> """text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""",
		"""Content-Type""" -> """application/x-www-form-urlencoded""")

	val headers_5 = Map(
		"""Accept""" -> """text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:10 GMT""",
		"""If-None-Match""" -> """W/"44432-1394442850000"""")

	val headers_6 = Map("""Accept""" -> """text/css,*/*;q=0.1""")

	val headers_7 = Map("""Accept""" -> """*/*""")

	val headers_13 = Map(
		"""Accept""" -> """text/css,*/*;q=0.1""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"1018-1394442849570"""")

	val headers_14 = Map(
		"""Accept""" -> """*/*""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"27637-1394442849764"""")

	val headers_15 = Map(
		"""Accept""" -> """*/*""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"61065-1394442849755"""")

	val headers_16 = Map(
		"""Accept""" -> """*/*""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"2751-1394442849780"""")

	val headers_17 = Map(
		"""Accept""" -> """*/*""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"64858-1394442849775"""")

	val headers_18 = Map(
		"""Accept""" -> """text/css,*/*;q=0.1""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"4741-1394442849571"""")

	val headers_44 = Map(
		"""Accept""" -> """text/css,*/*;q=0.1""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"27016-1394442849729"""")

	val headers_45 = Map(
		"""Accept""" -> """*/*""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"7010-1394442849682"""")

	val headers_46 = Map(
		"""Accept""" -> """*/*""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"3178-1394442849676"""")

	val headers_47 = Map(
		"""Accept""" -> """*/*""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"202061-1394442849707"""")

	val headers_50 = Map(
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"1720-1394442849730"""")

	val headers_51 = Map(
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:09 GMT""",
		"""If-None-Match""" -> """W/"3121-1394442849728"""")

	val headers_52 = Map(
		"""Accept""" -> """application/json""",
		"""Cache-Control""" -> """no-cache""",
		"""Content-Type""" -> """application/json; charset=UTF-8""",
		"""Pragma""" -> """no-cache""",
		"""X-Requested-With""" -> """XMLHttpRequest""")

	val headers_53 = Map(
		"""Accept""" -> """text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""",
		"""Cache-Control""" -> """max-age=0""")

	val headers_54 = Map(
		"""Accept""" -> """text/css,*/*;q=0.1""",
		"""Cache-Control""" -> """max-age=0""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:10 GMT""",
		"""If-None-Match""" -> """W/"128829-1394442850000"""")

	val headers_55 = Map(
		"""Cache-Control""" -> """max-age=0""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:10 GMT""",
		"""If-None-Match""" -> """W/"1482-1394442850000"""")

	val headers_56 = Map(
		"""Cache-Control""" -> """max-age=0""",
		"""If-Modified-Since""" -> """Mon, 10 Mar 2014 09:14:10 GMT""",
		"""If-None-Match""" -> """W/"1720-1394442850000"""")

	val headers_XmlHttp = Map(
		"""Accept""" -> """application/json""",
		"""Content-Type""" -> """application/json""",
		"""X-Requested-With""" -> """XMLHttpRequest""")

	val chain_0 = exec(http("iam-home")
			.get("""/iam-webapp/admin-iam/""")
            .check(css("input[name='lt']", "value").saveAs("casLT"))
			.headers(headers_1))
		.pause(5)
		.feed(csv("admin_users.csv").circular)
		.exec(http("cas-auth")
			.post(LoadTestConf.casBase + """/cas/login?service=""" + LoadTestConf.serverBase + """/iam-webapp/j_spring_cas_security_check""")
			.headers(headers_2)
			.param("""username""", "${username}")
			.param("""password""", "${password}")
			.param("""lt""", "${casLT}")
			.param("""execution""", """e2s1""")
			.param("""_eventId""", """submit""")
			.param("""submit""", """SE CONNECTER""")
			.check(status.is(404))
			)
        .pause(1)
        .exec(http("request_3")
            .get("""/iam-webapp/resources/imgs/flag/flag.fr.png"""))
        .pause(125 milliseconds)
        .exec(http("request_5")
            .get("""/iam-webapp/resources/font-awesome/fonts/fontawesome-webfont.woff?v=4.0.3""")
            .headers(headers_5))
        .pause(7)
        .exec(http("request_6")
            .get("""/iam-webapp/resources/css/morris/morris-0.4.3.min.css""")
            .headers(headers_6))
        .exec(http("request_7")
            .get("""/iam-webapp/resources/js/morris/morris-0.4.3.min.js""")
            .headers(headers_7))
        .exec(http("request_8")
            .get("""/iam-webapp/resources/js/morris/raphael-min.js""")
            .headers(headers_7))
        .pause(82 milliseconds)
        .exec(http("request_9")
            .get("""/iam-webapp/resources/js/morris/jsDataChart.jsp""")
            .headers(headers_7))
        .pause(105 milliseconds)
        .exec(http("request_12")
            .get("""/iam-webapp/admin-iam/application/list.do""")
            .headers(headers_1))
        .pause(94 milliseconds)
        .exec(http("request_13")
            .get("""/iam-webapp/resources/css/tablesorter/jquery.tablesorter.pager.css""")
            .headers(headers_13))
        .exec(http("request_14")
            .get("""/iam-webapp/resources/js/tablesorter/jquery.tablesorter.pager.js""")
            .headers(headers_14))
        .exec(http("request_15")
            .get("""/iam-webapp/resources/js/tablesorter/jquery.tablesorter.js""")
            .headers(headers_15))
        .exec(http("request_16")
            .get("""/iam-webapp/resources/js/tablesorter/tables.js""")
            .headers(headers_16))
        .pause(11 milliseconds)
        .exec(http("request_17")
            .get("""/iam-webapp/resources/js/tablesorter/jquery.tablesorter.widgets.js""")
            .headers(headers_17))
        .exec(http("request_18")
            .get("""/iam-webapp/resources/css/tablesorter/theme.bootstrap.css""")
            .headers(headers_18))
        .pause(284 milliseconds)
        .exec(http("request_21")
            .get("""/iam-webapp/admin-iam/application/edit.do?appId=IAM""")
            .headers(headers_1))
        .pause(117 milliseconds)
        .exec(http("request_24")
            .post("""/iam-webapp/admin-iam/application/edit.do""")
            .headers(headers_2)
            .param("""code""", """IAM""")
            .param("""id""", """1""")
            .param("""description""", """Identity Access Management""")
            .param("""url""", LoadTestConf.serverBase + """/iam-webapp"""))
        .pause(41 milliseconds)
        .exec(http("request_27")
            .post("""/iam-webapp/admin-iam/application/edit.do""")
            .headers(headers_2)
            .param("""code""", """IAM""")
            .param("""id""", """1""")
            .param("""description""", """Identity Access Management""")
            .param("""url""", LoadTestConf.serverBase + """/iam-webapp"""))
        .pause(61 milliseconds)
        .exec(http("request_30")
            .get("""/iam-webapp/admin-iam/application/list.do""")
            .headers(headers_1))
        .pause(52 milliseconds)
        .exec(http("request_33")
            .get("""/iam-webapp/admin-iam/role/list.do?appId=IAM""")
            .headers(headers_1))
        .pause(80 milliseconds)
        .exec(http("request_34")
            .get("""/iam-webapp/resources/js/autocomplete/jquery-ui-1.10.4.custom.min.js""")
            .headers(headers_7))
        .exec(http("request_35")
            .get("""/iam-webapp/resources/css/autocomplete/jquery-ui-1.10.4.custom.min.css""")
            .headers(headers_6))
        .pause(48 milliseconds)
        .exec(http("request_36")
            .get("""/iam-webapp/resources/js/autocomplete/role.jsp""")
            .headers(headers_7))
        .exec(http("request_39")
            .get("""/iam-webapp/resources/css/autocomplete/images/ui-bg_flat_75_ffffff_40x100.png"""))
        .pause(6)
        .exec(http("request_40")
            .get("""/iam-webapp/admin-iam/application/list.do""")
            .headers(headers_1))
        .pause(79 milliseconds)
        .exec(http("request_43")
            .get("""/iam-webapp/admin-iam/resource/findResource.do?appId=IAM""")
            .headers(headers_1))
        .pause(72 milliseconds)
        .exec(http("request_44")
            .get("""/iam-webapp/resources/js/jstree/themes/default/style.css""")
            .headers(headers_44))
        .exec(http("request_45")
            .get("""/iam-webapp/resources/js/jstree/jquery.ui.touch.js""")
            .headers(headers_45))
        .exec(http("request_46")
            .get("""/iam-webapp/resources/js/jstree/jquery.hotkeys.js""")
            .headers(headers_46))
        .exec(http("request_47")
            .get("""/iam-webapp/resources/js/jstree/jstree.js""")
            .headers(headers_47))
        .pause(16 milliseconds)
        .exec(http("request_50")
            .get("""/iam-webapp/resources/js/jstree/themes/default/throbber.gif""")
            .headers(headers_50))
        .exec(http("request_51")
            .get("""/iam-webapp/resources/js/jstree/themes/default/32px.png""")
            .headers(headers_51))
        .pause(7)
        .exec(http("request_52")
            .post("""/iam-webapp/admin-iam/json/resource/renameTreeNode.do?appCode=IAM""")
            .headers(headers_52)
            .body(RawFileBody("RecordedSimulation_request_52.txt")))
        .pause(46 milliseconds)
        .exec(http("request_53")
            .get("""/iam-webapp/admin-iam/resource/findResource.do?appId=IAM""")
            .headers(headers_53))
        .pause(63 milliseconds)
        .exec(http("request_54")
            .get("""/iam-webapp/resources/css/bootstrap.css""")
            .headers(headers_54)
            .check(status.is(404)))
        .pause(164 milliseconds)
        .exec(http("request_55")
            .get("""/iam-webapp/resources/ico/iam_32x32.png""")
            .headers(headers_55)
            .check(status.is(404)))
        .pause(48 milliseconds)
        .exec(http("request_56")
            .get("""/iam-webapp/resources/js/jstree/themes/default/throbber.gif""")
            .headers(headers_56))
        .pause(11)
        .exec(http("request_57")
            .post("""/iam-webapp/admin-iam/json/resource/deleteTreeNode.do?appCode=IAM""")
            .headers(headers_52)
            .body(RawFileBody("RecordedSimulation_request_57.txt")))

val chain_1 = pause(16)
        .exec(http("request_58")
            .get("""/iam-webapp/admin-iam/application/list.do""")
            .headers(headers_1))
        .pause(75 milliseconds)
        .exec(http("request_61")
            .get("""/iam-webapp/admin-iam/resource/values.do?appId=IAM""")
            .headers(headers_1))
        .pause(78 milliseconds)
        .exec(http("request_62")
            .get("""/iam-webapp/resources/js/jstree/grid/jstreegrid.js""")
            .headers(headers_7))
        .pause(76 milliseconds)
        .exec(http("request_63")
            .get("""/iam-webapp/resources/js/autocomplete/resourceValue.jsp""")
            .headers(headers_7))
        .exec(http("request_66")
            .get("""/iam-webapp/resources/css/autocomplete/images/ui-bg_glass_75_e6e6e6_1x400.png"""))
        .pause(3)
        .exec(http("request_67")
            .get("""/iam-webapp/resources/css/autocomplete/images/ui-anim_basic_16x16.gif"""))
        .exec(http("request_68")
            .get("""/iam-webapp/resources/css/autocomplete/images/ui-bg_glass_75_dadada_1x400.png"""))
        .pause(1)
        .exec(http("request_69")
            .get("""/iam-webapp/admin-iam/resource/values.do?appId=IAM&roleId=250""")
            .headers(headers_1))
        .pause(78 milliseconds)
        .exec(http("request_70")
            .get("""/iam-webapp/resources/js/autocomplete/resourceValue.jsp""")
            .headers(headers_7))
        .exec(http("request_73")
            .get("""/iam-webapp/admin-iam/resource/values.do?appId=IAM&roleId=250""")
            .headers(headers_1))
        .pause(78 milliseconds)
        .exec(http("request_74")
            .get("""/iam-webapp/resources/js/autocomplete/resourceValue.jsp""")
            .headers(headers_7))
        .exec(http("request_77")
            .get("""/iam-webapp/admin-iam/json/resource/changeNodeValue.do?appCode=IAM&idRole=250&idRes=13&idValue=2&_=1400245356872""")
            .headers(headers_XmlHttp))
        .pause(3)
        .exec(http("request_78")
            .get("""/iam-webapp/admin-iam/json/resource/changeNodeValue.do?appCode=IAM&idRole=250&idRes=14&idValue=3&_=1400245356873""")
            .headers(headers_XmlHttp))
        .pause(21)
        .exec(http("request_79")
            .get("""/iam-webapp/admin-iam/json/resource/changeNodeValue.do?appCode=IAM&idRole=250&idRes=8&idValue=2&_=1400245356874""")
            .headers(headers_XmlHttp))
        .pause(3)
        .exec(http("request_80")
            .get("""/iam-webapp/admin-iam/json/resource/changeNodeValue.do?appCode=IAM&idRole=250&idRes=9&idValue=3&_=1400245356875""")
            .headers(headers_XmlHttp))
        .pause(13)
        .exec(http("request_81")
            .get("""/iam-webapp/admin-iam/json/resource/changeNodeValue.do?appCode=IAM&idRole=250&idRes=6&idValue=0&_=1400245356876""")
            .headers(headers_XmlHttp))
        .pause(5)
        .exec(http("request_82")
            .get("""/iam-webapp/admin-iam/json/resource/changeNodeValue.do?appCode=IAM&idRole=250&idRes=6&idValue=2&_=1400245356877""")
            .headers(headers_XmlHttp))
        .pause(2)
        .exec(http("request_83")
            .get("""/iam-webapp/admin-iam/json/resource/changeNodeValue.do?appCode=IAM&idRole=250&idRes=6&idValue=1&_=1400245356878""")
            .headers(headers_XmlHttp))
        .pause(7)
        .exec(http("request_84")
            .get("""/iam-webapp/admin-iam/json/resource/changeNodeValue.do?appCode=IAM&idRole=250&idRes=5&idValue=3&_=1400245356879""")
            .headers(headers_XmlHttp))
        .pause(4)
        .exec(http("request_85")
            .get("""/iam-webapp/admin-iam/json/resource/changeNodeValue.do?appCode=IAM&idRole=250&idRes=3&idValue=2&_=1400245356880""")
            .headers(headers_XmlHttp))
        .pause(3)
        .exec(http("request_86")
            .get("""/iam-webapp/admin-iam/resource/values.do""")
            .headers(headers_1))
        .pause(75 milliseconds)
        .exec(http("request_87")
            .get("""/iam-webapp/resources/js/autocomplete/resourceValue.jsp""")
            .headers(headers_7))
        .exec(http("request_90")
            .get("""/iam-webapp/admin-iam/role/list.do""")
            .headers(headers_1))
        .pause(72 milliseconds)
        .exec(http("request_91")
            .get("""/iam-webapp/resources/js/autocomplete/role.jsp""")
            .headers(headers_7))
        .exec(http("request_94")
            .get("""/iam-webapp/j_spring_security_logout""")
            .headers(headers_1))
					
	val scn = scenario("Admin Scenario").exec(chain_0, chain_1)

}