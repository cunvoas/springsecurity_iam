<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div class="row">
    <div class="col-lg-12">
        <h2>
            <fmt:message key="menu.resource.title" />
        </h2>
    </div>
    <ol class="breadcrumb">
        <li><i class="fa fa-list-ul"></i> <fmt:message key="tree.resource.title" /></li>
        <li class="active"><i class="fa fa-list-ol"></i> <fmt:message key="tree.resource.titleVal" /></li>
    </ol>
</div><!-- /.row -->

<form:form method="POST" id="SearchResValForm" name="SearchResValForm" modelAttribute="SearchResValForm" action="./values.do" role="form">
                            
        <div class="row">
            <div class="col-lg-12">
            <h4><fmt:message key="resource.application.title" /></h4>
            
               <spring:hasBindErrors name="SearchResValForm">
                   <center>
                       <div class="clear error">
                            <c:forEach items="${errors.globalErrors}" var="error"><%--  errors.allErrors or errors.globalErrors or errors.fieldErrors  --%>
                               <spring:message code="${error.defaultMessage}" /><br />
                            </c:forEach>
                       </div>
                   </center>
               </spring:hasBindErrors>
           
                <div>
                    <form:hidden path="appId" />
                    <form:input path="appCode" /> <form:errors path="appCode" cssClass="error" />
                    <form:input path="appDesc" disabled="disabled" readonly="readonly" cssStyle="width: 50em;"/>
                </div>
            </div>
            

        </div>
        <div class="row">
            <div class="col-lg-12">
            <h4><fmt:message key="resource.role.title" /></h4>
            <div class="ui-widget">
                <form:hidden path="roleId" />
                <form:select path="roleCode">
                    <form:option value=""></form:option>
				    <form:options items="${roles}" itemValue="id" itemLabel="codeAndDescription" />
				</form:select> <form:errors path="roleCode" cssClass="error" />
                <form:input path="roleDesc" disabled="disabled" readonly="readonly" cssStyle="width: 50em;"/>
            </div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-lg-8">
              <div id="selectPos">
                   <form:select path="valId" cssStyle="display: none;">
                     <form:options items="${resValRef}" itemValue="id" itemLabel="valeur" />
                   </form:select>
                   <div id="fadeout-wrapper">
                       <div id="fadeout-KO"><fmt:message key="async.save.ko"/></div> 
                       <div id="fadeout-OK"><fmt:message key="async.save.ok"/></div> 
                   </div>
               </div>
            </div>
              <div class="col-lg-4" >
                 <input type="text" value=""
                     style="box-shadow: inset 0 0 4px #eee; width: 120px; margin: 0; padding: 6px 12px; border-radius: 4px; border: 1px solid silver; font-size: 1.1em;"
                     id="searchTree" placeholder="Search" />
             </div>
        </div>
</form:form>
        


<div class="row">

    <div class="col-lg-12">
        <h4>
            <fmt:message key="tree.resource.titleVal" />
        </h4>

       <center>
            <div class="clear error" id="errorMsg" />
        </center>
                        
        <div id="ressTree"></div>

        <script>

$( document ).ready(function() {
        
    // add startsWith method on String object
    if (!String.prototype.startsWith) {
          Object.defineProperty(String.prototype, 'startsWith', {
            enumerable: false,
            configurable: false,
            writable: false,
            value: function (searchString, position) {
              position = position || 0;
              return this.indexOf(searchString, position) === position;
            }
          });
        }

    function srvPost(data, mvcMapping){
        <%--
        @RequestParam("idRes") String resId, 
        @RequestParam("idRole") String roleId, 
        @RequestParam("idValue") String valueId
        --%>
        

        }
    

    var memoData;
    function changeSelect() {
        if (memoData!=null) {
            
            var data=memoData;

            var newVal = $("#valId").val();
            var newTxt = $("#valId option:selected").text();
            var idRes = data.node.id;
                
            // ajax call
            $.ajax ({
                type: "GET",
                url: window.location.origin + 
                     "<c:url value="/admin-iam/json/resource/" />changeNodeValue.do"+
                     "?appCode=${application.code}"+
                     "&idRole=${role.id}"+
                     "&idRes="+idRes+
                     "&idValue="+newVal
                ,
                contentType: 'application/json',
                mimeType: 'application/json',
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/json");
                },
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: false,    //This will force requested pages not to be cached by the browser          
                //processData:false, //To avoid making query String instead of JSON
                success: function(updatedData, textStatus, jqXHR) {
                        if (typeof(updatedData)=='object') {
                             $('#fadeout-OK').show().delay(100).fadeOut(1000);
                             
                             
                            // set values updated by server
                            var newSrvId = updatedData.id;
                            var newSrvTxt = updatedData.valeur;
                          
                            // refresh text in tree
                            data.node.original.attr.resourceValue=newSrvId;
                            data.node.original.attr.resourceText=newSrvTxt;
                            
                            // refresh text in grid
                            // Dont work : $("#ressTree").jstree.plugins.grid.redraw_node(data.node, 2, false);
                            var txt = $("li#"+data.node.id+" :eq(5)").html(newSrvTxt);
                            console.log("new id="+ newSrvId );
                            console.log("new label="+ txt );
                            
                            // refresh jstree node
                            $("#ressTree").jstree("_node_changed", data.node);
                            
                           
                        }
                        console.log( "Résultat: " + textStatus );
                    },
                error: function (jqXHR, textStatus, errorThrown) {
                    $('#fadeout-KO').show().delay(100).fadeOut(1000);
                    alert( "Error: " + textStatus );
                    }
                });
            
            // clear select dans memoData variable
            /*
            $("#valId option[value='']").attr('selected', 'selected');
            memoData = null;
            */
        } else {
            alert("<fmt:message key="msg.resourceValue.select.resource" />");
        }
    }
    
    function populateSelect(data, mvcMapping){
        if (data.node.icon == "fa fa-bookmark-o") {
              // only if a leaf
            memoData = data;
             
            var opt = data.node.original.attr.resourceValue;
            
            console.log("resId="+ data.node.id );
            console.log("appId="+  $("#appId").val() );
            console.log("rolId="+  $("#roleId").val() );
            console.log("valId="+  opt );
        
            $("#valId option[value='"+opt+"']").attr('selected', 'selected');
        }
    }
    
    
    $("#ressTree")
    .on('changed.jstree', function (e, data) {
        populateSelect(data, "changeNodeValue");
        //srvPost(data, "changeNodeValue");
      })

    .jstree({
        "plugins" : [ "unique", "wholerow", "search", "types", "grid"],
        "core" : {
            "check_callback" : true,
            "multiple" : false
            <c:if test="${not empty resourceTree}">, "data" : ${resourceTree}</c:if>
        },
        grid: {
            columns: [
                {width: 300, header: "Ressources",title:"_DATA_"},
                {width: 150, value: "resourceText", width: 60, header: "Acréditation", title:"Acréditation", cellClass: "gridValue"}
            ],
            resizable:true
        },
         "cookies" : {
                save_opened : true,
                save_selected : true,
                auto_save : true
            },
            
        "types" : {
            "valid_children": ["root"],
                  "root" : {
                      "delete_node": false,
                  }
             },     
        
    });
    
 // add extra features
    var to = false;
    $('#searchTree').keyup(function () {
        if(to) { clearTimeout(to); }
        to = setTimeout(function () {
            var v = $('#searchTree').val();
            $('#ressTree').jstree(true).search(v);
        }, 250);
    });

    console.log( "document loaded" );
    <c:if test="${not empty resourceTree}">
    $('#valId').css("display", "block");
    $('#valId').change(changeSelect);
    </c:if>
    
    $('#fadeout-OK').show().delay(0).fadeOut(1);
    $('#fadeout-KO').show().delay(0).fadeOut(1);
})
</script>

    </div>
    <!-- /.col-12 -->
</div>
<!-- /.row -->