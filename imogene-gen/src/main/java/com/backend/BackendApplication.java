package com.backend;

import com.GA.ImageGenerator;
import com.GA.generation.RandomColorGeneration;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.utils.BitMapImage;
import com.utils.ImageUtils;
import com.utils.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.Executors;

public class BackendApplication {

    public static void main(String[] args) throws IOException {
        int portNumber = 8080;
        // Accept other external connections (Render) 
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", portNumber), 0);

        // Assign handlers to endpoints
        server.createContext("/", new RootHandler());
        server.createContext("/generate", new GenerationHandler());
        server.createContext("/filter", new FilterHandler());

        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();

        System.out.println("Server started on http://localhost:" + portNumber);
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<!DOCTYPE html>\n" + // TODO: move to a separate html file
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <title>Imogene API</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\t<p>\n" +
                    "\t\tWelcome to Imogene API.\n" +
                    "\t</p>\n" +
                    "</body>\n" +
                    "</html>";
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }

        }
    }

    static class GenerationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Enables CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");

            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");


            try {
                if (!"GET".equals(exchange.getRequestMethod())) {
                    exchange.sendResponseHeaders(405, -1); // Method not allowed
                    return;
                }

                // Parse query parameters
                Map<String, String> params = Util.queryToMap(exchange.getRequestURI().getQuery());
                int height = Integer.parseInt(params.getOrDefault("height", "0"));
                int width = Integer.parseInt(params.getOrDefault("width", "0"));
                String type = params.getOrDefault("type", "");

                BitMapImage image = new BitMapImage(width, height);

                if (type.equalsIgnoreCase("randomBitmap"))
                    image = ImageGenerator.randomPixels(height, width);

                if (type.equalsIgnoreCase("randomColour"))
                    image = (new RandomColorGeneration()).generate(height, width).getImage();


                String json = Util.arrayToJson(image.getRgb());
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, json.getBytes().length);

                OutputStream os = exchange.getResponseBody();
                os.write(json.getBytes());
                os.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class FilterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                if (!"POST".equals(exchange.getRequestMethod())) {
                    exchange.sendResponseHeaders(405, -1); // Method not allowed
                    return;
                }

                // Read request body
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                String type = extractJsonValue(body, "type");

                String imageString = extractJsonValue(body, "image");

                int[][][] rgb = Util.parse3DArray(imageString);
                BitMapImage image = new BitMapImage(rgb);

                if(type.equalsIgnoreCase("grayscale"))
                    image = ImageUtils.grayscale(image);

                if(type.equalsIgnoreCase("invert"))
                    image = ImageUtils.invert(image);

                if(type.equalsIgnoreCase("smoothFilterSoft"))
                    image = ImageUtils.smoothFilter(image, 0.8, 0.025);

                if(type.equalsIgnoreCase("smoothFilterMedium"))
                    image = ImageUtils.smoothFilter(image, 0.5, 0.0625);

                if(type.equalsIgnoreCase("smoothFilterHard"))
                    image = ImageUtils.smoothFilter(image, 0.2, 0.1);

                if(type.equalsIgnoreCase("rebalanceRed"))
                    image = ImageUtils.rgbBalancing(image, 0.6, 0.2, 0.2);

                if(type.equalsIgnoreCase("rebalanceGreen"))
                    image = ImageUtils.rgbBalancing(image, 0.2, 0.6, 0.2);

                if(type.equalsIgnoreCase("rebalanceBlue"))
                    image = ImageUtils.rgbBalancing(image, 0.2, 0.2, 0.6);

                if(type.equalsIgnoreCase("redOntoGreen"))
                    image = ImageUtils.spectralProjection(image, "Red", "Green");

                if(type.equalsIgnoreCase("greenOntoBlue"))
                    image = ImageUtils.spectralProjection(image, "Green", "Blue");

                if(type.equalsIgnoreCase("blueOntoRed"))
                    image = ImageUtils.spectralProjection(image, "Blue", "Red");

                if(type.equalsIgnoreCase("hueOntoSaturation"))
                    image = ImageUtils.spectralProjection(image, "Hue", "Saturation");

                if(type.equalsIgnoreCase("saturationOntoLightness"))
                    image = ImageUtils.spectralProjection(image, "Saturation", "Lightness");

                if(type.equalsIgnoreCase("lightnessOntoHue"))
                    image = ImageUtils.spectralProjection(image, "Lightness", "Hue");

                String json = Util.arrayToJson(image.getRgb());
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, json.getBytes().length);

                OutputStream os = exchange.getResponseBody();
                os.write(json.getBytes());
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String extractJsonValue(String json, String key) {
        String search = "\"" + key + "\"";
        int start = json.indexOf(search);
        if (start == -1) return null;
        int colon = json.indexOf(':', start);
        int firstQuote = json.indexOf('"', colon + 1);
        if (firstQuote == -1) return null;
        int secondQuote = json.indexOf('"', firstQuote + 1);
        if (secondQuote == -1) return null;
        return json.substring(firstQuote + 1, secondQuote);
    }
}