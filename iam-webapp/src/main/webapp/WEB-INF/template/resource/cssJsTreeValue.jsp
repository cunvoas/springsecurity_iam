<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link rel="stylesheet" href="<c:url value="/resources/js/jstree/themes/default/style.css" />">

<!-- autocomplete theme -->
<link rel="stylesheet" href="<c:url value="/resources/css/autocomplete/jquery-ui-1.10.4.custom.min.css" />">
<style>
	.ui-autocomplete-loading {
		background: white url('<c:url value="/resources/css/autocomplete/images/ui-anim_basic_16x16.gif" />') right center no-repeat;
	}
	#fadeout-wrapper {
        position:relative;
        z-index:1;
	}
	#fadeout-KO {
        position:absolute;
        color: #ff0000;
        z-index: -1;
        left: 130px;
        top: -17px;
	}
    #fadeout-OK {
        position:absolute;
		color: #00ff00;
        z-index: -1;
        left: 130px;
        top: -17px;
    }
  .custom-combobox {
    position: relative;
    display: inline-block;
  }
  .custom-combobox-toggle {
    position: absolute;
    top: 0;
    bottom: 0;
    margin-left: -1px;
    padding: 0;
    /* support: IE7 */
    *height: 1.7em;
    *top: 0.1em;
  }
  .custom-combobox-input {
    margin: 0;
    padding: 0.3em;
  }
</style>