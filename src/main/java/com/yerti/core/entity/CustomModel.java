package com.yerti.core.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomModel {

    private List<CustomModelPart> armorstands;


    /**
     * Creates a new CustomModel
     * Every CustomModel has a list of CustomModelParts
     */
    public CustomModel() {
        this.armorstands = new ArrayList<>();
    }

    /**
     * Retrieves the parts of a CustomModel
     * @return
     */
    public List<CustomModelPart> getArmorstands() {
        return armorstands;
    }

    /**
     * Adds a part to the CustomModel
     * @param model
     */
    public void addArmorStand(CustomModelPart model) {
        armorstands.add(model);
    }

    /**
     * Removes a part from the CustomModel
     * @param model
     */
    public void removeArmorStand(CustomModelPart model) {
        Optional<CustomModelPart> optional = armorstands.stream().filter(m -> m.equals(model)).findFirst();

        optional.ifPresent(armorStandModel -> armorstands.remove(armorStandModel));
    }

    /**
     * Destroys the CustomModel
     */
    public void destroy() {
        for (CustomModelPart part : armorstands) {
            part.getArmorStand().remove();
        }
    }
}
