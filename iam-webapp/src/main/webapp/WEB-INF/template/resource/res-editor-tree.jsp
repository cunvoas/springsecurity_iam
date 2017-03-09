<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <li class="active"><i class="fa fa-list-ul"></i> <fmt:message key="tree.resource.title" /></li>
        <li><i class="fa fa-list-ol"></i> <fmt:message key="tree.resource.titleVal" /></li>
    </ol>
</div>
<!-- /.row -->

<div class="row">
    <div class="col-lg-8">
        <button type="button" class="btn btn-primary" onclick="ress_create();">
            <i class="fa fa-plus"></i> <fmt:message key="form.resource.node.create" />
        </button>
        <button type="button" class="btn btn-info" onclick="ress_rename();">
            <i class="fa fa-pencil"></i> <fmt:message key="form.resource.node.rename" />
        </button>
        <button type="button" class="btn btn-warning" onclick="ress_delete();">
            <i class="fa fa-trash-o"></i> <fmt:message key="form.resource.node.delete" />
        </button>
    </div>
    <div class="col-lg-4" style="text-align: right;">
        <input type="text" value=""
            style="box-shadow: inset 0 0 4px #eee; width: 120px; margin: 0; padding: 6px 12px; border-radius: 4px; border: 1px solid silver; font-size: 1.1em;"
            id="searchTree" placeholder="Search" />
    </div>


    <div class="col-lg-12">
        <h4>
            <fmt:message key="tree.resource.title" />
        </h4>
        <center>
            <div class="clear error" id="errorMsg" />
        </center>
                        
        <div id="ressTree"></div>

        <script>

function ress_create() {
    var ref = $('#ressTree').jstree(true),
        sel = ref.get_selected();
    if(!sel.length) { return false; }
    sel = sel[0];
    sel = ref.create_node(sel, {"type":"file"});
    if(sel) {
        ref.edit(sel);
    }
};
function ress_rename() {
    var ref = $('#ressTree').jstree(true),
        sel = ref.get_selected();
    if(!sel.length) { return false; }
    sel = sel[0];
    ref.edit(sel);
};
function ress_delete() {
    var ref = $('#ressTree').jstree(true),
        sel = ref.get_selected();
    if(!sel.length) { return false; }
    ref.delete_node(sel);
};
        
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
    
    /* JSTree Events to monitor
     create_node.jstree
     rename_node.jstree
     delete_node.jstree
     move_node.jstree
    // set_text.jstree
     
     */
     
    function getTreeResource(jsTreeItem, resval) {
        var myId = jsTreeItem.node.id;
        if (typeof(jsTreeItem.node.id) == 'string' && jsTreeItem.node.id.startsWith("j")) {
            myId = -10001;
        }
        var treeResource = {
            "id": myId,
            "text": jsTreeItem.node.text,
            "parent": jsTreeItem.node.parent,
            "parentOrg": jsTreeItem.node.original.parent,
            "position": jsTreeItem.position,
            "attr" :{
                resourceText: jsTreeItem.node.text,
                resourceValue: resval
                }
            };
            //JSON.stringify(treeResource);
        return treeResource;
    }

    
    function srvPost(data, mvcMapping, oItem){
        $.ajax({
            type: "POST",
            url: window.location.origin + "<c:url value="/admin-iam/json/resource/" />"+mvcMapping+".do?appCode=${application.code}",
            data: JSON.stringify(oItem),
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
                    	if (mvcMapping=="renameTreeNode" && 
                    			typeof(data.node.id) == 'string' && data.node.id.startsWith("j")) {
                    		// fix click impossible after add
                    		location.reload();
                    	}
                    	
                        // set values updated by server
                        var newId = updatedData.id;
                        if (typeof(newId=='string') && data.node.id!=newId) {
                            data.node.id=newId;
                           // data.li_attr.id=newId;
                        }
                        var newParentId = updatedData.parent;
                        if (typeof(newParentId=='string') && data.node.parent!=newParentId) {
                            data.node.parent=newParentId;
                            data.node.original.parent=newParentId;
                        }
                        var newText = updatedData.text;
                        if (typeof(newId=='string') && data.node.text!=newText) {
                            data.node.text=newText;
                        }
                        
                    }
                    console.log( "Résultat: " + textStatus );
                },
            error: function (jqXHR, textStatus, errorThrown) {
                alert( "Error: " + textStatus );
                }
            });
        }
    
    function clientTreeProcess(data, strEvent) {
        console.log("action "+data.action+ ", strEvent="+strEvent);
        
        var oItem = getTreeResource(data, 0);
        if ( typeof(data.node) == "object" ) {
            console.log("\tid="+data.node.id);
            console.log("\tparentId="+data.node.parent);
            console.log("\ttext="+data.node.text);
            console.log("\tpos="+data.position);
    
            console.log("\tOrg id="+data.node.original.id);
            console.log("\tOrg parentId="+data.node.original.parent);
            console.log("\tOrg text="+data.node.original.text);
            
            if (data.node.icon == true) {
                $("#ressTree").jstree("set_icon", data.node, "fa fa-bookmark-o");
            }
            
            if (data.node.children.length==0 && data.node.icon=="fa fa-folder") {
                $("#ressTree").jstree("set_icon", data.node, "fa fa-bookmark-o");
                
            } else if (data.node.children.length>0 && data.node.icon=="fa fa-bookmark-o") {
                $("#ressTree").jstree("set_icon", data.node, "fa fa-folder");
                
            } else if (data.node.original.parent != data.node.parent) {
                console.log("PARENT CHANGED");
                // find new parent and check parent icon
                parentNode = $("#ressTree").jstree("get_node", data.node.parent);
                console.log("PARENT ID="+parentNode.id+" text="+parentNode.text);
                 if (typeof(parentNode) == 'object' && parentNode.children.length>0 && parentNode.icon=="fa fa-bookmark-o") {
                    console.log("PARENT CHANGED : LEAF BECAME FOLDER");
                     $("#ressTree").jstree("set_icon", parentNode, "fa fa-folder");
                 }
                 
                // find old parent and check parent icon
                oldParentNode = $("#ressTree").jstree("get_node", data.node.original.parent);
                console.log("OLD PARENT ID="+data.node.original.parent+","+oldParentNode.id+" text="+oldParentNode.text);
                 if (typeof(oldParentNode) == 'object' && oldParentNode.children.length==0 && oldParentNode.icon=="fa fa-folder") {
                    console.log("PARENT CHANGED : FOLDER BECAME LEAF");
                     $("#ressTree").jstree("set_icon", oldParentNode, "fa fa-bookmark-o");
                 }
                 
            }
                        
        }
        
        if (strEvent == 'moveTreeNode') {
            // the original node didn't be refresh avec move
            data.node.original.parent = data.node.parent;
        }
        return oItem;
    }
    
    
    $("#ressTree")
    .on('changed.jstree', function (e, data) {
        clientTreeProcess(data, "changed");
      })
    .on('create_node.jstree', function (e, data) {
        clientTreeProcess(data, "createTreeNode");
        })
    .on('rename_node.jstree', function (e, data) {
        var treeItem = clientTreeProcess(data, "renameTreeNode");
        // CALL server
        srvPost(data, "renameTreeNode", treeItem);
        })
    .on('delete_node.jstree', function (e, data) {
        var treeItem = clientTreeProcess(data, "deleteTreeNode");
        // CALL server
        srvPost(data, "deleteTreeNode", treeItem);
        })
    .on('move_node.jstree', function (e, data) {
        var treeItem = clientTreeProcess(data, "moveTreeNode");
        // CALL server
        srvPost(data, "moveTreeNode", treeItem);
        })
      
    .jstree({
        "plugins" : [ "unique", "dnd", "wholerow", "search", "types"],
        "core" : {
            "check_callback" : true,
            "multiple" : false,
            "data" : ${resourceTree}
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
             }
        
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
})
</script>

    </div>
    <!-- /.col-12 -->
</div>
<!-- /.row -->