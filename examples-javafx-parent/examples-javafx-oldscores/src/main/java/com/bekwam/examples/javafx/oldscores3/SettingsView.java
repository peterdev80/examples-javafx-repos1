/*
 * Copyright 2015 Bekwam, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bekwam.examples.javafx.oldscores3;

import com.bekwam.examples.javafx.oldscores3.data.SettingsDAO;
import com.bekwam.examples.javafx.oldscores3.data.SettingsDAOImpl;
import com.bekwam.jfxbop.guice.GuiceBaseView;
import com.bekwam.jfxbop.view.Viewable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Dialog for Settings screen
 *
 * @author carl_000
 */
@Viewable(
        fxml= "/fxml3/Settings.fxml",
        stylesheet = "/styles.css",
        title = "Pre-1995 and Recentered Scores"
)
public class SettingsView extends GuiceBaseView {

    private final Logger logger = LoggerFactory.getLogger(ScoresView.class);

    @Inject
    SettingsDAO settingsDAO;

    @Inject
    NavigationDelegate navigationDelegate;

    @Inject
    Provider<SettingsViewController> svControllerProvider;

    @Override
    public void show() throws Exception {

        super.show();

        if( stage == null ) {
            init();
        }

        if( !stage.isShowing() ) {
            if( logger.isDebugEnabled() ) {
                logger.debug("[SHOW] stage is not showing");
            }

            ((SettingsDAOImpl)settingsDAO).markForRefresh();

            stage.show();
        }
    }

    @Override
    protected void postInit() {

        scene.setOnKeyPressed(evt -> {

            if( evt.getCode().equals(KeyCode.F1) ) {
                try {
                    if( logger.isDebugEnabled() ) {
                        logger.debug("[OPEN HELP]");
                    }
                    navigationDelegate.openHelpDialog();
                } catch (Exception exc) {
                    String msg = "error showing help dialog";
                    logger.error(msg);
                    Alert alert = new Alert(Alert.AlertType.ERROR, msg);
                    alert.showAndWait();
                }
            }
        });

    }
}
