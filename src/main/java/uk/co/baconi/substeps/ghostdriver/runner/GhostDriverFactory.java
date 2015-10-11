/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.baconi.substeps.ghostdriver.runner;

import static org.openqa.selenium.phantomjs.PhantomJSDriverService.*;

import com.technophobia.webdriver.substeps.runner.DriverType;
import com.technophobia.webdriver.substeps.runner.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.baconi.substeps.ghostdriver.properties.GhostDriverSubstepsConfiguration;

import java.util.Optional;

public class GhostDriverFactory implements WebDriverFactory {

    private static final Logger LOG = LoggerFactory.getLogger(GhostDriverFactory.class);

    private final Optional<WebDriverFactory> fallbackFactory;
    private final boolean phantomJsDriverRequested;

    public GhostDriverFactory() {

        final String driverType = GhostDriverSubstepsConfiguration.PROPERTIES.getDriverType();
        phantomJsDriverRequested = driverType != null && driverType.trim().equals("PHANTOMJS");

        if(phantomJsDriverRequested){
            fallbackFactory = Optional.empty();
        } else {
            final Class<? extends WebDriverFactory> fallback = GhostDriverSubstepsConfiguration.PROPERTIES.getFallbackFactory();
            try {
                fallbackFactory = Optional.of(fallback.newInstance());
            } catch (final InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("No fallback webdriver factory available.", e);
            }
        }

    }

    @Override
    public WebDriver createWebDriver() {
        if (phantomJsDriverRequested) {
            return createGhostDriver();
        } else {
            return fallbackFactory.get().createWebDriver();
        }
    }

    @Override
    public DriverType driverType() {
        if (phantomJsDriverRequested) {
            return () -> true;
        } else {
            return fallbackFactory.get().driverType();
        }
    }

    private WebDriver createGhostDriver() {

        final GhostDriverSubstepsConfiguration properties = GhostDriverSubstepsConfiguration.PROPERTIES;
        final DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();

        // GhostDriver extra Capabilities
        properties.getPhantomJsPageSettings().forEach((setting, value) -> {
            desiredCapabilities.setCapability(PHANTOMJS_PAGE_SETTINGS_PREFIX + setting, value);
        });
        properties.getPhantomJsPageCustomHeaders().forEach((setting, value) -> {
            desiredCapabilities.setCapability(PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + setting, value);
        });

        // PhantomJSDriver (Java-binding) Capabilities
        properties.getPhantomJsBinaryPath().ifPresent(s -> {
            desiredCapabilities.setCapability(PHANTOMJS_EXECUTABLE_PATH_PROPERTY, s);
        });
        properties.getPhantomJsBinaryCliArgs().ifPresent(s -> {
            desiredCapabilities.setCapability(PHANTOMJS_CLI_ARGS, s);
        });
        properties.getPhantomJsGhostDriverPath().ifPresent(s -> {
            desiredCapabilities.setCapability(PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY, s);
        });
        properties.getPhantomJsGhostDriverCliArgs().ifPresent(s -> {
            desiredCapabilities.setCapability(PHANTOMJS_GHOSTDRIVER_CLI_ARGS, s);
        });

        return new PhantomJSDriver(desiredCapabilities);
    }
}