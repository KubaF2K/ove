package com.example.main.game;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;

import java.util.LinkedList;

public class LogBox extends TextArea {
    private final LinkedList<String> stringList;
    private final int maxStrings;

    public LogBox(int maxStrings){
        super();
        super.setEditable(false);
        super.setFocusTraversable(false);
        super.setMouseTransparent(true);
        super.setMaxHeight(maxStrings*18);
        this.getStyleClass().add("log-box");
        this.maxStrings = maxStrings;
        stringList = new LinkedList<>();
    }

    public void addLine(String newLine){
        while (stringList.size()>=maxStrings){
            stringList.removeFirst();
        }
        stringList.add(newLine);
        refreshText();
    }

    public void refreshText(){
        StringBuilder newText = new StringBuilder();
        for (String text :
                stringList) {
            newText.append(text).append("\n");
        }
        super.setText(newText.toString());
    }
}
