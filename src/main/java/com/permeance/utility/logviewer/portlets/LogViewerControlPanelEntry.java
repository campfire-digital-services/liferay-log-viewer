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

import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.ControlPanelEntry;

/**
 * LogViewerControlPanelEntry
 * 
 * @author Chun Ho <chun.ho@permeance.com.au>
 */
public class LogViewerControlPanelEntry implements ControlPanelEntry {

    public boolean isVisible(PermissionChecker permissionChecker, Portlet portlet) throws Exception {
        return permissionChecker.isCompanyAdmin() || permissionChecker.isOmniadmin();
    }

    public boolean isVisible(Portlet portlet, String category, ThemeDisplay themeDisplay) throws Exception {
        return isVisible(themeDisplay.getPermissionChecker(), portlet);
    }

}
