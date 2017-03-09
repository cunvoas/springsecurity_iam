<%@ page language="java" %>
<%@ page contentType="application/javascript; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
$(document).ready(function() {
	
	$(function() {

        var renderTxtSearch = null;
        /**
         * Override the default behavior
         * of autocomplete._renderItem.
         * @param {object} ul The conventional ul container
         *     of the autocomplete list.
         * @param {object} item The conventional object
         *    used to represent autocomplete data.
         *    {value:'',label:'',desc:'',icon:''}
         */
        var renderItemOverride = function (ul, item) {
        	var txtLabel = item.label;
        	var renderMatcher = new RegExp( $.ui.autocomplete.escapeRegex(renderTxtSearch), "i" );
        	if (renderMatcher.test(txtLabel)) {
        		txtLabel = txtLabel.replace(
	                          new RegExp(
	                            "(?![^&;]+;)(?!<[^<>]*)(" +
	                            $.ui.autocomplete.escapeRegex(renderTxtSearch) +
	                            ")(?![^<>]*>)(?![^&;]+;)", "gi"
	                          ), "<strong>$1</strong>" );
        	}
        	
            return $("<li></li>")
                .data("item.autocomplete", item)
                .append($( "<a>" ).html( txtLabel ) )
                .appendTo(ul);
        };
        	
		$( "#appSearch" ).autocomplete({
			minLength:3, // numbers of chars before send a request
			delay:500, // deplay in ms
		    open: function() {	// generation de la pseudo liste d'autocomplete
		        $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
		    },
		    close: function() { //fermeture de la pseudo liste d'autocomplete
		        $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
		    },
		    source: function(request, response) {
		    	renderTxtSearch = request.term;
		        $.getJSON(
		        	"<c:url value="/admin-iam/json/application/find.do" />",
		        	{ "search": request.term },
		        	function(result) {
		                response($.map(result, function(item) {	// population des Ã©lÃ©ments "$.ui" standardisÃ©s
		                    return {
		                    	label: item.code + " - "+ item.description,	// libelle a presenter dans la combo
		                    	value: item.code,			// PK de l'Ã©lÃ©ment
		                    	id:item					// objet complet de l'Ã©lÃ©ment
		                    }
		                }));
		       	 	}
		        );
		    },
		    create: function() {
		        $(this).data('ui-autocomplete')._renderItem = renderItemOverride;
		    	return false;
		    },
			select: function(event, ui) {	// action sur sÃ©lection
		 	    var selectApp = ui.item.id;
			   	$('#appId').val(selectApp.id);
			   	$('#appSearch').val(selectApp.code);
			   	$('#appDesc').val(selectApp.description);
			   	$('#formSearch').submit();
			   	return false;
			},
            error: function(jqXHR, textStatus, errorThrown){
                alert(textStatus);
            }
		    
		});
  
		
	});
});
