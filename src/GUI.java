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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JTextArea textArea = new JTextArea(30, 30);
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        JScrollPane scroller = new JScrollPane(textArea);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addTileButton = new JButton("Add tile");
        JButton resetButton = new JButton("Reset");

        addTileButton.addActionListener(event -> {
            updateTileArray();
            updateTextArea(textArea);
        });

        resetButton.addActionListener(event -> {
            tiles.clear();
            updateTextArea(textArea);
        });

        buttonPanel.add(addTileButton);
        buttonPanel.add(resetButton);
        mainPanel.add(scroller);
        mainPanel.add(buttonPanel);
        getContentPane().add(BorderLayout.CENTER, mainPanel);
        setVisible(true);

        updateTextArea(textArea);
    }

    private void updateTextArea(JTextArea textArea) {
        textArea.setText(getAreaConstructor() + "\n\n" + getTileArrayConstructor());
    }

    private void updateTileArray() {
        Tile playerLoc = ctx.localPlayer().getLocation();
        if (!tiles.contains(playerLoc)) {
            tiles.add(playerLoc);
        } else {
            tiles.remove(playerLoc);
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
