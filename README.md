[![Java CI with Gradle](https://github.com/jimnewpower/relief-shading/actions/workflows/gradle.yml/badge.svg)](https://github.com/jimnewpower/relief-shading/actions/workflows/gradle.yml)

# Relief Shading
This package allows you to create shaded-relief raster images from Digital Elevation Models (DEM).

## What is Relief Shading?
Relief shading (sometimes referred to as hill shading, or shaded relief) refers to a technique used in cartography and computer graphics to create a three-dimensional representation of a surface on a flat map or image. This is achieved by simulating the effects of sunlight falling on the surface and casting shadows. The shadows are created by calculating the angles of the sun and the surface and mapping those angles to grayscale values, which are then applied to the surface. This creates a sense of depth and topography, making it easier to visualize and interpret the features of the surface.

[Technical reference](https://desktop.arcgis.com/en/arcmap/10.3/tools/spatial-analyst-toolbox/how-hillshade-works.htm)

## Examples With SRTM .hgt
[Download .hgt files](https://eospso.gsfc.nasa.gov/missions/shuttle-radar-topography-mission)  

## Shaded Relief
Create shaded relief image:  
```
        // Create shaded relief image from .hgt file.
        
        final String filename = "./N37W108.hgt";
        ZFactorDem zFactorDem = new ZFactorSrtmDem(FilenameSRTM.create(filename));

        GridClassifier classifier = GridClassifierShadedRelief
                .of(Preferences.createDefault(), zFactorDem);

        Grid grid = DemReader.shuttleRadarTopographyMissionHGT(path).read();
        BufferedImage image = classifier.classify(grid);

        Path output = Paths.get("shaded.png");
        ImageIO.write(image, "png", output.toFile());
```
<img src="https://github.com/jimnewpower/relief-shading/blob/main/images/N37w108-shaded.png" alt="Shaded Relief" width="400" height="400">

## Color Filled
Create color-filled image:  
```
        // Create color-filled image from .hgt file.

        final String filename = "./N37W108.hgt";

        GridClassifier classifier = GridClassifierColor
                .of(ColorPaletteDefaults.DEM.colorPalette());

        Grid grid = DemReader.shuttleRadarTopographyMissionHGT(path).read();
        BufferedImage image = classifier.classify(grid);

        Path output = Paths.get("color.png");
        ImageIO.write(image, "png", output.toFile());
```
<img src="https://github.com/jimnewpower/relief-shading/blob/main/images/N37w108-color.png" alt="Color Filled" width="400" height="400">

## Combined Image 1
Layer color on top of shaded, and reduce opacity of color layer (50% in this case).

<img src="https://github.com/jimnewpower/relief-shading/blob/main/images/shaded-and-colored.png" alt="Shaded and Colored" width="400" height="400">

## Combined Image 2
Layer shaded on top of colored, and reduce opacity of shaded layer (30% in this case).

<img src="https://github.com/jimnewpower/relief-shading/blob/main/images/colored-and-shaded.png" alt="Colored and Shaded" width="400" height="400">

## Adjustments
The two key settings for relief shading are:  

> - *azimuth* the azimuth of the sun (0-360 degrees), default is 315. Think of this as a circle laid flat on the map, where 0 is north. So, a value of 315 means the sun is coming from the north-west, while a value of 180 is coming from the south.
> - *altitude* the elevation of the sun (0-90 degrees), default is 45. The lower the value, the more accentuated the features should become, while the higher the value, the features will become less severe.

These may be adjusted in the Preferences class.

## Set Preferences
```
        Preferences preferences = new PreferencesBuilder()
                .altitudeDegrees(30)
                .azimuthDegrees(225)
                .build();

        GridClassifier classifier = GridClassifierShadedRelief
                .of(preferences, zFactorDem);
```


