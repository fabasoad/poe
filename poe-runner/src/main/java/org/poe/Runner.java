package org.poe;

import org.poe.collectors.ResourceCollector;

/**
 * @author Yevhen Fabizhevskyi
 * @date 26.03.2016.
 */
public class Runner {

    public static void main(String[] args) {
        while (true) ResourceCollector.getInstance().collect("resource");
    }
}
