package com.yerti.core.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomModel {

    private List<CustomModelPart> armorstands;


    public CustomModel() {
        this.armorstands = new ArrayList<>();
    }

    public List<CustomModelPart> getArmorstands() {
        return armorstands;
    }

    public void addArmorStand(CustomModelPart model) {
        armorstands.add(model);
    }

    public void removeArmorStand(CustomModelPart model) {
        Optional<CustomModelPart> optional = armorstands.stream().filter(m -> m.equals(model)).findFirst();

        optional.ifPresent(armorStandModel -> armorstands.remove(armorStandModel));
    }

    public void destroy() {
        for (CustomModelPart part : armorstands) {
            part.getArmorStand().remove();
        }
    }
}
