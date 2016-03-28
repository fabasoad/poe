package org.poe;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import java.util.Iterator;

/**
 * @author Yevhen Fabizhevskyi
 * @date 26.03.2016.
 */
public class Runner {

    public static void main(String[] args) {
        Screen screen = new Screen();
        while (true) {
            String target = ClassLoader.getSystemResource("img/simple/1.PNG").getPath();
            Match match = screen.exists(target);
            if (match != null) {
                Iterator<Match> elements;
                try {
                    elements = match.findAll(target);
                } catch (FindFailed e) {
                    System.out.println(e.getMessage());
                    break;
                }
                while (elements.hasNext()) {
                    elements.next().click();
                }
            } else {
                System.out.println("No elements found.");
                break;
            }
        }
    }
}
