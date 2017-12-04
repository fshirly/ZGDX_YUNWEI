/*
 * jquery sDashboard (2.5)
 * Copyright 2012, Model N, Inc
 * Distributed under MIT license.
 * https://github.com/ModelN/sDashboard
 */

( function(factory) {"use strict";
		if ( typeof define === 'function' && define.amd) {
			// Register as an AMD module if available...
			define(['jquery', 'Flotr'], factory);
		} else {
			// Browser globals for the unenlightened...
			factory($, Flotr);
		}
	}(function($, Flotr) {"use strict";

		$.widget("mn.sDashboard", {
			version : "2.5",
			options : {
				dashboardData : []
			},
			_create : function() {
				this.element.addClass("sDashboard");
				this._createView();

			},
			_setOption : function(key, value) {
				this.options[key] = value;
				if (key === "dashboardData") {
					this._createView();
				}
			},

			_createView : function() {
				
				//var docHeight = $(document).height();

				$("body").append("<div class='sDashboard-overlay'></div>");

				$(".sDashboard-overlay").height('100%');

				$(".sDashboard-overlay").hide();
				
				var _dashboardData = this.options.dashboardData;
				var i;
				for ( i = 0; i < _dashboardData.length; i++) {
					_dashboardData[i].widgetId = _dashboardData[i].widgetName;
					var widget = this._constructWidget(_dashboardData[i]);
					//append the widget to the dashboard
					this.element.append(widget);
					this._renderTable(_dashboardData[i]);
					this._renderChart(_dashboardData[i]);
				}

				var that = this;
				//call the jquery ui sortable on the columns
				this.element.sortable({
					handle : ".sDashboardWidgetHeader",
					update : function(event, ui) {
						var sortOrderArray = $(this).sortable('toArray');
						var sortedDefinitions = [];
						for ( i = 0; i < sortOrderArray.length; i++) {
							var widgetContent = that._getWidgetContentForId(sortOrderArray[i], that);
							sortedDefinitions.push(widgetContent);
						}

						if (sortedDefinitions.length > 0) {
							var evtData = {
								sortedDefinitions : sortedDefinitions
							};
							that._trigger("orderchanged", null, evtData);
						}

					}
				});

				var disableSelection = this.options.hasOwnProperty("disableSelection") ? this.options.disableSelection : true;
				if (disableSelection) {
					this.element.disableSelection();
				}
				//bind events for widgets
				this._bindEvents();

				//trigger creation complete when the dashboard widgets are constructed
				this._trigger("creationComplete", null);

			},
			_getWidgetContentForId : function(id, context) {
				var widgetData = context.getDashboardData();
				for (var i = 0; i < widgetData.length; i++) {
					var widgetObject = widgetData[i];
					if (widgetObject.widgetId === id) {
						return widgetObject;
					}
				}
				return [];
			},
			_bindEvents : function() {
				var self = this;
				//click event for maximize button
				this.element.on("click", ".sDashboardWidgetHeader div.sDashboard-icon.sDashboard-circle-plus-icon", function(e) {

					//get the widget List Item Dom
					var widgetListItem = $(e.currentTarget).parents("li:first");
					//get the widget Container
					var widget = $(e.currentTarget).parents(".sDashboardWidget:first");
					//get the widget Content
					var widgetContainer = widget.find(".sDashboardWidgetContent");

					var widgetDefinition = self._getWidgetContentForId(widgetListItem.attr("id"), self);

					//toggle the maximize icon into minimize icon
					$(e.currentTarget).toggleClass("sDashboard-circle-minus-icon");
					//change the tooltip on the maximize/minimize icon buttons
					if ($(e.currentTarget).attr("title") === "Maximize") {
						$(".sDashboard-overlay").show();
						$(e.currentTarget).attr("title", "Minimize");
						widget.css('height','80%');
						self._trigger("widgetMaximized", null, {
							"widgetDefinition" : widgetDefinition
						});
					} else {
						$(".sDashboard-overlay").hide();
						$(e.currentTarget).attr("title", "Maximize");
						widget.css('height','100%');
						self._trigger("widgetMinimized", null, {
							"widgetDefinition" : widgetDefinition
						});
					}

					//toggle the class for widget and inner container
					widget.toggleClass("sDashboardWidgetContainerMaximized");
					widgetContainer.toggleClass("sDashboardWidgetContentMaximized ");
					//放大或缩小之后重新加载，以适应页面大小
					var widgetFrame = widgetContainer.find('#widgetFrame');
					widgetFrame.attr('src',widgetFrame.attr('src'));
				});

				//refresh widget click event handler
				this.element.on("click", ".sDashboardWidgetHeader div.sDashboard-icon.sDashboard-refresh-icon", function(e) {
					var widget = $(e.currentTarget).parents("li:first");
					var widgetId = widget.attr("id");
					var widgetDefinition = self._getWidgetContentForId(widgetId, self);
					//var refreshedData = widgetDefinition.refreshCallBack.apply(self, [widgetId]);
					//widgetDefinition.widgetContent = refreshedData;
					if (widgetDefinition.widgetType === 'chart') {
						self._renderChart(widgetDefinition);
					} else if (widgetDefinition.widgetType === 'table') {
						self._refreshTable(widgetDefinition, widget);
					} else {
						self._refreshRegularWidget(widgetDefinition, widget);
					}

				});

				//delete widget by clicking the 'x' icon on the widget
				this.element.on("click", ".sDashboardWidgetHeader div.sDashboard-icon.sDashboard-circle-remove-icon ", function(e) {
					var widget = $(e.currentTarget).parents("li:first");
					var widgetId = widget.attr("id");
					//show hide effect
					widget.hide("fold", {}, 300);
					widget.remove();
					self._removeWidgetFromWidgetDefinitions(widgetId);
					$(".sDashboard-overlay").hide();
				});

				//table row click
				this.element.on("click", ".sDashboardWidgetContent table.sDashboardTableView tbody tr", function(e) {
					var selectedRow = $(e.currentTarget);

					if (selectedRow.length > 0) {
						var selectedDataTable = selectedRow.parents('table:first').dataTable();

						var selectedWidget = selectedRow.parents("li:first");
						var selectedRowData = selectedDataTable.fnGetData(selectedRow[0]);
						var selectedWidgetId = selectedWidget.attr("id");
						var evtData = {
							selectedRowData : selectedRowData,
							selectedWidgetId : selectedWidgetId
						};

						//trigger dashboardTableViewRowClick changed event
						self._trigger("rowclicked", null, evtData);
					}
				});
			},

			_constructWidget : function(widgetDefinition) {
				//create an outer list item
				var widget = $("<li/>").attr("id", widgetDefinition.widgetId);
				//设置部件的宽和高
				widget.css('width',widgetDefinition.widgetWidth + 'px').css('height',widgetDefinition.widgetHeight + 'px');
				//create a widget container
				var widgetContainer = $("<div/>").addClass("sDashboardWidget");
				widgetContainer.css('height','100%');
				//create a widget header
				var widgetHeader = $("<div/>").addClass("sDashboardWidgetHeader sDashboard-clearfix");
				var maximizeButton = $('<div title="Maximize" class="sDashboard-icon sDashboard-circle-plus-icon "></span>');

				var deleteButton = $('<div title="Close" class="sDashboard-icon sDashboard-circle-remove-icon"></div>');

				//add delete button
				widgetHeader.append(deleteButton);
				//add Maximizebutton
				//widgetHeader.append(maximizeButton);

				/**
				if (widgetDefinition.hasOwnProperty("enableRefresh") && widgetDefinition.enableRefresh == "true") {
					var refreshButton = $('<div title="Refresh" class="sDashboard-icon sDashboard-refresh-icon "></div>');
					//add refresh button
					widgetHeader.append(refreshButton);
				}
				*/
				//IT运维系统要求所有dashboard默认具有refresh功能
				var refreshButton = $('<div title="Refresh" class="sDashboard-icon sDashboard-refresh-icon "></div>');
				//add refresh button
				widgetHeader.append(refreshButton);
	
				//add widget title
				widgetHeader.append(widgetDefinition.widgetTitle);

				//create a widget content
				var widgetContent = $("<div/>").addClass("sDashboardWidgetContent").css('height','100%');

				if (widgetDefinition.widgetType === 'table') {					
					var dataTable = $('<table cellpadding="0" cellspacing="0" border="0" class="display sDashboardTableView table table-bordered"></table>');
					widgetContent.append(dataTable);
				} else if (widgetDefinition.widgetType == 1) 
				{
					//定制URL功能
					var iFrame = $("<iframe id='"+ widgetDefinition.widgetId +"_frame' allowTransparency='true' width='100%' height='100%' style='border:0px'/>").attr("src",getRootName() + widgetDefinition.widgetUrl);
					iFrame.attr('frameborder','0',0);
					widgetContent.append(iFrame);
				}
				else {
					widgetContent.append(widgetDefinition.widgetContent);
				}

				//add widgetHeader to widgetContainer
				widgetContainer.append(widgetHeader);
				//add widgetContent to widgetContainer
				widgetContainer.append(widgetContent);

				//append the widgetContainer to the widget
				widget.append(widgetContainer);

				//return widget
				return widget;
			},
			_refreshRegularWidget : function(widgetDefinition, widget) {
				var isMaximized = widget.find(".sDashboardWidgetContent").hasClass('sDashboardWidgetContentMaximized');
				//first remove the content
				widget.find('.sDashboardWidgetContent').empty().remove();
				//then create the content again
				var widgetContent = $("<div/>").addClass("sDashboardWidgetContent").css('height','100%');
				//if its maximized add the maximized class
				if (isMaximized) {
					widgetContent.addClass('sDashboardWidgetContentMaximized');
				}
				if (widgetDefinition.widgetType == 1)
				{
					//定制URL功能
					var iFrame = $("<iframe id='"+ widgetDefinition.widgetId +"' allowTransparency='true' width='100%' height='100%' style='border:0px'/>").attr("src",getRootName() + widgetDefinition.widgetUrl);
					iFrame.attr('frameborder','0',0);
					widgetContent.append(iFrame); 
				}
				else
				{
					widgetContent.append(widgetDefinition.widgetContent);
				}
				//then append this to the widget again;
				widget.find(".sDashboardWidget").append(widgetContent);
			},
			_refreshTable : function(widgetDefinition, widget) {
				var selectedDataTable = widget.find('table:first').dataTable();
				selectedDataTable.fnClearTable();
				selectedDataTable.fnAddData(widgetDefinition.widgetContent["aaData"]);
				
			},
			_renderTable : function(widgetDefinition){
				var id = "li#" + widgetDefinition.widgetId;
				var table;
				if(widgetDefinition.widgetType === 'table'){
					table = this.element.find(id + " table.sDashboardTableView");

					var tableDef = {};
					$.extend(tableDef,widgetDefinition.widgetContent);
					
					if (widgetDefinition.setJqueryStyle) {
						tableDef["bJQueryUI"] = true;
					}
					table.dataTable(tableDef);
				}
			},
			_renderChart : function(widgetDefinition) {
				var id = "li#" + widgetDefinition.widgetId;
				var chartArea;
				var data;
				var options;

				if (widgetDefinition.widgetType === 'chart') {
					chartArea = this.element.find(id + " div.sDashboardChart");
					//请求URL
					var widgetUrl = widgetDefinition.widgetUrl;
					if (widgetUrl)
					{
						var outer = this;
						//请求图表数据
						$.get(getRootName() + widgetUrl)
							.done(function(response){
								data = response.data;
								options = response.options;
								//按照原始的API组装数据，这样可以沿用之前的方法
								widgetDefinition = {
									widgetId : widgetDefinition.widgetId,
									widgetTitle : widgetDefinition.widgetTitle,
									widgetType : widgetDefinition.widgetType,
									getDataBySelection : widgetDefinition.getDataBySelection,
									widgetContent : {
										data : data,
										options : options
									}
								};
								
								Flotr.draw(chartArea[0], data, options);
								if (widgetDefinition.getDataBySelection) {
									outer._bindSelectEvent(chartArea[0], widgetDefinition.widgetId, widgetDefinition, outer);
								} else {
									outer._bindChartEvents(chartArea[0], widgetDefinition.widgetId, widgetDefinition, outer);
								}
							});
					}
					else
					{
						data = widgetDefinition.widgetContent.data;
						options = widgetDefinition.widgetContent.options;
						Flotr.draw(chartArea[0], data, options);
						if (widgetDefinition.getDataBySelection) {
							this._bindSelectEvent(chartArea[0], widgetDefinition.widgetId, widgetDefinition, this);
						} else {
							this._bindChartEvents(chartArea[0], widgetDefinition.widgetId, widgetDefinition, this);
						}
					}
				}

			},
			_bindSelectEvent : function(chartArea, widgetId, widgetDefinition, context) {
				Flotr.EventAdapter.observe(chartArea, "flotr:select", function(area) {
					var evtObj = {
						selectedWidgetId : widgetId,
						chartData : area
					};
					context._trigger("plotselected", null, evtObj);
				});
			},
			_bindChartEvents : function(chartArea, widgetId, widgetDefinition, context) {

				Flotr.EventAdapter.observe(chartArea, 'flotr:click', function(d) {
					//only if a series is clicked dispatch a click event
					if (d.index !== undefined && d.seriesIndex !== undefined) {
						var evtObj = {};
						evtObj.selectedWidgetId = widgetId;
						evtObj.flotr2GeneratedData = d;
						var widgetData = widgetDefinition.widgetContent.data;
						var seriesData = widgetData[d.seriesIndex];
						var selectedData;

						if ($.isArray(seriesData)) {
							selectedData = seriesData[d.index];
						} else {
							selectedData = seriesData;
						}

						evtObj.customData = {
							index : d.index,
							selectedIndex : d.seriesIndex,
							seriesData : seriesData,
							selectedData : selectedData
						};
						context._trigger("plotclicked", null, evtObj);
					}
				});

			},
			_removeWidgetFromWidgetDefinitions : function(widgetId) {
				var widgetDefs = this.options.dashboardData;
				for (var i in widgetDefs) {
					var currentWidgetId = widgetDefs[i].widgetId;
					if (currentWidgetId === widgetId) {
						widgetDefs.splice(i, 1);
						break;
					}
				}
			},

			_ifWidgetAlreadyExists : function(widgetId) {
				if (!widgetId) {
					throw "Expected widgetId to be defined";
				}
				var idSelector = "#" + widgetId;
				//get the dom element
				var widget = this.element.find("li" + idSelector);
				if (widget.length > 0) {
					return true;
				}
				return false;
			},

			/*public methods*/
			//add a widget to the dashbaord
			addWidget : function(widgetDefinition) {
				if (!widgetDefinition.widgetId) {
					throw "Expected widgetId to be defined";
				}

				if (this._ifWidgetAlreadyExists(widgetDefinition.widgetId)) {
					this.element.find("li#" + widgetDefinition.widgetId).effect("shake", {
						times : 3
					}, 800);
				} else {
					this.options.dashboardData.push(widgetDefinition);
					var widget = this._constructWidget(widgetDefinition);
					this.element.prepend(widget);
					this._renderChart(widgetDefinition);
					this._renderTable(widgetDefinition);
				}
			},
			//remove a widget from the dashboard
			removeWidget : function(widgetId) {
				if (!widgetId) {
					throw "Expected widgetId to be defined";
				}
				var idSelector = "#" + widgetId;
				//get the dom element
				var widget = this.element.find("li" + idSelector);
				if (widget.length > 0) {
					//delete the dom element
					this.element.find("li" + idSelector).remove();
					//remove the dom element from the widgetDefinition
					this._removeWidgetFromWidgetDefinitions(widgetId);
				}
			},

			//get the wigetDefinitions
			getDashboardData : function() {
				return this.options.dashboardData;
			},
			destroy : function() {
				//remove the overlay when the dashbaord is destroyed
				$(".sDashboard-overlay").remove();
				// call the base destroy function
				$.Widget.prototype.destroy.call(this);
			}
		});

	}));

