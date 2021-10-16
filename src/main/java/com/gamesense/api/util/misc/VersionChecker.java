package com.gamesense.api.util.misc;

import com.gamesense.client.GameSense;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

/**
 * @author Hoosiers
 * @since 12/15/2020
 */
public class VersionChecker {
    private static final String URL_CHECKER = "this is causing my fucking game to be hung up idc if it makes a npm lol";

    public static void init(String version) {
        if (version.startsWith("d")) {
            return;
        }

        try {
            Scanner scanner = new Scanner(new URL(URL_CHECKER).openStream());
            String grabbedVersion = scanner.next();

            if (!version.equalsIgnoreCase(grabbedVersion)) {
                generatePopUp(grabbedVersion);
            }
        } catch (IOException ignored) {
        }
    }

    //thank god for stack overflow... https://stackoverflow.com/questions/8348063/clickable-links-in-joptionpane
    private static void generatePopUp(String newVersion) {
        JLabel label = new JLabel();
        Font font = label.getFont();

        String style = "font-family:" + font.getFamily() + ";" + "font-weight:" + (font.isBold() ? "bold" : "normal") + ";" + "font-size:" + font.getSize() + "pt;";
        JEditorPane editorPane = new JEditorPane("text/html", "<html><body style=\"" + style + "\">" + "Version outdated! Download the latest (" + newVersion + ") " + "<a href=\"https://github.com/HausemasterIssue/spidersense/releases/\">HERE</a>" + "!" + "</body></html>");

        editorPane.addHyperlinkListener(event -> {
            if (event.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                        desktop.browse(event.getURL().toURI());
                    } else {
                        System.out.println("Couldn't open URL because of desktop not supporting the 'BROWSE' action.");
                    }
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        editorPane.setEditable(false);
        editorPane.setBackground(label.getBackground());
        JOptionPane.showMessageDialog(null, editorPane, GameSense.MODNAME + " " + GameSense.MODVER, JOptionPane.WARNING_MESSAGE);
    }
}
