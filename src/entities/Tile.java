package entities;
import game_parts.TileType;
import org.dyn4j.geometry.Rectangle;
public record Tile (TileType type, Rectangle space){}