<%--
/**
 * Copyright (c) 2013 Permeance Technologies. All rights reserved.
 * 
 * This file is part of Log Viewer.
 * 
 * Log Viewer is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * Log Viewer is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Log Viewer.
 * If not, see <http://www.gnu.org/licenses/>.
 */
--%>
<%@ include file="init.jsp"%>
<%@page import="com.permeance.utility.logviewer.portlets.LogHolder"%>

<liferay-ui:success key="success"
	message="ui-request-processed-successfully" />
<liferay-ui:error key="error"
	message="ui-request-processed-error" />

<script type='text/javascript'>

	window.resourcePointer = "-1";
	function poll() {
	    var resourceMappingUrl = '<portlet:resourceURL/>';
		AUI().use('aui-io-request', function(A){  
			A.io.request(resourceMappingUrl, {
			    method: 'POST', data: {
			        "pointer": window.resourcePointer
			    },
			    dataType: 'json',
			    on: {
			        success: function() {
			            window.resourcePointer = this.get('responseData').pointer;
			            document.getElementById("viewlog").innerHTML = document.getElementById("viewlog").innerHTML + this.get('responseData').content;
			            document.getElementById("viewlogmode").innerHTML = this.get('responseData').mode;
			        }
			    }
			});
		});
	}

	function detachlogger() {
	 	return sendCmd('detach');   
	}
	
	function attachlogger() {
	 	return sendCmd('attach');   
	}
	
	function sendCmd(mycmd) {
	    var resourceMappingUrl = '<portlet:resourceURL/>';
		AUI().use('aui-io-request', function(A){  
			A.io.request(resourceMappingUrl, {
			    method: 'POST', data: {
			        "cmd": mycmd
			    },
			    dataType: 'json',
			    on: {
			        success: function() {
			            var result = this.get('responseData').result;
			            if(result == 'error') {
							alert(this.get('responseData').error);			                
			            }
			        }
			    }
			});
		});
	}
	
	
	var t = setInterval(poll, 2000);
</script>

<liferay-ui:message key="the-logger-is-currently"/> <span id="viewlogmode"><liferay-ui:message key="waiting-for-status"/></span>. <liferay-ui:message key="polling-every-2-seconds"/><br/>
<input type="button" onClick="attachlogger(); return false;" value="<liferay-ui:message key="attach-logger"/>" />&nbsp;&nbsp;<input type="button" onClick="detachlogger(); return false;" value="<liferay-ui:message key="detach-logger"/>" /><br/>
<em><liferay-ui:message key="you-can-set-portal-property"/> <b>permeance.log.viewer.autoattach</b> <liferay-ui:message key="autoattach-description"/></em><br/>
<em><liferay-ui:message key="you-can-set-portal-property"/> <b>permeance.log.viewer.pattern</b> <liferay-ui:message key="pattern-description"/></em><br/><br/>

<pre id="viewlog">

</pre>

<br/>
<br/>

