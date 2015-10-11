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

import com.technophobia.substeps.runner.setupteardown.Annotations.AfterAllFeatures;
import com.technophobia.substeps.runner.setupteardown.Annotations.BeforeAllFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GhostDriverSetupAndTearDown {

    private static final Logger logger = LoggerFactory.getLogger(GhostDriverSetupAndTearDown.class);

    @BeforeAllFeatures
    public void beforeAllFeatures() {

        logger.info("GhostDriver - Before All Features - I don't actually do anything yet.");

    }

    @AfterAllFeatures
    public void afterAllFeatures() {

        logger.info("GhostDriver - After All Features - I don't actually do anything yet.");

    }
}