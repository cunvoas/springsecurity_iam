/*
 * jsTreeGrid 3.0.0
 * http://github.com/deitch/jstree-grid
 *
 * This plugin handles adding a grid to a tree to display additional data
 *
 * Licensed under the MIT license:
 *   http://www.opensource.org/licenses/mit-license.php
 * 
 * Works only with jstree "v3.0.0-alpha" and higher
 *
 * $Date: 2014-01-28 $
 * $Revision:  1.00 $
 */

/*jslint nomen:true */
/*global window,navigator, document, jQuery*/

(function ($) {
	"use strict";

	var htmlstripre = /<\/?[^>]+>/gi,
		SPECIAL_TITLE = "_DATA_",
		LEVELINDENT = 24,
		bound = false,
		renderAWidth = function(node,tree) {
			var depth, a = node.get(0).tagName.toLowerCase() === "a" ? node : node.children("a"),
			width = parseInt(tree.settings.grid.columns[0].width,10) + parseInt(tree.settings.grid.treeWidthDiff,10);
			// need to use a selector in jquery 1.4.4+
			depth = tree.get_node(node).parents.length;
			width = width - depth*LEVELINDENT;
			a.css({width: width, "vertical-align": "top", "overflow":"hidden","float":"left"});
		},
		renderATitle = function(node,t,tree) {
			var a = node.get(0).tagName.toLowerCase() === "a" ? node : node.children("a"), title, col = tree.settings.grid.columns[0];
			// get the title
			title = "";
			if (col.title) {
				if (col.title === SPECIAL_TITLE) {
					title = tree.get_text(t);
				} else if (t.attr(col.title)) {
					title = t.attr(col.title);
				}
			}
			// strip out HTML
			title = title.replace(htmlstripre, '');
			if (title) {
				a.attr("title",title);
			}
		},
		resizing_s = false,
		resizing_x = 0,
		resizing_i = null,
		resizing_c = $();

	$.jstree.defaults.grid = {
		width			: 25,
		columns			: [],
		treeClass		: "jstree-grid-col-0",
		defaultConf		: {"display" : "inline-block"},
		treeWidthDiff	: 0,
		resizable		: false
	};

	$.jstree.plugins.grid = function(options,parent) {
		this.init = function (el, options) {
			parent.init.call(this, el, options);
			this._prepare_headers();
			this.trigger("loaded_grid");
		};
		this.bind = function () {
			parent.bind.call(this);
			if (this.settings.grid.resizable) {
				this.element.on('loaded_grid.jstree', $.proxy(function () {
					this.element.closest('.jstree-grid-wrapper')
					.on("selectstart", ".jstree-grid-separator", function () { return false; })
					.on("mousedown", ".jstree-grid-separator", $.proxy(function (e) {
						resizing_s = $(e.currentTarget);
						resizing_x = e.clientX;
						resizing_i = this;
						resizing_c = resizing_s.prev();
						return false;
					}, this));
				}, this));
			}
		};
		this.teardown = function() {
			var gridparent = this.parent, container = this.element;
			container.detach();
			$("div.jstree-grid-wrapper",gridparent).remove();
			gridparent.append(container);
			parent.teardown.call(this);
		};
		this._prepare_headers = function() {
			var header, i, gs = this.settings.grid,cols = gs.columns || [], width, defaultWidth = gs.columnWidth, resizable = gs.resizable || false,
			cl, val, margin, last,
			container = this.element, gridparent = container.parent(), hasHeaders = 0,
			conf = gs.defaultConf, isClickedSep = false, oldMouseX = 0, newMouseX = 0, currentTree = null, colNum = 0, toResize = null, clickedSep = null, borPadWidth = 0;
			// save the original parent so we can reparent on destroy
			this.parent = gridparent;
			
			
			// set up the wrapper, if not already done
			header = this.header || $("<div></div>").addClass("jstree-grid-header jstree-grid-header-regular");
			
			// create the headers
			for (i=0;i<cols.length;i++) {
				cl = cols[i].headerClass || "";
				val = cols[i].header || "";
				if (val) {hasHeaders = true;}
				width = cols[i].width || defaultWidth;
				borPadWidth = 2+8; // account for the borders and padding
				width -= borPadWidth;
				margin = i === 0 ? 3 : 0;
				last = $("<div></div>").css(conf).css({"margin-left": margin,"width":width, "padding": "1 3 2 5"}).addClass("jstree-grid-header jstree-grid-header-cell jstree-grid-header-regular "+cl).text(val).appendTo(header)
					.after("<div class='jstree-grid-separator jstree-grid-separator-regular"+(resizable? " jstree-grid-resizable-separator":"")+"'>&nbsp;</div>");
			}
			if (last) {
				last.addClass("jstree-grid-header jstree-grid-header-regular");
			}
			// add a clearer
			$("<div></div>").css("clear","both").appendTo(header);
			// did we have any real columns?
			if (hasHeaders) {
				$("<div></div>").addClass("jstree-grid-wrapper").appendTo(gridparent).append(header).append(container);
				// save the offset of the div from the body
				gs.divOffset = header.parent().offset().left;
				gs.header = header;
			}
			this._data.grid.borPadWidth = borPadWidth;
		};
		/*
		 * Override redraw_node to correctly insert the grid
		 */
		this.redraw_node = function(obj, deep, is_callback) {
			// first allow the parent to redraw the node
			obj = parent.redraw_node.call(this, obj, deep, is_callback);
			// next prepare the grid
			if(obj) {
				this._prepare_grid(obj);
			}
			return obj;
		};
		this._prepare_grid = function (obj) {
			var gs = this.settings.grid, c = gs.treeClass, _this = this, t, cols = gs.columns || [], width,
			img, objData = this.get_node(obj),
			defaultWidth = gs.columnWidth, conf = gs.defaultConf, cellClickHandler = function (val,col,s) {
				return function() {
					$(this).trigger("select_cell.jstree-grid", [{value: val,column: col.header,node: $(this).closest("li"),sourceName: col.value,sourceType: s}]);
				};
			};
			// get our column definition
			t = $(obj);
			var i, val, cl, wcl, a, last, valClass, wideValClass, span, paddingleft, title, isAlreadyGrid, col, content, s, tmpWidth;
			
			// find the a children
			a = t.children("a");
			isAlreadyGrid = a.hasClass(c);
			
			if (a.length === 1) {
				a.prev().css("float","left");
				a.addClass(c);
				renderAWidth(a,_this);
				renderATitle(a,t,_this);
				last = a;
				for (i=1;i<cols.length;i++) {
					col = cols[i];
					s = col.source || "attr";
					// get the cellClass and the wideCellClass
					cl = col.cellClass || "";
					wcl = col.wideCellClass || "";


					// get the contents of the cell
					if (s === "attr") { val = col.value && objData.original.attr[col.value] ? objData.original.attr[col.value] : "";
					} else if (s === "metadata") { val = col.value && t.data(col.value) ? t.data(col.value) : ""; }

					// put images instead of text if needed
					if (col.images) {
					img = col.images[val] || col.images["default"];
					if (img) {content = img[0] === "*" ? '<span class="'+img.substr(1)+'"></span>' : '<img src="'+img+'">';}
					} else { content = val; }

					// get the valueClass
					valClass = col.valueClass && t.attr(col.valueClass) ? t.attr(col.valueClass) : "";
					if (valClass && col.valueClassPrefix && col.valueClassPrefix !== "") {
						valClass = col.valueClassPrefix + valClass;
					}
					// get the wideValueClass
					wideValClass = col.wideValueClass && t.attr(col.wideValueClass) ? t.attr(col.wideValueClass) : "";
					if (wideValClass && col.wideValueClassPrefix && col.wideValueClassPrefix !== "") {
						wideValClass = col.wideValueClassPrefix + wideValClass;
					}
					// get the title
					title = col.title && t.attr(col.title) ? t.attr(col.title) : "";
					// strip out HTML
					title = title.replace(htmlstripre, '');
					
					// get the width
					paddingleft = 7;
					width = col.width || defaultWidth;
					//tmpWidth = $.support.boxModel ? $(".jstree-grid-col-"+i+":first",t).width() : $(".jstree-grid-col-"+i+":first",t).outerWidth();
					width = tmpWidth || (width - paddingleft);
					
					last = isAlreadyGrid ? a.nextAll("div:eq("+(i-1)+")") : $("<div></div>").insertAfter(last);
					span = isAlreadyGrid ? last.children("span") : $("<span></span>").appendTo(last);

					// create a span inside the div, so we can control what happens in the whole div versus inside just the text/background
					span.addClass(cl+" "+valClass).css({"margin-right":"0px","display":"inline-block","*display":"inline","*+display":"inline"}).html(content)
					// add click handler for clicking inside a grid cell
					.click(cellClickHandler(val,col,s));
					last = last.css(conf).css({width: width,"padding-left":paddingleft+"px"}).addClass("jstree-grid-cell jstree-grid-cell-regular "+wcl+ " " + wideValClass).addClass("jstree-grid-col-"+i);
					
					if (title) {
						span.attr("title",title);
					}
				}
				last.addClass("jstree-grid-cell-last");
				$("<div></div>").css("clear","both").insertAfter(last);
			}
			this.element.css({'overflow-y':'auto !important'});
		};

		// need to do alternating background colors or borders
	};
	$(function () {
		var styles = [
			'.jstree-grid-cell {padding-left: 4px; vertical-align: top; overflow:hidden;}',
			'.jstree-grid-separator {display: inline-block; border-width: 0 2px 0 0; *display:inline; *+display:inline; margin-right:0px;float:left;width:0px;}',
			'.jstree-grid-header-cell {float: left;}',
			'.jstree-grid-header-regular {background-color: #EBF3FD;}',
			'.jstree-grid-resizable-separator {cursor: col-resize;}',
			'.jstree-grid-separator-regular {border-color: #d0d0d0; border-style: solid;}'
		];
		$('<style type="text/css">'+styles.join("\n")+'</style>').appendTo("head");
		$(document)
			.mouseup(function () {
				var i, cols, widths, headers;
				if(resizing_s) {
					cols		= resizing_i.settings.grid.columns;
					headers		= resizing_s.parent().children(".jstree-grid-header");
					widths		= {};
					if(!resizing_c.index()) {
						resizing_i.settings.grid.treeWidthDiff = resizing_i.element.find("ins:eq(0)").width() + resizing_i.element.find("a:eq(0)").width() - resizing_i.settings.grid.columns[0].width;
					}
					resizing_s = false;
					for (i=0;i<cols.length;i++) { widths[cols[i].header] = {w: parseFloat(headers[i].style.width)+resizing_i._data.grid.borPadWidth, r: i===resizing_c.index()/2 }; }
					resizing_i.trigger("resize_column.jstree-grid", [widths]);
				}
			})
			.mousemove(function (e) {
				if (resizing_s) {
					resizing_c.add(resizing_i.element.find('.jstree-grid-col-' + (resizing_c.index() / 2) )).each(function () { this.style.width = parseFloat(this.style.width) + (e.clientX - resizing_x) + "px"; });
					resizing_x = e.clientX;
				}
			});
	});
}(jQuery));
