import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;
import com.epicbot.api.shared.script.Script;
import com.epicbot.api.shared.script.ScriptManifest;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ScriptManifest(name = "Area Maker", gameType = GameType.OS)
public class AreaMaker extends Script {
    private final List<Tile> tiles = new ArrayList<>();

    @Override
    public boolean onStart(String... strings) {
        new GUI(getAPIContext(), tiles);
        return true;
    }

    @Override
    protected void onPaint(Graphics2D g, APIContext ctx) {
        Color outlineColor;
        Color fillColor;

        // selected tiles in solid white
        outlineColor = new Color(255, 255, 255);
        fillColor = new Color(255, 255, 255);
        for (Tile tile : tiles) {
            drawTile(g, tile, outlineColor, fillColor);
        }

        // generated area in transparent green
        Area area = new Area(tiles.toArray(new Tile[0]));
        outlineColor = new Color(0, 255, 0);
        fillColor = new Color(0, 255, 0, 128);
        for (Tile tile : area.getTiles()) {
            drawTile(g, tile, outlineColor, fillColor);
        }

    }

    private void drawTile(Graphics2D g, Tile tile, Color outlineColor, Color fillColor) {
        Polygon outline;
        if (tile != null && (outline = tile.getBounds()) != null) {
            g.setColor(outlineColor);
            g.drawPolygon(outline);
            g.setColor(fillColor);
            g.fillPolygon(outline);
        }
    }
}
