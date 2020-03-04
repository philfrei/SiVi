/**
 * @author Phil Freihofner
 *
 */
module siviModule {
	exports com.adonax.sivi.utils;
	exports com.adonax.sivi.color;
	exports com.adonax.sivi.animation;
	exports com.adonax.sivi.gallery;
	exports com.adonax.sivi;
	exports com.adonax.sivi.gradients;

	requires transitive java.datatransfer;
	requires transitive java.desktop;
	requires java.xml;
}