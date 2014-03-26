package org.flowerplatform.mindmap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristina Constantinescu
 */
public class MindMapNodePropertiesConstants {

	public static final List<String> FONT_SIZES = new ArrayList<>();
	static {
		for (int i = 1; i <= 72; i++) {
			FONT_SIZES.add(String.valueOf(i));
		}
	}
	
	// CLOUD SHAPES
	public static final String SHAPE_NONE = "none";		
	public static final String SHAPE_RECTANGLE = "rectangle";
	public static final String SHAPE_ROUND_RECTANGLE = "roundRectangle";
			
	public static final int DEFAULT_MIN_WIDTH = 1;
	public static final int DEFAULT_MAX_WIDTH = 600;
	
	public static final String TEXT = "text";
	
	public static final String MIN_WIDTH = "minWidth";
	public static final String MAX_WIDTH = "maxWidth";
	public static final String FONT_FAMILY = "fontFamily";
	public static final String FONT_SIZE = "fontSize";
	public static final String FONT_BOLD = "fontBold";
	public static final String FONT_ITALIC = "fontItalic";
	
	public static final String COLOR_TEXT = "colorText";
	public static final String COLOR_BACKGROUND = "colorBackground";
	
	public static final String CLOUD_COLOR = "cloudColor";
	public static final String CLOUD_SHAPE = "cloudShape";
	
}
