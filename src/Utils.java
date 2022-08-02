import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String areaConstructorStr(List<Tile> tiles) {
        return "new Area(" + String.join(", ", tileConstructorStrList(tiles)) + ")";
    }

    public static String tileArrayConstructorStr(List<Tile> tiles) {
        return "new Tile[] {" + String.join(", ", tileConstructorStrList(tiles)) + "}";
    }

    public static List<String> tileConstructorStrList(List<Tile> tiles) {
        return tiles.stream().map(tile -> "new Tile(" + tile.getX() + "," + tile.getY() + "," + tile.getPlane() + ")").collect(Collectors.toList());
    }

    public static Tile getNearestTile(Area area, Tile tile) {
        Tile[] tiles = area.getTiles();
        double minDist = Double.MAX_VALUE;
        Tile nearest = null;
        for (Tile otherTile : tiles) {
            double dist = Point2D.distance(tile.getX(), tile.getY(), otherTile.getX(), otherTile.getY());
            if (dist < minDist) {
                nearest = otherTile;
                minDist = dist;
            }
        }
        return nearest;
    }

    public static List<Tile> expand(List<Tile> tiles) {
        /*
         * Replace tiles that aren't included in the Area with a nearby tile such that it is included.
         */
        Area area = new Area(tiles.toArray(new Tile[0]));
        List<Tile> tilesExpanded = new ArrayList<>();
        if (area.getTiles().length == 0) {
            return tilesExpanded;
        }
        for (Tile tile : tiles) {
            if (area.contains(tile)) {
                tilesExpanded.add(tile);
            } else {
                Tile nearestTile = Utils.getNearestTile(area, tile);
                int newX;
                int newY;
                // expand in the X direction
                if (tile.getX() < nearestTile.getX()) {
                    newX = tile.getX() - 1;
                } else if (tile.getX() > nearestTile.getX()) {
                    newX = tile.getX() + 1;
                } else {
                    newX = tile.getX();
                }
                // expand in the Y direction
                if (tile.getY() < nearestTile.getY()) {
                    newY = tile.getY() - 1;
                } else if (tile.getY() > nearestTile.getY()) {
                    newY = tile.getY() + 1;
                } else {
                    newY = tile.getY();
                }
                tilesExpanded.add(new Tile(newX, newY, tile.getPlane()));
            }
        }
        return tilesExpanded;
    }
}
