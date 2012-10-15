/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package griffon.plugins.splash;

import griffon.core.GriffonApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * @author Andres Almiray
 */
public class DefaultSplashScreenProvider implements SplashScreenProvider {
    private Label status;

    public Scene getScene(GriffonApplication application) {
        BorderPane root = new BorderPane();
        root.setId("splashscreen");

        ImageView logo = new ImageView();
        logo.setId("logo");
        logo.setImage(new Image("griffon-icon-256x256.png"));
        BorderPane.setMargin(logo, new Insets(20, 20, 10, 20));
        BorderPane.setAlignment(logo, Pos.CENTER);
        root.setCenter(logo);

        status = new Label();
        status.setId("status");
        BorderPane.setMargin(status, new Insets(10, 20, 20, 20));
        BorderPane.setAlignment(status, Pos.BASELINE_LEFT);
        root.setBottom(status);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(application.getResourceAsURL("splashscreen.css").toString());
        return scene;
    }

    public void setMessage(String message) {
        status.setText(message);
    }
}
