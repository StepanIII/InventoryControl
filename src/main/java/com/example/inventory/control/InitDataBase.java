package com.example.inventory.control;

import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.repositories.ResourceRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitDataBase {

    @Autowired
    private ResourceRepository resourceRepository;

    @PostConstruct
    public void addData() {
        createResources();
    }

    private void createResources() {
        ResourceEntity resourceEntity1 = new ResourceEntity();
        resourceEntity1.setName("Яблоки");
        resourceEntity1.setResourceType(ResourceType.FOOD);
        resourceEntity1.setUnits(Units.KILOGRAM);

        ResourceEntity resourceEntity2 = new ResourceEntity();
        resourceEntity2.setName("Пеленки");
        resourceEntity2.setResourceType(ResourceType.HYGIENE_PRODUCT);
        resourceEntity2.setUnits(Units.THINGS);

        ResourceEntity resourceEntity3 = new ResourceEntity();
        resourceEntity3.setName("Ботинки");
        resourceEntity3.setResourceType(ResourceType.CLOTHING);
        resourceEntity3.setUnits(Units.PAIR);

        resourceRepository.saveAll(List.of(resourceEntity1, resourceEntity2, resourceEntity3));
    }

}
