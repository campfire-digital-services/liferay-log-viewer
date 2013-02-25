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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.text.DecimalFormat;


/**
 * PortletPropsValues
 * 
 * @author Chun Ho <chun.ho@permeance.com.au>
 */
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
