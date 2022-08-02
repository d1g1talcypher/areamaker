import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.model.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class GUI extends JFrame {
    private final APIContext ctx;
    private final List<Tile> tiles;

    public GUI(APIContext ctx, List<Tile> tiles) {
        super("Area Maker");
        this.ctx = ctx;
        this.tiles = tiles;

        setSize(500, 500);
        setLocation(50, 50);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        JScrollPane textAreaScrollPane = new JScrollPane(textArea);

        JButton toggleTileButton = new JButton("Toggle tile");
        JButton resetButton = new JButton("Reset");

        toggleTileButton.addActionListener(event -> {
            updateTileList();
            updateTextArea(textArea);
        });

        resetButton.addActionListener(event -> {
            tiles.clear();
            updateTextArea(textArea);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(toggleTileButton);
        buttonPanel.add(resetButton);

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout());
        rootPanel.add(textAreaScrollPane, BorderLayout.CENTER);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(rootPanel);
        setVisible(true);

        updateTextArea(textArea);
    }

    private void updateTextArea(JTextArea textArea) {
        textArea.setText(getAreaConstructor() + "\n\n" + getTileArrayConstructor());
    }

    private void updateTileList() {
        Tile playerTile = ctx.localPlayer().getLocation();
        if (!tiles.contains(playerTile)) {
            tiles.add(playerTile);
        } else {
            tiles.remove(playerTile);
        }
    }

    private String getAreaConstructor() {
        List<String> tileConstructors = tiles.stream().map(this::getTileConstructor).collect(Collectors.toList());
        return "new Area(" + String.join(", ", tileConstructors) + ")";
    }

    private String getTileArrayConstructor() {
        List<String> tileConstructors = tiles.stream().map(this::getTileConstructor).collect(Collectors.toList());
        return "new Tile[] {" + String.join(", ", tileConstructors) + "}";
    }

    private String getTileConstructor(Tile tile) {
        return "new Tile(" + tile.getX() + ", " + tile.getY() + ", " + tile.getPlane() + ")";
    }
}
