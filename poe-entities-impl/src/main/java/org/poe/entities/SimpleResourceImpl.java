package org.poe.entities;

import org.sikuli.api.visual.element.ImageElement;

/**
 * @author Yevhen Fabizhevskyi
 * @date 27.03.2016.
 */
public class SimpleResourceImpl extends SimpleResource {

    private ImageElement imageElement;

    public void initialize(ImageElement imageElement) {
        this.imageElement = imageElement;
    }

    public void invoke() {

    }

    public ImageElement getImageElement() {
        return imageElement;
    }
}
