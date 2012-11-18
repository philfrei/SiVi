SiVi
====

Simplex texture visualization tool

Ant
---

To build and execute from the command-line using Ant:

    ant clean build
    ant run


Maven
-----

    mvn clean package
    mvn exec:exec


Eclipse
-------

1. Select menu File -> New -> Java Project
2. In the New Java Project dialog uncheck: Use default location
3. Browse to location SiVi
4. Click Next
5. In the next dialog page (Java Settings) expand the src/main/resources folder
6. Right-click on it and select "Use as Source Folder" from the context-menu
7. Click Finish

Build and Debug main() in com.adonax.texturebuilder.SimplexTextureBuilder


IntelliJ
--------

1. Create New Project
2. Select radio button Import project from external model (maven pom)
3. Browse Root directory to SiVi

Make project and Debug main() in com.adonax.texturebuilder.SimplexTextureBuilder
