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

package uk.co.baconi.substeps.ghostdriver;

import com.technophobia.substeps.model.SubSteps.Step;
import com.technophobia.substeps.model.SubSteps.StepImplementations;
import com.technophobia.webdriver.substeps.impl.AbstractWebDriverSubStepImplementations;
import com.technophobia.webdriver.substeps.runner.DefaultExecutionSetupTearDown;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;

@StepImplementations(requiredInitialisationClasses = DefaultExecutionSetupTearDown.class)
public class TakeScreenShotStepImpl extends AbstractWebDriverSubStepImplementations {

    private static final Logger LOG = LoggerFactory.getLogger(TakeScreenShotStepImpl.class);

    @Step("TakeAScreenShot called '([^']*)'")
    public void takeAScreenShot(final String screenShotName) {
        final byte[] screenshotBytes = getScreenshotBytes();

        if (screenshotBytes != null) {

            File screenshotFile = new File("target/", screenShotName + ".png");

            try {

                IOUtils.write(screenshotBytes, new FileOutputStream(screenshotFile));

            } catch (Exception e) {

                LOG.error("Unable to create screenshot", e);
            }

        }
    }

    @Step("LogCurrentPageSource as (TRACE|DEBUG|ERROR|WARN|INFO)")
    public void logCurrentPageSource(final String level) {
        switch (level) {
            case "TRACE":
                LOG.trace(webDriver().getPageSource());
                break;
            case "DEBUG":
                LOG.debug(webDriver().getPageSource());
                break;
            case "ERROR":
                LOG.error(webDriver().getPageSource());
                break;
            case "WARN":
                LOG.warn(webDriver().getPageSource());
                break;
            case "INFO":
                LOG.info(webDriver().getPageSource());
                break;
        }
    }
}
