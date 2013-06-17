
JavaFX SplashScreen
-------------------

Plugin page: [http://artifacts.griffon-framework.org/plugin/splash-javafx](http://artifacts.griffon-framework.org/plugin/splash-javafx)


Provides a splash screen that is displayed during initialization of a Griffon application. The plugin provides hooks for
custom image and status text.

Usage
-----

The splash screen can be displayed by calling

        SplashJavafxGriffonAddon.display(app)

This code is usually placed inside `griffon-app/lifecycle/Initialize.groovy` as that's the lifecycle script that will be called
first when the application startups.

Configuration
-------------

### Setting an Image

You can configure a diffrent image to show by tweaking thew default `splashscreen.css` stylesheet.

### Updating Status

Updating the status text can be done at anytime while the Splash screen is shown, like this

    griffon.plugins.splash.SplashScreen.updateStatus("Initializing the Controller")

### Custom Screen

You can also define a totally different screen. Just implement `griffon.plugins.splash.SplashScreenProvider` the hooki it up by
defining the classname in `griffon-app/conf/metainf/services/griffon.plugins.splash.SplashScreenProvider`. For example, if the custom
screen provider is named `com.acme.CustomSplashScreenProvider` then the file should contain

    com.acme.CustomSplashScreenProvider


