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
import griffon.core.UIThreadManager;
import griffon.util.ApplicationClassLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import java.net.URL;

/**
 * @author Andres Almiray
 */
public class SplashScreen {
    private static SplashScreen INSTANCE;
    private static final Object LOCK = new Object[0];

    private Stage stage;
    private SplashScreenProvider splashScreenProvider;

    public static void show(final GriffonApplication app) {
        synchronized (LOCK) {
            UIThreadManager.getInstance().executeSync(new Runnable() {
                public void run() {
                    if (null == INSTANCE) createSplashScreen(app);
                    INSTANCE.stage.show();
                }
            });
        }
    }

    public static void hide() {
        synchronized (LOCK) {
            UIThreadManager.getInstance().executeAsync(new Runnable() {
                public void run() {

                    if (null != INSTANCE) INSTANCE.stage.hide();
                    INSTANCE = null;
                }
            });
        }
    }

    public static void updateStatus(final String message) {
        synchronized (LOCK) {
            if (null == INSTANCE) return;
            UIThreadManager.getInstance().executeAsync(new Runnable() {
                public void run() {
                    INSTANCE.splashScreenProvider.setMessage(message);
                }
            });
        }
    }

    private static void createSplashScreen(GriffonApplication app) {
        INSTANCE = new SplashScreen(app);
        app.addApplicationEventListener(INSTANCE);
    }

    private SplashScreen(GriffonApplication app) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(createScene(app));
        stage.sizeToScene();
    }

    private Scene createScene(GriffonApplication app) {
        if (null == splashScreenProvider) {
            initSplashScreenProvider();
        }
        return splashScreenProvider.getScene(app);
    }

    public void onWindowShown(Window window) {
        if (window != stage) stage.toFront();
    }

    public void onReadyEnd(GriffonApplication app) {
        hide();
        app.removeApplicationEventListener(this);
    }

    private void initSplashScreenProvider() {
        ClassLoader classLoader = ApplicationClassLoader.get();
        try {
            URL url = classLoader.getResource("META-INF/services/" + SplashScreenProvider.class.getName());
            String className = DefaultGroovyMethods.getText(url).trim();
            splashScreenProvider = (SplashScreenProvider) classLoader.loadClass(className).newInstance();
        } catch (Exception e) {
            splashScreenProvider = new DefaultSplashScreenProvider();
        }
    }
}