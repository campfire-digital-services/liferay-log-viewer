package com.permeance.utility.logviewer.portlets;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.text.DecimalFormat;

public class PortletPropsValues {

    public static final String PERMEANCE_LOG_VIEWER_PATTERN = GetterUtil.getString(
            PropsUtil.get(PortletPropsKeys.PERMEANCE_LOG_VIEWER_PATTERN), PortletConstants.DEFAULT_LOG_PATTERN);

    public static final boolean PERMEANCE_LOG_VIEWER_AUTOATTACH_ENABLED = GetterUtil.getBoolean(
            PropsUtil.get(PortletPropsKeys.PERMEANCE_LOG_VIEWER_AUTOATTACH_ENABLED), PortletConstants.DEFAULT_AUTOATTACH);

    public static final long PERMEANCE_LOG_VIEWER_REFRESH_INTERVAL = GetterUtil.getLong(
            PropsUtil.get(PortletPropsKeys.PERMEANCE_LOG_VIEWER_REFRESH_INTERVAL), PortletConstants.DEFAULT_REFRESH_INTERVAL);

    public static final long PERMEANCE_LOG_VIEWER_SLEEP_INTERVAL = GetterUtil.getLong(
            PropsUtil.get(PortletPropsKeys.PERMEANCE_LOG_VIEWER_SLEEP_INTERVAL), PortletConstants.DEFAULT_SLEEP_INTERVAL);

    public static final String PERMEANCE_LOG_VIEWER_REFRESH_INTERVAL_DISPLAY_SECONDS = (new DecimalFormat("##0.0#"))
            .format(PERMEANCE_LOG_VIEWER_REFRESH_INTERVAL / 1000d);
}
