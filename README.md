[![Java CI with Gradle](https://github.com/jimnewpower/relief-shading/actions/workflows/gradle.yml/badge.svg)](https://github.com/jimnewpower/relief-shading/actions/workflows/gradle.yml)

# Relief Shading
Create shaded-relief raster images from Digital Elevation Models (DEM).

## Example With SRTM .hgt
[Download .hgt files](https://eospso.gsfc.nasa.gov/missions/shuttle-radar-topography-mission)
```
        final String filename = "./N37W108.hgt";
        ZFactorDem zFactorDem = new ZFactorSrtmDem(FilenameSRTM.create(filename));

        GridClassifier classifier = GridClassifierShadedRelief
                .of(Preferences.createDefault(), zFactorDem);

        BufferedImage image = classifier.classify(loadGrid());

        Path output = Paths.get("N37w108-shaded.png");
        ImageIO.write(image, "png", output.toFile());
```
<img src="https://github.com/jimnewpower/relief-shading/blob/main/images/N37w108-shaded.png" width="800" height="800>

## Supported DEMs
Shuttle Radar Topography Mission (SRTM) .hgt (height) files.

![San Juan Mountains, Colorado](images/sanjuans.png?raw=true "San Juan Mountains, Colorado USA")

![San Juan Mountains, Colorado](images/sanjuans2.png?raw=true "San Juan Mountains, Colorado USA")
