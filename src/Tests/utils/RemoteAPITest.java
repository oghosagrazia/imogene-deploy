package Tests.utils;

import com.API.FilterConnector;
import com.API.GenerationConnector;
import com.application.panels.LeftSidebar;
import com.application.panels.ImageScreen;
import com.backend.BackendApplication;
import com.GA.ImageGenerator;
import com.utils.BitMapImage;
import com.utils.ImageUtils;

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.AfterAll;  
import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import java.util.concurrent.Executors;


public class RemoteAPITest {
	private LeftSidebar leftSidebar;
	private static HttpServer httpServer;
	private static Thread httpServerThread;
	
	@BeforeAll
	public static void setUpFirst() {
		httpServerThread = new Thread(() -> {
			try {
				BackendApplication.main(new String[] {});
			} catch (IOException e) {
				System.out.println("Server is already running");
			}
		});
		
		try {
			httpServerThread.start();
			Thread.sleep(1000);//Need to make sure this is long enough for the server to start.
			System.out.println("Server is up and running for testing");
		} catch (Exception e) {
			System.out.println("There was a problem starting the server\n" + e.getMessage());
		}
	}
	
	@BeforeEach
	public void setUp() {
		BitMapImage testImage = new BitMapImage(ImageScreen.currentImageWidth, ImageScreen.currentImageHeight);
		testImage.resetToWhite();
		ImageScreen.currentImage = testImage;
		leftSidebar = LeftSidebar.getInstance();
	}
	
	@AfterEach
	public void tearDown() {
		ImageScreen.currentImage = null;
	}
	
	@AfterAll
	public void tearDownLast() {
		if (httpServer != null) {
			try {
				httpServer.stop(0);
				System.out.println("The server has stopped");
			} catch (Exception e) {
				System.out.println("Something has gone seriously wrong: " + e.getMessage());
			}
		if (httpServerThread != null) httpServerThread.interrupt();

		}
	}
	
	
	
}
