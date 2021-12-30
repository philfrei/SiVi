# SiVi - a Java-based 2D Gradient-Noise Visualizer

## Contents
[Introduction](https://github.com/philfrei/SiVi#introduction)

[Compiling and Running SiVi](https://github.com/philfrei/SiVi#compiling-and-running-sivi)

[The GUI Explained](https://github.com/philfrei/SiVi#the-gui-explained)

* [Channels](https://github.com/philfrei/SiVi#channels)
* [Mixer](https://github.com/philfrei/SiVi#mixer)
* [Gradient Modulation](https://github.com/philfrei/SiVi#gradient-modulation)
* [Color Mapping](https://github.com/philfrei/SiVi#color-mapping)
* [Final Display](https://github.com/philfrei/SiVi#final-display)

[Example Gallery](https://github.com/philfrei/SiVi#example-gallery)
* [Classic Clouds](https://github.com/philfrei/SiVi#classic-clouds)
* [Tree Rings](https://github.com/philfrei/SiVi#tree-rings)
* [Plasma and Animation](https://github.com/philfrei/SiVi#plasma-and-animation)

[Exporting textures for use in other frameworks](https://github.com/philfrei/SiVi#exporting-textures-for-use-in-other-frameworks)

[Using GUI settings to write code](https://github.com/philfrei/SiVi#using-gui-settings-to-write-code)
* [Code template](https://github.com/philfrei/SiVi#code-template)
* [Notes on the code](https://github.com/philfrei/SiVi#notes-on-the-code)

[Additional Topics](https://github.com/philfrei/SiVi#further-notes)

[Project History](https://github.com/philfrei/SiVi#project-history)

[References and links](https://github.com/philfrei/SiVi#references-and-links)

## Introduction

**SiVi** is a Java-based GUI built to assist in the creation of textures that rely on the mixing of 2D [gradient noise]([https://en.wikipedia.org/wiki/Gradient_noise). With SiVi, one can view the results of mixing variously scaled and manipulated *channels* of gradient noise data. Additional capabilities include the exporting of graphics and animated gifs. Values are exposed by the GUI that can be referred to when writing procedural code to create textures on the fly. A [template](https://github.com/philfrei/SiVi#code-template) for writing procedural code is included below.

The inspiration for making SiVi came from reading Stefan Gustafsen's 2005 article [Simplex noise demystified](http://staffwww.itn.liu.se/~stegu/simplexnoise/simplexnoise.pdf). I strongly recommend Gustafsen's article to anyone wanting a clear explanation of the theory and for his implementation of Ken Perlin's *Simplex Noise*. However, the article said little about how to *use* this technique artistically. SIVI was written to help with learning both how to design and how to code gradient-noise-based textures, and to make experimentation easier.

SiVi uses [OpenSimplexNoise](https://gist.github.com/KdotJPG/b1270127455a94ac5d19) as its default. SiVi has been set up to easily incorporate additional noise generating libraries, making it possible to compare different types of gradient noise. Gustafsen's implementation of *SimplexNoise* is included and can be selected for comparison purposes.

## Compiling and Running SiVi

If you don't wish to fork the project, you can download and run SiVi-1.0.1-SNAPSHOT.jar with the following command: `java -jar SiVi-1.0.1-SNAPSHOT.jar`.
The program is configured to run with Java 11 or higher.

To recreate this jar, in the CLI navigate to the project directory (contains pom.xml) and execute the Maven command: `mvn clean package`.
The jar will be created in the /target directory. 

## The GUI Explained

In this next section, the basic features of the GUI are described. The following figure shows the GUI for a 3-channel project used to create an earthlike planet texture. 

*Fig. SiVi's GUI (67% size), developing an earthlike planet texture*

<img src="images\fsTerra.png" alt="fsTerra" style="zoom:67%;" />

#### Settings

From the menu bar, the settings allow the specification of the width and height of the graphic, and the number of contributing "octaves". The noise engine is also selected here. Most recently, horizontal and vertical reflections have been added. These reflections result in a doubling of the width and height in the final display.

#### Channels

The top half of SiVi contains controls for the contributing *channels* of noise. This project has 3 channels. If more channels are used than can be displayed, a scroll bar is provided. Each *channel* controls a separate call to the noise function. The settings on each channel are for *scaling*, *translation*, a filter function (of dubious merit), and a set of radio buttons for the selection of an algorithm to transform the results, or not, from the range -1 to 1, to the range 0 to 1. This section also includes a visualization of the target area.

A call is made to the noise function for every pixel in the target rectangle image. As we traverse over the area of our intended graphic using a simple `for` loop, the X and Y coordinates are plugged in as arguments to the noise function. The GUI controls **X Scale** and **Y Scale** hold values that control the distance we travel through the *gradient noise space* over the course of this `for` loop.

With a scaling of 2 instead of 1, our arguments to the noise function are twice as large, and thus cover twice as much of the *gradient noise space*. This results in a denser, more compact, pattern of peaks and valleys in the data. In the above example, the first channel has an X scaling of 3, the second an X scaling of 9 and the third and X scaling of 27. If you look at the corresponding visualizations for the channels, you can see that the first graphic matches the top left corner of the second (at 1/3rd the width and height), and the second matches up to the top corner of the 3rd.

The GUI fields **X Trans** and **Y Trans** control the starting point for our traversals in the *gradient noise space*.

The values returned by the noise function range from -1 to 1. The **MIN** and **MAX** settings can be used to curtail that range. For example, with a **MIN** setting of -0.5, returned values from the noise function that lie between -1 and -0.5 will be set to -0.5 before results are passed on to the mixer. I have yet to find any artistically interesting uses for these filters, and am considering eliminating them from the GUI.   

In the next stage, we choose whether or not to apply one of two scaling functions to the noise data. The first conversion option, **`(v + 1)/2`** is a simple scaling that preserves the shape, and was given the name *Smooth Noise* by Ken Perlin. The second option, **`|v|`**, an ABS function, gives the return noise data curve a fold at the zero point, and is the basis of what Perlin called *Turbulent Noise*. The third option,  **none** allows the values to pass to the mixer unaltered.

#### Mixer

In the bottom half of SiVi, the top left corner has an array of sliders, one per *channel*, under the heading **Mix**. With this, one can control the relative contributions of each channel. This section also include a slider named **All** that affects the amplitude of the resulting mix of channels. In our example, the first channel is given a value of 63, the second channel is given 21, and the third 7. Each has 1/3 the weight of the previous, corresponding to the scaling changes between channels. The mixed noise value will be computed as follows: 
$$
noiseVal = (noiseVal_0 * 63 + noiseVal_1 * 21 + noiseVal_2 * 7)/(63 + 21 + 7)
$$
This *fractal*-like progression of settings between channels is often an efficient way to generate a texture with a minimum number of channels. By far the most common fractal progression seen with mixing is called *octaves*, and uses a factor of  1/2, not 1/3.

#### Gradient Modulation

The output of the mix can go directly to the *Color Mapping* function, or it can optionally be combined with data from another shape or function. There are only a few target gradient shapes built into SiVi: currently a plane, a radial gradient, and a sine function. These are shown in the box labeled **Modulate a Gradient Function** which is directly below the **MIX** section. The check boxes are used to include a gradient. Settings for the target gradients can be edited by clicking the corresponding label. 

Expanding the number of gradient shapes and their configurability would greatly enhance the artistic potential of SiVi.

#### Color Mapping

As the last step *before* color mapping, in the area **Color Map Preprocessing**, we have two options for handling values that fall out of the input range (0 to 1) of the color map. The radio button *Clamp* sets all values below 0 to 0, and all values above 1 to 1. The radio button *Ring* sets the output to repeatedly wrap around the values 0 to 1.

The color mapping function operation is configured in the area **Color Map Selector** which displays six check boxes and accompanying rectangle which display the mapped colors in 256 steps. Checking the check box control of a map will select it. Only one map can be selected at a time. Mappings can be copied and moved easily between the six slots. Clicking on a map rectangle enables editing of the map's configuration settings. 

In the shown example, we translate the values 0 to 1 to a series of colors as might be seen from a satellite's view of the earth, with blues for the  ocean, and yellows, greens and greys for land. Each of these colors has a location within the range 0 to 255. This particular mapping is my attempt to replicate the mapping shown in a blog post written by *AngryOctapus*: [Content Generation for Programmers Pt 1](http://www.angryoctopus.co.nz/?p=11).

Six color map slots are provided in the GUI tool in order to allow for a convenient way to compare mappings while working on this key aspect of texture design. 

#### Final Display

The *Final Texture* area on the lower left corner displays the resulting texture graphic.

## Example Gallery

SiVi includes a gallery of examples that are procedurally generated. These can be displayed using the Menu Bar: (MenuBar>>Settings>>Gallery). Clicking on a displayed graphic will bring it's settings into the GUI for further editing. Gallery examples can be used as starting points for the creation of new graphics.

In this section, we take a closer look at two examples from the *Gallery* contained within SIVI.

#### Classic clouds

*Fig. Clouds*

<img src="images\classic_clouds.png" alt="Classic Clouds" />

This texture uses 5 channels, configured with a *fractal*-like pattern commonly called *octaves*. The scaling on the inputs progresses by doubling: 2, 4, 8, 16, 32. The relative contribution of each successive channel is halved (approximately): 0.52, 0.26, 0.13, 0.6, 0.3. Fractal relationships between channels work well for many textures, and *octave* relationships are often set as a default in implementations that I've come across. IMHO, the use of this exact doubling/halving pattern is not always needed. For example, the earthlike planet texture in the GUI example obtains an interesting degree of variation with only three channels, using a 3:1 ratio between the channel settings instead of 2:1. Given that executing the noise function is relatively costly, finding ways to minimize the number of channels can be helpful.

Note that the weighting values in the mixer add up to 1. As a result, with the amplitude also set to one, the sum of the weights is guaranteed to never exceed 1. So, no pre-screening is needed prior to sending the data to the *Color Map*, and selecting either *Ring* or *Clamp* has no effect.

The 0 value of the color mapping is a simple Blue [R:0, G:0, B:255] and the 1 value is a simple White [R:255, G:255, B:255]. If setting the colors with `floats` instead of `ints`, we would use: [0, 0, 1] and [1, 1, 1] respectively. If coding the textures procedurally, we could forgo the use of the map altogether, and simply plug the noise values into the R and G arguments for the pixel, and leave the B value at 1 for all pixels.

#### Tree Rings

*Fig. Tree Rings*

<img src="images/tree_rings.png" alt="Tree Rings" />

This texture uses just one channel. The mixer has the amplitude set to 4, and combines the noise values with a radial gradient function. The radial gradient is centered in the middle with a high value of 15, and progresses at a linear angle away from the center, reaching a value of 0 at the center edge (and a bit lower at the corners). This gradient, when added to the noise function, causes the values reaching the Color Map to greatly exceed the map's range. The 0 value color is [154, 122, 29] and the 1 value of the map is [192, 160, 64]. With the *rings* value selected, the function repeatedly ranges through the map's colors as the input to the mapping function ranges from below 0 to over 15. Intermediate values are computed using linear interpolation for each color channel.

####  "Plasma" and Animation

*Fig. SIVI can export gifs*

<img src="images\plasma.gif" alt="Plasma gif" />

A tool for creating animating gifs is now part of the project. The animation is now enabled on the *x-, y- and z-axis*. The tool can be invoked from the Menu Bar:  (MenuBar>>Settings>>AnimationTool). The tool opens with default settings that create a 50-frame graphic, with the 20 milliseconds per frame. 

In this example graphic, you will notice that there is no discontinuity when the gif loops. This smoothness is accomplished by designating a large number of frames in the *Overlap* control. When there are overlap frames, linear interpolation is used between each overlapped pair to achieve smoothing.

As for the fiery graphic itself, there are four channels of *Turbulent Noise*, set to *octave* relationships. The output is sent to a color map function that consists of three colors: 

| map value | red  | green | blue |
| --------- | ---- | ----- | ---- |
| 0         | 255  | 255   | 102  |
| 127       | 204  | 0     | 0    |
| 255       | 0    | 0     | 0    |

The mapping function in SIVI works as follows: `float` values ranging from 0 to 1 are mapped to `int` values ranging from 0 to 255. If the calculated `int` is exactly 0, 127 or 255, the corresponding color values are used. If the calculated `int` value lies in between 0 and 127, or in between 127 and 255, then the RGB color values are calculated using linear interpolation. For example, the map value of 214 (half way between 127 and 255) would give us a *red* value of 102.  

A couple notes and TODOs: I neglected to allow animation when the z-axis increment is 0. For the moment, a very small value, such as 0.000001 can be added for a virtually motionless action on the z-axis.
Bear in mind that the value of 1 is pretty much equivalent to moving from node to node, and thus will create a fairly random sequence. Values of 0.1 or 0.01 will likely produce better results. I'm also noticing that the timing of 50 milliseconds per frame (20 fps) is reasonably smooth.




## Exporting textures for use in other frameworks

*Fig. Screen shot of 3 textures, imported into AFRAME as skins/surfaces*

<img src="images\trio.jpg" alt="Three AFRAME objects with generated skins" />

Texture settings cannot yet be saved/loaded (this is high on the *task list*). But graphics can be exported to *jpg*, *png* and *gif* formats. The exported graphics can then be imported into other systems. The above figure shows three shapes drawn with the Javascript AR library AFRAME, each making use of a SIVI-generated texture graphic for its surface. This AFRAME screen shot is a bit of a cheat, though! If we were actually using AFRAME to view these 3D objects, it would be possible to navigate and view them from the reverse side. At that point it would be possible to see the seams, where the edges of the graphic meet up. Adding a provision to eliminate the seams is on the *task list*.

## Using GUI Settings to write code

SIVI displays values that can be plugged into code, allowing for the creation of textures on a *procedural* basis. The template code below was written with instructions on where the various GUI settings are placed.

*Important note:* There isn't any single best way to get from a given 2D pixel location to the noise function and from there to that pixel's color values. This template is just one example. The code here is functionally very close to what is used in SiVi's [TextureFunctions](https://github.com/philfrei/SiVi/blob/master/src/main/java/com/adonax/sivi/TextureFunctions.java) class. This template omits making use of the *Channel Min/Max* settings, and does not provide the details used to create and configure the gradient value arrays, nor does it provide the details used to create or configure the color mapping.

#### Code Template

```java
public class CodeTemplate {

	enum ScalingMethod {SMOOTH, TURBULENT, NONE;}
	enum RingOrClamp {RING, CLAMP;}
	
	float[] mixWeights;

	// [][] coordinate values reflect X & Y location of given pixel	
	float[][] noiseVals;
	float[][] modulationTarget;

	// Holds source data for constructing image in preferred Object
	// e.g. BufferedImage (Swing) or WritableImage (JavaFX)
    	Color[][] graphicData; 
	
	NoiseFunction noiseFunction;
	GUI gui;
	
	CodeTemplate(int xDim, int yDim, int nChannels)
	{
		// (1a) denominator for weighting factor
		float mixWeightsTotal = gui.getMixWeightsTotal();
		
		// (1b) get weighting factor for each channel
		for (int n = 0; n < nChannels; n++)
		{
			mixWeights[n] = gui.getMixWeight(n) / mixWeightsTotal;
		}	
		
		// (2a) iterate through the width
		for (int i = 0; i < xDim; i ++)
		{
			// (2b)iterate through the height
			for (int j = 0; j < yDim; j++)
			{
				// (2c) iterate through each channel
				for (int n = 0; n < nChannels; n++)
				{
				    // (3a) obtaining the X & Y args for the noise function
					float argX = i * (gui.getXScale(n)/256f) + gui.getXTranslate(n);
					float argY = j * (gui.getYScale(n)/256f) + gui.getYTranslate(n);
					// (3b) noise function call
					float noiseVal = noiseFunction.get(argX, argY);
					
					// (4) choice: convert [-1 to 1] to [0 to 1] or not
					switch (gui.getScalingMethod(n))
					{
						case SMOOTH: 
							noiseVal = (noiseVal + 1)/2f;
							break;
						case TURBULENT:
							noiseVal = Math.abs(noiseVal);
							break;
						case NONE:
							break;
					}
					// (5a) calculate the relative weighting
					noiseVal *= mixWeights[n];
					noiseVals[i][j] += noiseVal;
				}
				
				// (5b) and the overall amplitude of the noise signal
				noiseVals[i][j] *= gui.getMixAmplitude();

				// (6) Optional, if using the noise to modulate a target gradient.
				if (modulationTarget != null)
				{
					noiseVals[i][j] += modulationTarget[i][j];
				}

				// (7) Keep values within range of ColorMapping function (range 0 to 1).
				switch (gui.getRingOrClamp())
				{
				case RING:
					noiseVals[i][j] = noiseVals[i][j] 
							- (float)Math.floor(noiseVals[i][j]);
					break;
				case CLAMP:
					noiseVals[i][j] = Math.max(0, Math.min(1, noiseVals[i][j]));
				
				// (8) Color Mapping
				graphicData[i][j] = gui.colorMapFunction(noiseVals[i][j]);
				}	
			}
		}
	}
	
	// (9) Wrapper/Adaptor for chosen noise function
	interface NoiseFunction
	{
		float get(float xVal, float yVal);
	}
	
	// (10) Functions to obtain plug in values from GUI, here used
	//      to show where values are located in the GUI.
	abstract class GUI
	{
		abstract float getXScale(int channel);
		abstract float getYScale(int channel);
		abstract float getXTranslate(int channel);
		abstract float getYTranslate(int channel);
		abstract float getMixWeight(int channel);
		abstract float getMixWeightsTotal();
		abstract float getMixAmplitude();
		abstract ScalingMethod getScalingMethod(int channel);
		abstract RingOrClamp getRingOrClamp();
		abstract Color colorMapFunction(float normal);
	}
}
```


#### Notes on the code.

(1a, b) The degree to which the different *channels* will affect the texture are set in the *MIX* section of the GUI. We have one *Mix* slider for each *channel*. Weights are calculated by taking the sum of all channels as a denominator, and the individual setting as the numerator. In the *Earth-like Planet* example shown at the top,  the denominator = 0.63 + 0.21 + 0.07, and the individual channels will get the following weights:
`mixWeights[0]` = 0.63/0.91, `mixWeights[1]` = 0.21/0.91, `mixWeights[2]` = 0.07/0.91. These values are precalculated and stored in `mixWeights[]` at the outset, so that the factors can be plugged into the inner loops rather than redundantly recalculated.

(2a, b, c)  The noise calculation must be executed for every pixel in the graphic and for every *channel* used to build the texture. These loops iterate through the width and height of the graphic and perform the computations on each channel for every pixel in the graphic. 

(3a, b) Here we obtain the arguments that are used by the noise-function. The scaling settings **X Scale** and **Y Scale** are multiplied against the unit iterator, which affects the distance traversed over the *gradient noise space*. Higher increments arising from higher scaling values will take bigger steps over the space, and thus produce more closely packed peaks and valleys in the data.

The denominator 256 was arbitrarily chosen here and is correctly considered an *antipattern* ([magic number](https://en.wikipedia.org/wiki/Magic_number_%28programming%29)). The goal is to aim to set 256 steps (i.e., 256 pixels) to correspond to 1 lattice traversal with the scaling set to 1. However, the difference between X and X+1 in a noise function call does not necessarily match the distance between lattice points. And different engines seem to have configured this differently, with Gustafsen's *Simplex Noise* requiring a denominator of 512 in order to match the lattice position differences employed in *Open Simplex Noise*. 

As the step size approaches the distance between consecutive lattices, the relationship between steps becomes increasingly random. For reasons of practicality, an upper limit for the scaling was set to 128 steps (half of the distance between two lattices). At the lower end, making miniscule progress between two lattices approaches a return of a constant value, so as a practical matter, the lower limit of the scaling is set to 1/8th the distance between two lattices.

The iteration over the *gradient noise space* starts at [0, 0] by default. A translation value can move the starting point to another location in the *gradient noise space*. Sometimes the translation values are thought of as being similar to random number generator *seeds*.

(4) The values returned by the noise function range from -1 to 1. Colors, however, are commonly set with values ranging from 0 to 1. At this point, we can choose whether or not to make use of *transform* to get our values within the color-mapping range.  Ken Perlin describes using a scaling transform (scales the signal to half size and then translates it from [-0.5...0.5] to [0...1]) as *Smooth Noise*, and a transform that *folds* the negative values of the signal into positives (noiseValue = |noiseValue|) as *Turbulent Noise*. But we can also elect to forego this step as there will be further opportunities to ensure the Color Mapping function only receives valid data.

(5) We multiply the precalculated weight factors with the noise value, and add the contributions of the individual channels to the value that will represent the given pixel. Once the sum has been determined, the *volume* slider labeled **All** at the bottom of the *Mix* area is used as a factor to alter the overall amplitude of the signal.

(6) If we have a shape or gradient to modulate with our noise signal, it's done at this point. It is assumed here that the object being modulated has equivalent dimensions. The noise values and gradient values are summed together. It is possible to perform more than one modulation.

(7) This is a preprocessing step we take to ensure that the values fed to the Color Mapping function are within a legal range. Often, and deliberately, the noise signal will exceed the range 0 to 1. The *Ring* transform causes values that exceed the 0 to 1 range to wrap around and repeatedly traverse over the range. For example, a noise value of 1.1 becomes 0.1, 1.9 becomes 0.9, 2.1 becomes 0.1. 

An interesting variant, instead of using the *floor* function, is to cast the noise value to an `int` and subtract that from the noise value and take the `abs value` of the result. This causes the wrap-around to reverse in direction for negative noise signal values. 

|      | N - floor(N) | \|N - (int)N\| |
| ---- | ------------ | -------------- |
| 0.3  | 0.3          | 0.3            |
| 0.2  | 0.2          | 0.2            |
| 0.1  | 0.1          | 0.1            |
| 0    | 0            | 0              |
| -0.1 | 0.9          | 0.1            |
| -0.2 | 0.8          | 0.2            |
| -0.3 | 0.7          | 0.3            |

(8) The color mapping function i SIVI maps values in the `float` range 0 to 1 to the `int` range from 0 to 255. However, one can also create colors using the noise values floats directly. Since they have gone through a pre-processing stage (note 7), the values should be legal, and can be directly plugged into the *Red*, *Green*, *Blue*, or *Alpha* channels of the pixel.

The template code simply has us populating an array of `Color` objects. Presumably one will be using these colors with either a `BufferedImage` with a `WritableRaster`, if working with Swing, or a `WritableImage` and `PixelWriter` with JavaFX.

(9) An Interface is used in this template code as a place-holder to a call to the noise function. There are currently noise functions that one can use. SIVI uses [OpenSimplexNoise]() as the default, and includes Gustafson's implementation of Ken Perlin's [SimplexNoise](). Other gradient noise functions also exist, such as *ClassicPerlinNoise* and *ImprovedPerlinNoise*. These can be brought into SIVI, by implementing `NoiseEngine` and storing the adaptation class in the `enum` collection `NoiseEngines.Source`.

(10) This section is also place-holder code. It's purpose is to indicate where on the GUI the plug-in values are displayed. SIVI makes use of a functional programming design, where collections of variables are stored in immutable "model" objects.

## Further notes

The noise-texture functions can also be used with what are sometimes called *anisotropic* effects to create the appearance of a globe or perspective. *AngryOctapus* [describes the use of an oval lens](http://www.angryoctopus.co.nz/?p=11) near the end of the linked blog article. I believe these transforms are best performed as part of step (3a). SIVI has not (yet) implemented this.

It would also be useful to be able to incorporate stencils, asis done in the above *AngryOctapus* blog, as part of the process of creating a "planet" graphic, or as I've seen Ken Perlin demonstrate in a graphic that looks like a solar eclipse. (The link to this has vanished, unfortunately.) I am thinking in terms of stencils as either being incorporated into the Color Mapping step, or as an additional step after the Color Mapping and prior to display. In this step, pixels in the area to be stenciled out would receive an Alpha channel value of 0, to make them transparent.

## Project History

#### Origins

When this project was started, I was pretty much a novice Java coder. I could barely write functioning GUI code, and learned a great deal from collaborators at Java-Gaming.org (now jvm-gaming.org) and especially from member LoomWeaver (Sumio Kiyooka). Despite this help, cruft and code smell remain attributes of the project, for which I admit responsibility.

Along the way, I learned about *functional programming* and the *Model-View-Controller* design pattern, and the code was revised in my first attempts to implement these concepts.

#### Project reboot

In February, 2020, the project was rebooted. Revisions included deleting *tutorial* and *code-generation* functionality to bring down the code base size, revisions and enhancements to the GUI, and a tweaking of the *model* data structure.

The most significant change was making *OpenSimplexNoise* the default noise engine, and creating a code interface to allow the inclusion of additional noise engines. Gustafson's implementation of Perlin's Simplex Noise has been left in as an alternative for viewing. 

#### Wish List

Following are items that if coded would enhance the project. They can be found in the *task list*, along with notes relevant for the task. The ordering below reflects my personal, first estimate of priority and size-of-task. But it should be possible to jump in at any point in this list.

- Improve the field controls so that an <Enter> keypress is not required after changing the value in the field. This has only been done for the *Animation Panel* fields.
  
- Save/Load for texture settings
  *Will likely include a review of the current somewhat fragmented "model" structure.* 

- Bring in additional noise engines
  
- Anisotropic effects
  *Requires functions that respond to the [x, y] location in the target graphic, and are applied prior to the call to the noise function.*

- Additional, and enhanced, gradients

- Add more poles to the color mapping, and include an alpha channel.
	
- Stencils
  *Apply masks that can either be color (RGB) or alpha (opacity) channel values.*

- More animation parameters
  *We now have [x, y, z] translation. Consider animating other settings.*

- Code export

- NEW idea: add the mapping functions shown in article [Procedural Generation](http://squall-digital.com/ProceduralGeneration.html)
  

## References and Links

Stefan Gustafson's paper [*Simplex Noise Demystified*](http://weber.itn.liu.se/~stegu/simplexnoise/simplexnoise.pdf)

[Repository for noise functions (Simplex, Classic, Cellular)](https://github.com/stegu/webgl-noise/) from Stefan Gustafson

*AngryOctapus'* blog post [Content Generation for Programmers Pt 1](http://www.angryoctopus.co.nz/?p=11)

Also worth checking out:

This is an impressive, live 3D demo using *WebGL* and *three.js*: [Experiments with Perlin Noise](https://www.clicktorelease.com/blog/experiments-with-perlin-noise/)

