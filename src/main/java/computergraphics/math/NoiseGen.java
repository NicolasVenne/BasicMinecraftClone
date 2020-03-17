package computergraphics.math;

import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector2i;

/**
 * NoiseGen
 */
public class NoiseGen {

    public static long seed = (long)(Math.random() * 400 + 1);

    public static float[][] getNoiseMap(int width, int height, int octaves, float persistance, float lacunarity, int scale, Vector2i offset) {
        OpenSimplexNoise openNoise = new OpenSimplexNoise(seed);
        Random r = new Random();
        r.setSeed(seed);
        

        float[][] noiseMap = new float[width][height];
        Vector2f[] octaveOffsets = new Vector2f[octaves];

        float maxPossibleHeight = 0;
        float amplitude = 1;

        float halfWidth = width / 2f;
        float halfHeight = height / 2f;

        for(int i = 0; i < octaves; i++) {
            float offsetX = ((float)(r.nextFloat() * 20000) - 10000) + (offset.x * width);
            float offsetY = ((float)(r.nextFloat() * 20000) - 10000) + (offset.y * height);
            octaveOffsets[i] = new Vector2f(offsetX, offsetY);

            maxPossibleHeight += amplitude;
            amplitude *= persistance;
        }

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                amplitude = 1;
                float frequency = 1;
                float noiseHeight = 0;

                for(int i = 0; i < octaves; i++) {
                    float pX = (x - halfWidth + octaveOffsets[i].x) / scale * frequency;
                    float pY = (y - halfHeight + octaveOffsets[i].y) / scale * frequency;
                    float perlinValue = (float)openNoise.eval(pX, pY);
                    noiseHeight += perlinValue * amplitude;
                    amplitude *= persistance;
                    frequency *= lacunarity;
                }

                noiseMap[x][y] = noiseHeight;
                float normalizedHeight = (noiseMap[x][y] + 1) / (maxPossibleHeight / 0.9f);
                noiseMap[x][y] = Math.max(0, Math.min(Integer.MAX_VALUE, normalizedHeight));
            }
        }

        return noiseMap;
    }

}