package com.seekhelp.desktop;

import javafx.scene.Scene;
import java.io.InputStream;
import java.net.URL;

public class CSSLoader {
    
    public static boolean loadCSS(Scene scene, String cssPath) {
        try {
            // Try multiple methods to load CSS
            URL cssUrl = CSSLoader.class.getResource(cssPath);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("CSS loaded successfully: " + cssUrl.toExternalForm());
                return true;
            }
            
            // Try alternative path
            cssUrl = CSSLoader.class.getClassLoader().getResource(cssPath.substring(1));
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("CSS loaded with ClassLoader: " + cssUrl.toExternalForm());
                return true;
            }
            
            // Try as stream
            InputStream cssStream = CSSLoader.class.getResourceAsStream(cssPath);
            if (cssStream != null) {
                cssStream.close();
                // If we can read it as stream, try the original method again
                cssUrl = CSSLoader.class.getResource(cssPath);
                if (cssUrl != null) {
                    scene.getStylesheets().add(cssUrl.toExternalForm());
                    System.out.println("CSS loaded via stream check: " + cssUrl.toExternalForm());
                    return true;
                }
            }
            
            System.err.println("CSS file not found: " + cssPath);
            return false;
            
        } catch (Exception e) {
            System.err.println("Error loading CSS: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public static void loadDefaultStyles(Scene scene) {
        // Try multiple CSS paths
        String[] cssPaths = {
            "/css/styles.css",
            "css/styles.css",
            "/styles.css",
            "styles.css"
        };
        
        boolean loaded = false;
        for (String path : cssPaths) {
            if (loadCSS(scene, path)) {
                loaded = true;
                break;
            }
        }
        
        if (!loaded) {
            System.err.println("Failed to load any CSS file. Application will use default styling.");
        }
        
        // Force CSS refresh
        scene.getStylesheets().clear();
        for (String path : cssPaths) {
            if (loadCSS(scene, path)) {
                loaded = true;
                break;
            }
        }
        
        // Add fallback inline styles if CSS still fails
        if (!loaded) {
            System.err.println("Adding fallback inline styles...");
            addFallbackStyles(scene);
        }
    }
    
    private static void addFallbackStyles(Scene scene) {
        // Add some basic fallback styles to the root node
        if (scene.getRoot() != null) {
            scene.getRoot().setStyle("""
                -fx-background-color: #f0fdf4;
                -fx-font-family: 'System';
            """);
        }
    }
}
