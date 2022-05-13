package com.mars.controller;

import com.mars.gui.GameGui;
import com.mars.gui.alt.GameFrame;
import com.mars.objects.Location;
import com.mars.stats.Stats;
import com.mars.util.CommandProcessor;
import com.mars.util.JSONHandler;
import com.mars.util.TextParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class GameController {
    private final TextParser parser = new TextParser();
    private final CommandProcessor processor = new CommandProcessor();
    private final JSONHandler jsonhandler = new JSONHandler();
    private final Map<String, Location> locationMap = jsonhandler.loadLocationMap();
    private final Stats playerStats = new Stats();
    private final GameFrame gui;
    private Location currentLocation;

    // Puzzles
    private boolean isGhSolved = false;
    private boolean isHydroSolved = false;
    private boolean isReactorSolved = false;
    private boolean isSolarSolved = false;

    public GameController(GameFrame gui) {
        System.out.println("hello");
        this.gui = gui;
        this.currentLocation = locationMap.get("Docking Station");
        gui.setTitleScreenHandler(new TitleScreenHandler());
    }

    class TitleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            gui.createIntroScreen();
            gui.setIntroScreenHandler(new IntroScreenHandler());
        }
    }

    class IntroScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent event){
            gui.createGameScreen();
            gui.addDirectionChoiceButtonListeners(new GameScreenHandler());
            gui.setLocationInfo(currentLocation);
        }
    }

    class GameScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            allPuzzlesCompleted();
            // get text input from field
            String choice = ((JButton) e.getSource()).getText();
            String direction = choice.split(" ")[1];
            String nextRoomName = currentLocation.getDirections().get(direction);
            currentLocation = locationMap.get(nextRoomName);
            // use command parser?
            gui.setLocationInfo(currentLocation);
        }
    }

    boolean allPuzzlesCompleted() {
        return true;
    }
}