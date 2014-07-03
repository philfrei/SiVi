package com.adonax.simplexNoiseVisualizer.animation;

/*
 * The following code was based on two examples. One came from 
 * Riven's JGO post: 
 *     http://www.java-gaming.org/topics/generate-animated-gifs/24196/view.html
 * and the other came from the following Oracle Community post:
 *     https://community.oracle.com/thread/1264385
 * It is unclear to me who the exact authors were on this. The 
 * following names are some but probably not all the contributors:
 *     Brian Burkhalter, Geoff Titmus, Andrew Thompson.
 * I chose to make this implementation less generalized. We 
 * assumes we always animate indefinitely, always use the same 
 * time increment, and we make no provision for transparency. I 
 * leave these features as possibilities for future improvements. 
 * Riven's code examples should be an excellent start, when the 
 * time comes as it explicitly deals with transparency issues.
 * Phil Freihofner 7/2/2014. 
 * 
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.Node;

public class AnimatedGifExport
{
	static void Export(BufferedImage[] images, 
			int delay, File outfile) throws IOException
	{
    	ImageWriter iw = 
    			ImageIO.getImageWritersByFormatName("gif").next();
    	ImageOutputStream ios = 
    			ImageIO.createImageOutputStream(outfile);
    	
        iw.setOutput(ios);
        System.out.println("outfile:" + outfile);
        iw.prepareWriteSequence(null);
        
        for (int i = 0; i < images.length; i++)
        {
            ImageWriteParam iwp = iw.getDefaultWriteParam();
            IIOMetadata metadata = iw.getDefaultImageMetadata(
            		new ImageTypeSpecifier(images[i]), iwp);
            
            // Start of a "configure" routine. In source examples, 
            // this section is contained in its own method.            
            String metaFormat = metadata.getNativeMetadataFormatName();
        	
            Node root = metadata.getAsTree(metaFormat);

            //find the GraphicControlExtension node
            Node child = root.getFirstChild();
            while (child != null) 
            {
                if ("GraphicControlExtension".equals(child.getNodeName())) 
                {
                    break;
                }
                child = child.getNextSibling();
            }

            IIOMetadataNode gce = (IIOMetadataNode) child;
            gce.setAttribute("userInputFlag", "FALSE");
            gce.setAttribute("delayTime", String.valueOf(delay / 10L));
            // Riven puts a "disposalMethod" setAttribute here.
            
            // applies only to first frame
            if (i == 0)
            {
               IIOMetadataNode aes = 
            		   new IIOMetadataNode("ApplicationExtensions");
               IIOMetadataNode ae = 
            		   new IIOMetadataNode("ApplicationExtension");
               ae.setAttribute("applicationID", "NETSCAPE");
               ae.setAttribute("authenticationCode", "2.0");
               
               //3rd byte is number of loops, where 0 = continuous
               byte[] uo = new byte[] { 0x1, 0x0, 0x0};
               ae.setUserObject(uo);
               aes.appendChild(ae);
               root.appendChild(aes);
            }
            
            try
            {
               metadata.setFromTree(metaFormat, root);
            }
            catch (IIOInvalidTreeException e)
            {
               throw new Error(e);
            }
            
            // This is the end of the "configure" routine
            
            IIOImage ii = new IIOImage(images[i], null, metadata);
            iw.writeToSequence(ii, null);
        }
        
        iw.endWriteSequence();
        ios.close();
	}
}
