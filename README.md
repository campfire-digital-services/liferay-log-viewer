# Liferay Log Viewer Portlet

*liferay-log-viewer-portlet*

This project provides a portlet that attaches to the Liferay Portal's log4j mechanism to provide a log view within the browser.


## Supported Products

* Liferay Portal 6.1 CE GA2 (6.1.1+)
* Liferay Portal 6.1 EE GA2 (6.1.20+)


## Downloads

The latest releases are available from [SourceForge](http://sourceforge.net/projects/permeance-apps/files/liferay-log-viewer/ "Liferay Log Viewer").

You can also download or install the portlet from [Liferay Marketplace](http://www.liferay.com/marketplace/-/mp/application/21793045?_7_WAR_osbportlet_backURL=%2Fmarketplace%2F-%2Fmp%2Fcategory%2F11232561 "Liferay Log Viewer")

## Usage

Administrators will see a "Log Viewer" portlet in the Server area of the Control Panel.
Other users can also be assigned permissions to see the Log Viewer Portlet.

![Log Viewer Portlet](/doc/images/log-viewer-screenshot.png "Log Viewer Portlet")

The page polls the log buffer every 2 seconds to update the page with the latest logs.
You can also attach or detach the logger from the portal log4j. (when detached, the portlet does not cause any overhead on portal operations).

Two portal properties can be set:
* *permeance.log.viewer.autoattach* to toggle whether the logger attaches automatically on startup, default "true".
* *permeance.log.viewer.pattern* to configure the log4j pattern, default "%d{ABSOLUTE} %-5p \[%c{1}:%L\] %m%n"


## Building

Step 1. Checkout source from GitHub project

    % git  clone  https://github.com/permeance/liferay-log-viewer

Step 2. Build and package

    % mvn  -U  clean  package

This will build "liferay-log-viewer-portlet-XXX.war" in the targets tolder.

NOTE: You will require JDK 1.6+ and Maven 3.


## Installation

### Liferay Portal + Apache Tomcat Bundle

eg.

Deploy "liferay-log-viewer-portlet-1.0.0.1.war" to "LIFERAY_HOME/deploy" folder.

## License

Liferay Log Viewer is available under GNU Public License version 3.0 (GPLv3). A copy of the license is attached in the code package.

## Project Team

* Chun Ho - chun.ho@permeance.com.au
