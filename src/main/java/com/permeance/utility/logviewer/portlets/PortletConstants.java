package com.permeance.utility.logviewer.portlets;

public class PortletConstants {
    public static final String DEFAULT_LOG_PATTERN = "%d{ABSOLUTE} %-5p [%c{1}:%L] %m%n";
    public static final boolean DEFAULT_AUTOATTACH = true;
    public static final long DEFAULT_REFRESH_INTERVAL = 2000l;
    public static final long DEFAULT_SLEEP_INTERVAL = 1000l;

    public static final String ADD_APPENDER = "addAppender";
    public static final String REMOVE_APPENDER = "removeAppender";
    public static final String GET_ROOT_LOGGER = "getRootLogger";
    public static final String LOG4J_APPENDER_CLASS = "org.apache.log4j.Appender";
    public static final String LOG4J_LOGGER_CLASS = "org.apache.log4j.Logger";
    public static final String LOG4J_WRITER_APPENDER_CLASS = "org.apache.log4j.WriterAppender";
    public static final String LOG4J_LAYOUT_CLASS = "org.apache.log4j.Layout";
    public static final String LOG4J_PATTERN_LAYOUT_CLASS = "org.apache.log4j.PatternLayout";

    public static final String MIME_TYPE_JSON = "text/json";
    public static final String NO_CACHE = "no-cache";

}
