import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.model.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private final APIContext ctx;
    private final List<Tile> tiles = new ArrayList<>();
    private final JTextArea textArea;
    private final JCheckBox inclusiveModeCheckbox;

    public GUI(APIContext ctx) {
        super("Area Maker");
        this.ctx = ctx;

        setSize(500, 500);
        setLocation(50, 50);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        JScrollPane textAreaScrollPane = new JScrollPane(textArea);

        inclusiveModeCheckbox = new JCheckBox("Inclusive mode");
        JButton toggleTileButton = new JButton("Toggle tile");
        JButton resetButton = new JButton("Reset");

        inclusiveModeCheckbox.addActionListener(event -> updateTextArea());
        toggleTileButton.addActionListener(event -> {
            toggleTile();
            updateTextArea();
        });

        resetButton.addActionListener(event -> {
            tiles.clear();
            updateTextArea();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(toggleTileButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(inclusiveModeCheckbox);

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout());
        rootPanel.add(textAreaScrollPane, BorderLayout.CENTER);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(rootPanel);
        setVisible(true);

        updateTextArea();
    }

    private void updateTextArea() {
        textArea.setText(Utils.areaConstructorStr(getTiles()) + "\n\n" + Utils.tileArrayConstructorStr(getTiles()));
    }

    private void toggleTile() {
        Tile playerTile = ctx.localPlayer().getLocation();
        if (!tiles.contains(playerTile)) {
            tiles.add(playerTile);
        } else {
            tiles.remove(playerTile);
        }
    }

    public List<Tile> getSelectedTiles() {
        return tiles;
    }

    public List<Tile> getTiles() {
        if (inclusiveModeCheckbox.isSelected()) {
            return Utils.expand(tiles);
        } else {
            return tiles;
        }
    }
}
