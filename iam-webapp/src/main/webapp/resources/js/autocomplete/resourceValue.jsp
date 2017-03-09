<%@ page language="java" %>
<%@ page contentType="application/javascript; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
        	
		$( "#appCode" ).autocomplete({
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
		        	{ "search": request.term
		        	 },
		        	function(result) {
		                response($.map(result, function(item) {	// population des elements "$.ui" standardises
		                    return {
		                    	label: item.code + " - "+ item.description,	// libelle a presenter dans la combo
		                    	value: item.code,			// PK de l'element
		                    	id:item					// objet complet de l'element
		                    }
		                }));
		       	 	}
		        );
		    },
		    create: function() {
		        $(this).data('ui-autocomplete')._renderItem = renderItemOverride;
		    	return false;
		    },
			select: function(event, ui) {	// action sur selection
                var selectApp = ui.item.id;
			   	$('#appId').val(selectApp.id);
			   	$('#appCode').val(selectApp.code);
			   	$('#appDesc').val(selectApp.description);
			   
			    $('#appCode').attr("value", selectApp.code);
			    
			   // clear role form
			    $('#roleId').val("");
                $('#roleCode').val("");
                $('#roleDesc').val("");
                
                document.location.href="<c:url value="/admin-iam/resource/values.do" />?appId="+selectApp.code;
			   	return false;
			},
            error: function(jqXHR, textStatus, errorThrown){
                alert(textStatus);
            }
		    
		});
/*
		$( "#roleCode" ).autocomplete({
			minLength:3, // numbers of chars before send a request
			delay:500, // deplay in ms
		    open: function() {	// generation de la pseudo liste d'autocomplete
		        $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
		    },
		    close: function() { //fermeture de la pseudo liste d'autocomplete
		        $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
		    },
		    source: function(request, response) {
		    	
		    	var applicationCode = $('#appCode').val();
		    	if (typeof(applicationCode) == "string" && applicationCode.trim()!="") {
			    	renderTxtSearch = request.term;
			        $.getJSON(
			        	"<c:url value="/admin-iam/json/role/find.do" />",
			        	{ "search": request.term ,
	                      "appCode": $('#appCode').val()
	                    },
			        	function(result) {
			                response($.map(result, function(item) {	// population des elements "$.ui" standardises
			                    return {
			                    	label: item.code + " - "+ item.description,	// libelle a presenter dans la combo
			                    	value: item.code,			// PK de l'element
			                    	id:item					// objet complet de l'element
			                    }
			                }));
			       	 	}
			        );
			     } else {
			         alert("<fmt:message key="msg.resourceValue.select.application"/>");
			         return false;
			     }
		    },
		    create: function() {
		        $(this).data('ui-autocomplete')._renderItem = renderItemOverride;
		    	return false;
		    },
			select: function(event, ui) {	// action sur selection
		 	    var selectRol = ui.item.id;
			   	$('#roleId').val(selectRol.id);
			   	$('#roleCode').val(selectRol.code);
			   	$('#roleDesc').val(selectRol.description);
			   
			     $('#roleId').attr("value", selectRol.id);
			   // submit form populate JsTree
			   $('#SearchResValForm').submit();
			   return false;
			},
            error: function(jqXHR, textStatus, errorThrown){
                alert(textStatus);
            }
		    
		});
*/


	    $.widget("ui.combobox", {
	        _create: function () {
	            var input,
	              that = this,
	              wasOpen = false,
	              select = this.element.hide(),
	              selected = select.children(":selected"),
	              defaultValue = selected.text() || "",
	              wrapper = this.wrapper = $("<span>")
	                .addClass("ui-combobox")
	                .insertAfter(select);
	
	            function removeIfInvalid(element) {
	                var value = $(element).val(),
	                  matcher = new RegExp("^" + $.ui.autocomplete.escapeRegex(value) + "$", "i"),
	                  valid = false;
	                select.children("option").each(function () {
	                    if ($(this).text().match(matcher)) {
	                        this.selected = valid = true;
	                        return false;
	                    }
	                });
	
	                if (!valid) {
	                    // remove invalid value, as it didn't match anything
	                    $(element).val(defaultValue);
	                    select.val(defaultValue);
	                    input.data("ui-autocomplete").term = "";
	                }
	            } // removeIfInvalid
	
	            input = $("<input>")
	              .appendTo(wrapper)
	              .val(defaultValue)
	              .attr("title", "")
	              .addClass("ui-state-default ui-combobox-input")
	              .width(select.width())
	              .autocomplete({
	                  delay: 0,
	                  minLength: 0,
	                  autoFocus: true,
	                  source: function (request, response) {
	                      var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
	                      response(select.children("option").map(function () {
	                          var text = $(this).text();
	                          if (this.value && (!request.term || matcher.test(text)))
	                              return {
	                                  label: text.replace(
	                                    new RegExp(
	                                      "(?![^&;]+;)(?!<[^<>]*)(" +
	                                      $.ui.autocomplete.escapeRegex(request.term) +
	                                      ")(?![^<>]*>)(?![^&;]+;)", "gi"
	                                    ), "<strong>$1</strong>"),
	                                  value: text,
	                                  option: this
	                              };
	                      }));
	                  },
	                  select: function (event, ui) {
	                      ui.item.option.selected = true;
	                      that._trigger("selected", event, {
	                          item: ui.item.option
	                      });

                var applicationCode = $('#appCode').val();
                document.location.href="<c:url value="/admin-iam/resource/values.do" />?appId="+ applicationCode +"&roleId="+ ui.item.option.value;
                return false;
                
 
	                  },
	                  change: function (event, ui) {
	                      if (!ui.item) {
	                          removeIfInvalid(this);
	                      }
	                  }
	              }) // autocomplete
	              .addClass("ui-widget ui-widget-content ui-corner-left");
	
	            input.data("ui-autocomplete")._renderItem = function (ul, item) {
	                return $("<li>")
	                  .append("<a>" + item.label + "</a>")
	                  .appendTo(ul);
	            };
	
	            $("<a>")
	              .attr("tabIndex", -1)
	              .appendTo(wrapper)
	              .button({
	                  icons: {
	                      primary: "ui-icon-triangle-1-s"
	                  },
	                  text: false
	              })
	              .removeClass("ui-corner-all")
	              .addClass("ui-corner-right ui-combobox-toggle")
	              .mousedown(function () {
	                  wasOpen = input.autocomplete("widget").is(":visible");
	              })
	              .click(function () {
	                  input.focus();
	                  // close if already visible
	                  if (wasOpen) {
	                      return;
	                  }
	
	                  // pass empty string as value to search for, displaying all results
	                  input.autocomplete("search", "");
	              });
	        },
	
	        _destroy: function () {
	            this.wrapper.remove();
	            this.element.show();
	        }
        });


        $( "#roleCode" ).combobox();

	});
});
