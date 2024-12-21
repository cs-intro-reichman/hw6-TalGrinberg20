import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;
	

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);

		image = flippedVertically(tinypic);
		System.out.println();
		print(image);

		image = grayScaled(tinypic);
		System.out.println();
		print(image);
		

		image = scaled(tinypic, 3, 5);
		System.out.println();
		print(image);

		Color a  = new Color(100, 40, 100);
		Color b  = new Color(200, 20, 40);
		print(blend(a, b, 0.25));	

		Color[][] img1 = read("escher.ppm");
        Color[][] img2 = read("eyes.ppm");	
		Color[][] blended = Runigram.blend(img1, img2, 0);
        Color[][] res = img2;
		if (blended == null || res == null) {
            System.out.println("false"); 
        }
        boolean res1 = TesterRunigram.compareImages(blended, res);

        blended = Runigram.blend(img1, img2, 0.21);
        res = TesterRunigram.safeRead("expected_results/blend_021.ppm");
        
        if (blended == null || res == null) {
            System.out.println("false");
        }
        boolean res2 = TesterRunigram.compareImages(blended, res);


        blended = Runigram.blend(img1, img2, 0.5);
        res = TesterRunigram.safeRead("expected_results/blend_05.ppm");
        
        if (blended == null || res == null) {
            System.out.println("false"); 
        }
        boolean res3 = TesterRunigram.compareImages(blended, res);

        blended = Runigram.blend(img1, img2, 0.75);
        res = read("expected_results/blend_075.ppm");
        
        if (blended == null || res == null) {
            System.out.println("false");
        }
        boolean res4 = TesterRunigram.compareImages(blended, res);

        blended = Runigram.blend(img1, img2, 1);
        res = img1;
        
        if (blended == null || res == null) {
            System.out.println("false");;
        }
        boolean res5 = TesterRunigram.compareImages(blended, res);
        
        System.out.println(res1 && res2 && res3 && res4 && res5);	
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		Color curerentColor;
		int r,g,b;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				r = in.readInt();
				g = in.readInt();
				b = in.readInt();
				curerentColor = new Color(r,g,b);
				image[i][j] = curerentColor;
			}
			
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.


	private static void print(Color[][] image) {
		// System.out.println("test");
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				print(image[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] filppedImage = new Color[image.length][image[0].length];
		for (int i = 0 ; i < filppedImage.length; i++) {
			for (int j = 0 , originColIndex = image[0].length-1 ; j < filppedImage[0].length; j++ , originColIndex--) {
				filppedImage[i][j] = image[i][originColIndex];
			}
		}
		return filppedImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] filppedImage = new Color[image.length][image[0].length];
		for (int i = 0 , originRowIndex = image.length-1; i < filppedImage.length; i++ , originRowIndex--) {
			for (int j = 0 ; j < filppedImage[0].length; j++ ) {
				filppedImage[i][j] = image[originRowIndex][j];
			}
		}
		return filppedImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		int greyedValue = (int) (0.299*pixel.getRed() + 0.587*pixel.getGreen() + 0.114*pixel.getBlue());
		Color lumPixel  = new Color(greyedValue, greyedValue, greyedValue);
		return lumPixel;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] grayImage = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				grayImage[i][j] = luminance(image[i][j]);
			}
		}
		return grayImage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] scaledImage = new Color[height][width];
		double hsource = image.length;
		double wsource = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				scaledImage[i][j] = image[(int)(i*(hsource/height))][(int) (j*(wsource/width))];
			}
		}		
		return scaledImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int red, green, blue;
    // Compute blended RGB values
    	 red = (int)((alpha * c1.getRed()) + ((1 - alpha) * c2.getRed()));
    	 green = (int)((alpha * c1.getGreen()) + ((1 - alpha) * c2.getGreen()));
    	 blue = (int)((alpha * c1.getBlue()) + ((1 - alpha) * c2.getBlue()));

    // Ensure RGB values are within bounds [0, 255]
    	red = Math.max(0, Math.min(255, red));
    	green = Math.max(0, Math.min(255, green));
    	blue = Math.max(0, Math.min(255, blue));

		Color blendedColor = new Color (red,green,blue);
		return blendedColor;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] blendedImage = new Color[image1.length][image1[0].length];
		for (int i = 0; i < image1.length; i++) {
			for (int j = 0; j < image1[0].length; j++) {
				blendedImage[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return blendedImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		//// Replace this comment with your code
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

