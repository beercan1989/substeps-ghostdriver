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

package uk.co.baconi.substeps.ghostdriver.properties;

import com.technophobia.webdriver.substeps.runner.WebDriverFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum GhostDriverSubstepsConfiguration {

    PROPERTIES("environment"); // uninstantiable

    /**
     * All properties under "substeps.driver", including environment specific if available.
     */
    private final Config properties;

    // GhostDriver Substeps Properties
    private final Class<? extends WebDriverFactory> fallbackFactory;
    private final String driverType;

    // GhostDriver extra Capabilities
    private final Map<String, Object> phantomJsPageSettings;
    private final Map<String, Object> phantomJsPageCustomHeaders;

    // PhantomJSDriver (Java-binding) Capabilities
    private final Optional<String> phantomJsBinaryPath;
    private final Optional<String> phantomJsBinaryCliArgs;
    private final Optional<String> phantomJsGhostDriverPath;
    private final Optional<String> phantomJsGhostDriverCliArgs;

    GhostDriverSubstepsConfiguration(final String environmentProperty) {

        //
        // All properties under "substeps.driver", including environment specific if available.
        //
        final Config systemProperties = ConfigFactory.systemProperties();
        if (systemProperties.hasPath(environmentProperty)) {
            final String environment = systemProperties.getString(environmentProperty);
            // Load properties using ${environment}.conf falling back on the normal Typesafe Config structure.
            properties = ConfigFactory.
                    parseResourcesAnySyntax(environment).
                    withFallback(ConfigFactory.load());
        } else {
            // Load properties without environment specific configuration.
            properties = ConfigFactory.load();
        }

        this.driverType = properties.getString("substeps.driver.webdriver.driverType");

        try {
            fallbackFactory = Class.forName(properties.getString("substeps.driver.ghost.fallback.factory")).asSubclass(WebDriverFactory.class);
        } catch (final ClassNotFoundException e) {
            throw new IllegalStateException("'substeps.driver.ghost.fallback.factory' is invalid.", e);
        }

        phantomJsPageSettings = propertyPathToMap(properties, "phantomjs.page.settings");
        phantomJsPageCustomHeaders = propertyPathToMap(properties, "phantomjs.page.customHeaders");
        phantomJsBinaryPath = getOptionalString(properties, "phantomjs.binary.path");
        phantomJsBinaryCliArgs = getOptionalString(properties, "phantomjs.cli.args");
        phantomJsGhostDriverPath = getOptionalString(properties, "phantomjs.ghostdriver.path");
        phantomJsGhostDriverCliArgs = getOptionalString(properties, "phantomjs.ghostdriver.cli.args");
    }

    public Config getProperties() {
        return properties;
    }

    public Class<? extends WebDriverFactory> getFallbackFactory() {
        return fallbackFactory;
    }

    public String getDriverType() {
        return driverType;
    }

    public Map<String, Object> getPhantomJsPageSettings() {
        return phantomJsPageSettings;
    }

    public Map<String, Object> getPhantomJsPageCustomHeaders() {
        return phantomJsPageCustomHeaders;
    }

    public Optional<String> getPhantomJsBinaryPath() {
        return phantomJsBinaryPath;
    }

    public Optional<String> getPhantomJsBinaryCliArgs() {
        return phantomJsBinaryCliArgs;
    }

    public Optional<String> getPhantomJsGhostDriverPath() {
        return phantomJsGhostDriverPath;
    }

    public Optional<String> getPhantomJsGhostDriverCliArgs() {
        return phantomJsGhostDriverCliArgs;
    }

    private Map<String, Object> propertyPathToMap(final Config properties, final String path) {
        if (properties.hasPath(path)) {
            return properties.getConfig(path).entrySet().stream().collect(
                    Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().unwrapped()
                    )
            );
        } else {
            return Collections.emptyMap();
        }
    }

    private Optional<String> getOptionalString(Config properties, final String path) {
        if (properties.hasPath(path)) {
            return Optional.ofNullable(properties.getString(path));
        } else {
            return Optional.empty();
        }
    }
}