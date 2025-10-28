package com.API;

import com.application.panels.ConnectionScreen;
import com.utils.BitMapImage;
import com.utils.Util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FilterConnector {

    public static final String FILTER_GRAYSCALE = "grayscale";
    public static final String FILTER_INVERT = "invert";
    public static final String FILTER_SMOOTH_SOFT = "smoothFilterSoft";
    public static final String FILTER_SMOOTH_MEDIUM = "smoothFilterMedium";
    public static final String FILTER_SMOOTH_HARD = "smoothFilterHard";
    public static final String REBALANCE_RED = "rebalanceRed";
    public static final String REBALANCE_GREEN = "rebalanceGreen";
    public static final String REBALANCE_BLUE = "rebalanceBlue";
    public static final String REDGREEN = "redOntoGreen";
    public static final String GREENBLUE = "greenOntoBlue";
    public static final String BLUERED = "blueOntoRed";
    public static final String HUE_SATURATION = "hueOntoSaturation";
    public static final String SATURATION_LIGHTNESS = "saturationOntoLightness";
    public static final String LIGHTNESS_HUE = "lightnessOntoHue";


    public static BitMapImage requestFilter(String type, BitMapImage image) throws IOException, InterruptedException {

        String remote = ConnectionScreen.getInstance().getRemote();

        String imageJson = Util.arrayToJson(image.getRgb());

        String jsonBody = String.format("{\"type\":\"" + type + "\",\"image\":\"%s\"}", imageJson);

        // Send request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(remote + "/filter"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String imageString = response.body();

        int[][][] rgb = Util.parse3DArray(imageString);

        return new BitMapImage(rgb);
    }

}
