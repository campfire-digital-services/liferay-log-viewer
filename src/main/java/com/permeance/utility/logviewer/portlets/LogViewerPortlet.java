/**
 * Copyright (C) 2013 Permeance Technologies
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */
package com.permeance.utility.logviewer.portlets;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * LogViewerPortlet
 * 
 * @author Chun Ho <chun.ho@permeance.com.au>
 */
public class LogViewerPortlet extends MVCPortlet {

    public static final Log log = LogFactoryUtil.getLog(LogViewerPortlet.class);

    /**
     * view method
     */
    @Override
    public void doView(final RenderRequest renderRequest, final RenderResponse renderResponse) throws IOException, PortletException {
        try {
            include("/html/portlet/log-viewer/view.jsp", renderRequest, renderResponse);
        } catch (final Exception e) {
            log.warn(e);
            include("/html/portlet/log-viewer/error.jsp", renderRequest, renderResponse);
        }
    }

    /**
     * serveResource method
     */
    @Override
    public void serveResource(final ResourceRequest resourceRequest, final ResourceResponse resourceResponse) {
        try {
            resourceResponse.setContentType("text/json");
            resourceResponse.addProperty(HttpHeaders.CACHE_CONTROL, "no-cache");

            final String cmd = resourceRequest.getParameter("cmd");

            if ("attach".equals(cmd)) {
                try {
                    LogHolder.attach();
                    final JSONObject obj = JSONFactoryUtil.createJSONObject();
                    obj.put("result", "success");
                    resourceResponse.getWriter().print(obj.toString());
                } catch (final Exception e) {
                    final StringWriter sw = new StringWriter();
                    final PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    pw.close();
                    sw.close();

                    final JSONObject obj = JSONFactoryUtil.createJSONObject();
                    obj.put("result", "error");
                    obj.put("error", e.toString());
                    obj.put("trace", sw.toString());

                    resourceResponse.getWriter().print(obj.toString());

                    log.error(e);
                }
            } else if ("detach".equals(cmd)) {
                LogHolder.detach();
                final JSONObject obj = JSONFactoryUtil.createJSONObject();
                obj.put("result", "success");
                resourceResponse.getWriter().print(obj.toString());
            } else {

                final int pointer = GetterUtil.getInteger(resourceRequest.getParameter("pointer"), -1);

                final RollingLogViewer viewer = LogHolder.getViewer();

                int curpointer = -1;
                String content = StringPool.BLANK;
                String mode = "detached";
                if (viewer != null) {
                    curpointer = viewer.getCurrentPointer();
                    content = new String(viewer.getBuffer(pointer, curpointer));
                    mode = "attached";
                }
                final JSONObject obj = JSONFactoryUtil.createJSONObject();
                obj.put("pointer", Integer.toString(curpointer));
                obj.put("content", content);
                obj.put("mode", mode);

                resourceResponse.getWriter().print(obj.toString());
            }
        } catch (Exception e) {
            log.warn(e);
        }
    }

}
