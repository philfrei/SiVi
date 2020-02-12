/**
 * @author Phil Freihofner
 *
 */
module siviModule {
	exports com.adonax.simplexNoiseVisualizer.utils;
	exports com.adonax.simplexNoiseVisualizer.color;
	exports com.adonax.simplexNoiseVisualizer.animation;
	exports com.adonax.simplexNoiseVisualizer.tutorial;
	exports com.adonax.simplexNoiseVisualizer;
	exports com.adonax.simplexNoiseVisualizer.gradients;

	requires transitive java.datatransfer;
	requires transitive java.desktop;
	requires java.xml;
}