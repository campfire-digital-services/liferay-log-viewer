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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.CharArrayWriter;
import java.io.Writer;

public class LogHolder {
    public static Log _log = LogFactoryUtil.getLog(LogHolder.class);
    private static LogRunnable runnable = null;
    private static Object writerAppenderObj = null;
    private static RollingLogViewer viewer = null;
    private static boolean attached = false;

    public static RollingLogViewer getViewer() {
        return viewer;
    }

    public static synchronized void attach() throws Exception {
        if (!isAttached()) {
            try {
                ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();
                Class logger = portalClassLoader.loadClass("org.apache.log4j.Logger");
                Object rootLoggerObj = logger.getMethod("getRootLogger").invoke(null);

                Class patternLayout = portalClassLoader.loadClass("org.apache.log4j.PatternLayout");

                String pattern = GetterUtil.getString(PropsUtil.get("permeance.log.viewer.pattern"), "%d{ABSOLUTE} %-5p [%c{1}:%L] %m%n");

                Object patternLayoutObj = patternLayout.getConstructor(String.class).newInstance(pattern);

                CharArrayWriter pwriter = new CharArrayWriter();
                viewer = new RollingLogViewer();
                runnable = new LogRunnable(pwriter, viewer);
                Thread t = new Thread(runnable);
                t.start();

                Class writerAppender = portalClassLoader.loadClass("org.apache.log4j.WriterAppender");

                Class appender = portalClassLoader.loadClass("org.apache.log4j.Appender");
                Class layout = portalClassLoader.loadClass("org.apache.log4j.Layout");
                writerAppenderObj = writerAppender.getConstructor(layout, Writer.class).newInstance(patternLayoutObj, pwriter);

                logger.getMethod("addAppender", appender).invoke(rootLoggerObj, writerAppenderObj);
                attached = true;
            } catch (Exception e) {
                _log.error(e);
                throw e;
            }
        }
    }

    public static synchronized void detach() {
        if (isAttached()) {
            try {
                runnable.setStop(true);

                ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();
                Class logger = portalClassLoader.loadClass("org.apache.log4j.Logger");
                Object rootLoggerObj = logger.getMethod("getRootLogger").invoke(null);
                Class appender = portalClassLoader.loadClass("org.apache.log4j.Appender");
                logger.getMethod("removeAppender", appender).invoke(rootLoggerObj, writerAppenderObj);
            } catch (Exception e) {
                _log.warn(e);
            }
        }
        runnable = null;
        viewer = null;
        writerAppenderObj = null;
        attached = false;
    }

    public static boolean isAttached() {
        return attached;
    }

    public static class LogRunnable implements Runnable {
        private final RollingLogViewer viewer;
        private final CharArrayWriter writer;
        private boolean stop = false;

        public LogRunnable(CharArrayWriter writer, RollingLogViewer viewer) {
            this.writer = writer;
            this.viewer = viewer;
        }

        public void setStop(boolean stop) {
            this.stop = stop;
        }

        public void run() {
            try {
                while (true) {
                    char[] buf = writer.toCharArray();
                    writer.reset();
                    if (buf.length > 0) {
                        viewer.write(buf, 0, buf.length);
                    }
                    if (stop) {
                        break;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
