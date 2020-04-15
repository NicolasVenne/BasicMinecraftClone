package computergraphics.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector2i;

import computergraphics.math.BoxCollider;
import computergraphics.math.Transform;
import computergraphics.math.VisibilityChange;

/**
 * TerrainGenerator
 * Generate infinite terrain using chunks
 */
public class TerrainGenerator implements VisibilityChange {

    public static TerrainGenerator instance;

    public final int viewDistance = 3;
    public final float playerMoveLimitToChunkUpdate = 5f;
    public final float sqrPlayerMoveLimitToChunkUpdate = playerMoveLimitToChunkUpdate * playerMoveLimitToChunkUpdate;

    public HashMap<Vector2i, Chunk> world;
    public HashSet<BoxCollider> colliders;
    public List<Chunk> visibleChunks;

    public Transform player;

    public Vector2f playerPosition;
    public Vector2f playerPositionOld;
    public Vector2i playerInChunkCoordinates;

    

    public TerrainGenerator(Transform player) {
        if(instance == null) {
            instance = this;
        }
        this.player = player;
        world = new HashMap<Vector2i, Chunk>();
        visibleChunks = new ArrayList<Chunk>();
        colliders = new HashSet<BoxCollider>();
        playerPosition = null;
        playerPositionOld = new Vector2f(500,500);
    }

    /**
     * Called by update loop
     * Get the players position to calculate the distance traveled,
     * If the distance is grater then two chunks, update the chunks
     */
    public void Update() {
        playerPosition = new Vector2f(player.position.x, player.position.z);
        Vector2f calc = new Vector2f(0,0);
        playerPositionOld.sub(playerPosition, calc);
        if(calc.lengthSquared() > sqrPlayerMoveLimitToChunkUpdate) {
            playerPositionOld = playerPosition;
            playerInChunkCoordinates = new Vector2i(Math.round(playerPosition.x / Chunk.CHUNK_WIDTH), Math.round(playerPosition.y / Chunk.CHUNK_WIDTH));
            UpdateChunks();
        }
    }

    /**
     * Update the chunks arround the player within a given view distance
     */
    public void UpdateChunks() {
        HashSet<Vector2i> alreadyUpdated = new HashSet<Vector2i>();

        for(int i = visibleChunks.size() - 1; i >= 0; i--) {
            alreadyUpdated.add(new Vector2i(visibleChunks.get(i).coordinates));
            visibleChunks.get(i).UpdateChunk();
        }

        int currentChunkX = Math.round(playerPosition.x / Chunk.CHUNK_WIDTH);
        int currentChunkY = Math.round(playerPosition.y / Chunk.CHUNK_WIDTH);

        for(int yOffset = -viewDistance; yOffset <= viewDistance; yOffset++) {
            for(int xOffset = -viewDistance; xOffset <= viewDistance; xOffset++) {
                Vector2i lookupChunk = new Vector2i(currentChunkX + xOffset, currentChunkY + yOffset);
                if(!alreadyUpdated.contains(lookupChunk)) {
                    if(world.containsKey(lookupChunk)) {
                        world.get(lookupChunk).UpdateChunk();
                    } else {

                        Chunk chunk = new Chunk( new Vector2i(lookupChunk));
                        world.put(new Vector2i( new Vector2i(lookupChunk)), chunk);
                        chunk.register(this);
                        chunk.load();

                    }
                }
            }
        }
    }

    
    /** 
     * Listener for a chunk visibility change
     * @param chunk
     * @param visible
     */
    @Override
    public void OnChunkVisibilityChange(Chunk chunk, boolean visible) {
        if(visible) {
            visibleChunks.add(chunk);
        } else {
            visibleChunks.remove(chunk);
        }
    }
    
}