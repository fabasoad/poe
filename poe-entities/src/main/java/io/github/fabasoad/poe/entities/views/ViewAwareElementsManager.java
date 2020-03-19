package io.github.fabasoad.poe.entities.views;

import io.github.fabasoad.poe.entities.ElementsManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ViewAwareElementsManager extends ElementsManager {

    protected abstract ViewType getViewType();

    protected final void trySwitchView() {
        find(getViewType().asElement()).ifPresent(view -> {
            view.click();
            log.info("Switched to {}.", getViewType().asElement().getMiddle());
        });
    }
}
