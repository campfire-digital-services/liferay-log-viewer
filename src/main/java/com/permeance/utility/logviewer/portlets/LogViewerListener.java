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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * LogViewerListener
 * 
 * @author Chun Ho <chun.ho@permeance.com.au>
 * @see StartupServlet
 */
public class LogViewerListener implements ServletContextListener {

    private static final Log log = LogFactoryUtil.getLog(LogViewerListener.class);

    public void contextInitialized(final ServletContextEvent arg0) {
        startApplication();
    }

    public void contextDestroyed(final ServletContextEvent arg0) {
        stopApplication();
    }

    private static boolean appStarted = false;

    public static synchronized void startApplication() {
        if (!appStarted) {
            appStarted = true;
            log.info("Log Viewer Startup");

            try {
                final boolean autoAttach = GetterUtil.getBoolean(PropsUtil.get("permeance.log.viewer.autoattach"), true);

                if (autoAttach) {
                    log.info("Autoattaching logger");
                    LogHolder.attach();
                } else {
                    log.info("NOT autoattaching logger");
                }
            } catch (final Exception e) {
                log.error(e);
            }
        }
    }

    public static synchronized void stopApplication() {
        if (appStarted) {
            appStarted = false;
            LogHolder.detach();
            log.info("Log Viewer Shutdown");
        }
    }

}
