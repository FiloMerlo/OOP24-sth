package sth.engine.tiles;

public record Tile (int spriteID, TileType t){
    public boolean isSolid() {
        return t == TileType.SOLID;
    }
}
