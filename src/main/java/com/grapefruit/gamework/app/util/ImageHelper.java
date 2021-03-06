package com.grapefruit.gamework.app.util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.Random;

/**
 * The type Image helper.
 */
public abstract class ImageHelper {

    /**
     * Gets random chunk of image.
     *
     * @param startImage  the start image
     * @param chunkWidth  the chunk width
     * @param chunkHeight the chunk height
     * @return the random chunk of image
     */
    public static Image getRandomChunkOfImage(Image startImage, int chunkWidth, int chunkHeight) {

        Random random = new Random();
        int startX = random.nextInt((int) startImage.getWidth() - chunkWidth);
        int startY = random.nextInt((int) startImage.getHeight() - chunkHeight);

        PixelReader reader = startImage.getPixelReader();
        WritableImage randomizedImage = new WritableImage(reader, startX, startY, chunkWidth, chunkHeight);

        return randomizedImage;
    }

}
