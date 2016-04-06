package org.poe;

import org.poe.entities.food.FoodManager;
import org.poe.entities.resources.ResourceManager;
import org.poe.entities.validation.ValidationManager;

/**
 * @author Yevhen Fabizhevskyi
 * @date 26.03.2016.
 */
public class Runner {

    public static void main(String[] args) {
        while (true) {
            ValidationManager.validateAnotherClient();
            ValidationManager.validateServerConnectionError();
            ValidationManager.validateError17();

            ResourceManager.collect();
            FoodManager.growCarrot();
        }
    }
}
