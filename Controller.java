package application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
//import javafx.stage.FileChooser;
import utilities.Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
//import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {
	
	@FXML
	private ImageView imageView; // the image display window in the GUI
	
	private Mat image;
	
	private int width;
	private int height;
	private int sampleRate; // sampling frequency
	private int sampleSizeInBits;
	private int numberOfChannels;
	private double[] freq; // frequencies for each particular row
	private int numberOfQuantizionLevels;
	private int numberOfSamplesPerColumn;
	@FXML
	private Slider slider;

	
	
	
	private VideoCapture capture;
	private ScheduledExecutorService timer;
	
	private String fileName;
	
	
	
	@FXML
	private void initialize() {
		// Optional: You should modify the logic so that the user can change these values
		// You may also do some experiments with different values
		width = 32;
		height = 32;
		
		
		sampleRate = 8000;
		sampleSizeInBits = 8;
		numberOfChannels = 1;
		
		numberOfQuantizionLevels = 16;
		
		numberOfSamplesPerColumn = 500;
		
		 slider.setShowTickMarks(true);


		// assign frequencies for each particular row
		freq = new double[height]; // Be sure you understand why it is height rather than width
		freq[height/2-1] = 440.0; // 440KHz - Sound of A (La)
		for (int m = height/2; m < height; m++) {
			freq[m] = freq[m-1] * Math.pow(2, 1.0/12.0); 
		}
		for (int m = height/2-2; m >=0; m--) {
			freq[m] = freq[m+1] * Math.pow(2, -1.0/12.0); 
		}
	}
	@FXML
	protected void getImageFilename(ActionEvent event) throws InterruptedException{
		// This method should return the filename of the image to be played
		// You should insert your code here to allow user to select the file
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open a video File");
		File f = fileChooser.showOpenDialog(null);
		
		if (f != null)
		{
			String ext = f.getName().substring(f.getName().indexOf(".") + 1);

			if (ext.equalsIgnoreCase("mp4") || ext.equalsIgnoreCase("avi")|| ext.equalsIgnoreCase("wmv")|| ext.equalsIgnoreCase("mov"))
			{
				fileName=f.getAbsolutePath();
			}
			else
			{
				System.out.println("file type not correct");
			}
		}
		else {
			System.out.println("file does not exist");
		}	
	}
	
/*	protected void createFrameGrabber() throws InterruptedException {
		if (capture != null && capture.isOpened()) { // the video must be open
			double framePerSecond = capture.get(Videoio.CAP_PROP_FPS);
		// create a runnable to fetch new frames periodically
		Runnable frameGrabber = new Runnable() {
			@Override
			public void run() {
				Mat frame = new Mat();
					if (capture.read(frame)) { // decode successfully
						Image im = Utilities.mat2Image(frame);
						Utilities.onFXThread(imageView.imageProperty(), im);
						image=frame;
						
						double currentFrameNumber = capture.get(Videoio.CAP_PROP_POS_FRAMES); ---------------
						double totalFrameCount = capture.get(Videoio.CAP_PROP_FRAME_COUNT); ---------------
						slider.setValue(currentFrameNumber / totalFrameCount * (slider.getMax() - slider.getMin()));
					
						try {
							playImage();
						} catch (LineUnavailableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} 
						
					else { // reach the end of the video
						capture.set(Videoio.CAP_PROP_POS_FRAMES, 0);
					}
				}
			};
		// terminate the timer if it is running
		
			if (timer != null && !timer.isShutdown()) {
				timer.shutdown();
				timer.awaitTermination(Math.round(1000/framePerSecond), TimeUnit.MILLISECONDS);
			}
		// run the frame grabber
			timer = Executors.newSingleThreadScheduledExecutor();
			timer.scheduleAtFixedRate(frameGrabber, 0, Math.round(1000/framePerSecond), TimeUnit.MILLISECONDS);
		}
	}*/
	
	
	
	
	
	@FXML
	protected void openImage(ActionEvent event) throws InterruptedException {
		// This method opens an image and display it using the GUI
		// You should modify the logic so that it opens and displays a video
		
		// ----------Assume it is copying center-column 
		
		
		capture = new VideoCapture(fileName); // open video file
		if (capture.isOpened()) 
		{ // open successfully		
		//createFrameGrabber();
			
			// total frame number
			int totalFrameCount = (int)capture.get(Videoio.CAP_PROP_FRAME_COUNT); 
			
			//create matrix: frame number X column number
			Mat sti_hist = new Mat(totalFrameCount, 32 );
			
			
			
			
			
			
			
			
		}
		
		// You don't have to understand how mat2Image() works. 
		// In short, it converts the image from the Mat format to the Image format
		// The Mat format is used by the opencv library, and the Image format is used by JavaFX
		// BTW, you should be able to explain briefly what opencv and JavaFX are after finishing this assignment
	}

	@FXML
	protected void playImage(/*ActionEvent event*/) throws LineUnavailableException {
		// This method "plays" the image opened by the user
		// You should modify the logic so that it plays a video rather than an image
		
           //image = frame;
         //}
		if (image != null) {
			// convert the image from RGB to grayscale

			
			// resize the image
			Mat resizedImage = new Mat();
			Imgproc.resize(image, resizedImage, new Size(width, height));
			
			// quantization
			double[][] roundedImage = new double[resizedImage.rows()][resizedImage.cols()];
			for (int row = 0; row < resizedImage.rows(); row++) {
				for (int col = 0; col < resizedImage.cols(); col++) {
					roundedImage[row][col] = (double)Math.floor(resizedImage.get(row, col)[0]/numberOfQuantizionLevels) / numberOfQuantizionLevels;
				}
			}
			
/*			// I used an AudioFormat object and a SourceDataLine object to perform audio output. Feel free to try other options
	        AudioFormat audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, numberOfChannels, true, true);
            SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
            sourceDataLine.open(audioFormat, sampleRate);
            sourceDataLine.start();
            byte[] click= new byte[numberOfSamplesPerColumn];
            
            for (int col = 0; col < width; col++) {
            	byte[] audioBuffer = new byte[numberOfSamplesPerColumn];
            	
            	for (int t = 1; t <= numberOfSamplesPerColumn; t++) {
            		double signal = 0;
            		double sig=0;
                	for (int row = 0; row < height; row++) {
                		int m = height - row - 1; // Be sure you understand why it is height rather width, and why we subtract 1 
                		int time = t + col * numberOfSamplesPerColumn;
                		double ss = Math.sin(2 * Math.PI * freq[m] * (double)time/sampleRate);
                		double cc= Math.sin(2*Math.PI *50* (double)time/sampleRate);
                		signal += roundedImage[row][col] * ss;
                		sig+=roundedImage[row][col] * cc;
                	}
                	double normalizedSignal = signal / height; // signal: [-height, height];  normalizedSignal: [-1, 1]
                	double normalizedSig = sig / height;
                	audioBuffer[t-1] = (byte) (normalizedSignal*0x7F); // Be sure you understand what the weird number 0x7F is for
                	click[t-1]=(byte) (normalizedSig*0x7F);
            	}
            	sourceDataLine.write(audioBuffer, 0, numberOfSamplesPerColumn);
            }
            sourceDataLine.write(click,0,numberOfSamplesPerColumn);
            sourceDataLine.drain();
            sourceDataLine.close();
		} else {
			// What should you do here?
			System.out.println("fail to play video");
			
		}*/
	} 
	
	
	

}
