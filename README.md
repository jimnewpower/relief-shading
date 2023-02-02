[![Java CI with Gradle](https://github.com/jimnewpower/relief-shading/actions/workflows/gradle.yml/badge.svg)](https://github.com/jimnewpower/relief-shading/actions/workflows/gradle.yml)

# Relief Shading
This package allows you to create shaded-relief raster images from Digital Elevation Models (DEM).

## What is Relief Shading?
Relief shading (sometimes referred to as hill shading, or shaded relief) refers to a technique used in cartography and computer graphics to create a three-dimensional representation of a surface on a flat map or image. This is achieved by simulating the effects of sunlight falling on the surface and casting shadows. The shadows are created by calculating the angles of the sun and the surface and mapping those angles to grayscale values, which are then applied to the surface. This creates a sense of depth and topography, making it easier to visualize and interpret the features of the surface.

[Technical reference](https://desktop.arcgis.com/en/arcmap/10.3/tools/spatial-analyst-toolbox/how-hillshade-works.htm)

## Data
Digital Elevation Models (DEM) provide nice inputs to see relief shading. You may use Shuttle Radar Topography Mission (.hgt) files directly.
 - [About the SRTM](https://eospso.gsfc.nasa.gov/missions/shuttle-radar-topography-mission)  
 - [Mission Page, Downloads](https://www2.jpl.nasa.gov/srtm/)

## Reference Image (from Google Maps)

>1 arc-second (90 meter) quad, 37-38 degrees north latitude, 107-108 degrees west longitude.
 
<img src="https://github.com/jimnewpower/relief-shading/blob/main/images/reference.png" alt="Reference Image" width="400" height="400">

## Shaded Relief
Create shaded relief image:  
```
        // Create shaded relief image from .hgt file.
        
        final String filename = "./N37W108.hgt";
        ZFactorDem zFactorDem = new ZFactorSrtmDem(FilenameSRTM.create(filename));

        GridClassifier classifier = GridClassifier
                .shaded(Preferences.createDefault(), zFactorDem);

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

        GridClassifier classifier = GridClassifier
                .color(ColorPaletteDefaults.DEM.colorPalette());

        Grid grid = DemReader.shuttleRadarTopographyMissionHGT(path).read();
        BufferedImage image = classifier.classify(grid);

        Path output = Paths.get("color.png");
        ImageIO.write(image, "png", output.toFile());
```
<img src="https://github.com/jimnewpower/relief-shading/blob/main/images/N37w108-color.png" alt="Color Filled" width="400" height="400">

## Color Filled, Constrained to Custom Z Bounds
Create color-filled image with custom z bounds:  
```
        // Create color-filled image from .hgt file.

        final String filename = "./N37W108.hgt";

        Bounds zBounds = Bounds.of(2250, 4310);//meters

        GridClassifier classifier = GridClassifier
                .color(ColorPaletteDefaults.DEM.colorPalette(), zBounds);

        Grid grid = DemReader.shuttleRadarTopographyMissionHGT(path).read();
        BufferedImage image = classifier.classify(grid);

        Path output = Paths.get("color-z.png");
        ImageIO.write(image, "png", output.toFile());
```
<img src="https://github.com/jimnewpower/relief-shading/blob/main/images/N37w108-color-z-in.png" alt="Color Filled" width="400" height="400">

## Combined Image
Shaded image with 30% opacity overlaid onto color image.

<img src="https://github.com/jimnewpower/relief-shading/blob/main/images/N37w108-overlay.png" alt="Shaded and Colored" width="400" height="400">

## Adjustments
The two key settings for relief shading are:  

> - *azimuth* the azimuth of the sun (0-360 degrees), default is 315. Think of this as a circle laid flat on the map, where 0 is north. So, a value of 315 means the sun is coming from the north-west, while a value of 180 is coming from the south.
> - *altitude* the elevation of the sun (0-90 degrees), default is 45. The lower the value, the more accentuated the features should become, while the higher the value, the features will become less severe.

These may be adjusted in the Preferences class.

## Set Preferences
```
        // Set preferences
        
        Preferences preferences = new PreferencesBuilder()
                .altitudeDegrees(30)
                .azimuthDegrees(225)
                .build();

        GridClassifier classifier = GridClassifier
                .shaded(preferences, zFactorDem);
```


