[![Java CI with Gradle](https://github.com/jimnewpower/relief-shading/actions/workflows/gradle.yml/badge.svg)](https://github.com/jimnewpower/relief-shading/actions/workflows/gradle.yml)

# Relief Shading
Create shaded-relief raster images from Digital Elevation Models (DEM).

## Example With SRTM .hgt
[Download .hgt files](https://eospso.gsfc.nasa.gov/missions/shuttle-radar-topography-mission)  
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

## Supported DEMs
Shuttle Radar Topography Mission (SRTM) .hgt (height) files.

![San Juan Mountains, Colorado](images/sanjuans.png?raw=true "San Juan Mountains, Colorado USA")

![San Juan Mountains, Colorado](images/sanjuans2.png?raw=true "San Juan Mountains, Colorado USA")
