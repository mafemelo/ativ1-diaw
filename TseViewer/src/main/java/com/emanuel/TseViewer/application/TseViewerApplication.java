package com.emanuel.TseViewer.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.emanuel.TseViewer")
public class TseViewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TseViewerApplication.class, args);
	}

}
