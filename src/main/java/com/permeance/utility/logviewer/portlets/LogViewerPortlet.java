/**
 * Copyright (c) 2013 Permeance Technologies. All rights reserved.
 * 
 * This file is part of Log Viewer.
 * 
 * Log Viewer is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * Log Viewer is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Log Viewer. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package com.permeance.utility.logviewer.portlets;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

public class LogViewerPortlet extends MVCPortlet {
    public static Log _log = LogFactoryUtil.getLog(LogViewerPortlet.class);

    public static void checkPermissions(PortletRequest request) throws Exception {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();

        if (!permissionChecker.isOmniadmin() && !permissionChecker.isCompanyAdmin()) {
            throw new Exception("No permissions to execute this request");
        }
    }

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        try {
            LogViewerPortlet.checkPermissions(renderRequest);
            include("/html/portlet/log-viewer/view.jsp", renderRequest, renderResponse);
        } catch (Exception e) {
            _log.warn(e);
            include("/html/portlet/log-viewer/error.jsp", renderRequest, renderResponse);
        }
    }

    @Override
    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
        try {
            LogViewerPortlet.checkPermissions(resourceRequest);
            resourceResponse.setContentType("text/json");
            resourceResponse.addProperty(HttpHeaders.CACHE_CONTROL, "no-cache");

            String cmd = resourceRequest.getParameter("cmd");

            if ("attach".equals(cmd)) {
                try {
                    LogHolder.attach();
                    JSONObject obj = JSONFactoryUtil.createJSONObject();
                    obj.put("result", "success");
                    resourceResponse.getWriter().print(obj.toString());
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    pw.close();
                    sw.close();

                    JSONObject obj = JSONFactoryUtil.createJSONObject();
                    obj.put("result", "error");
                    obj.put("error", e.toString());
                    obj.put("trace", sw.toString());

                    resourceResponse.getWriter().print(obj.toString());

                    _log.error(e);
                }
            } else if ("detach".equals(cmd)) {
                LogHolder.detach();
                JSONObject obj = JSONFactoryUtil.createJSONObject();
                obj.put("result", "success");
                resourceResponse.getWriter().print(obj.toString());
            } else {

                int pointer = GetterUtil.getInteger(resourceRequest.getParameter("pointer"), -1);

                RollingLogViewer viewer = LogHolder.getViewer();

                int curpointer = -1;
                String content = StringPool.BLANK;
                String mode = "detached";
                if (viewer != null) {
                    curpointer = viewer.getCurrentPointer();
                    content = new String(viewer.getBuffer(pointer, curpointer));
                    mode = "attached";
                }
                JSONObject obj = JSONFactoryUtil.createJSONObject();
                obj.put("pointer", Integer.toString(curpointer));
                obj.put("content", content);
                obj.put("mode", mode);

                resourceResponse.getWriter().print(obj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
