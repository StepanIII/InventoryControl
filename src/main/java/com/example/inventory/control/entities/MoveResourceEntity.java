package com.example.inventory.control.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Сущность для таблицы ресурсов перемещения.
 */
@Entity
@Table(name = "MOVE_RESOURCES")
public class MoveResourceEntity {
    /**
     * Идентификатор.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ресурс.
     */
    @ManyToOne
    @JoinColumn(name = "RESOURCE_ID")
    private ResourceEntity resource;

    /**
     * Перемещение.
     */
    @ManyToOne
    @JoinColumn(name = "MOVE_ID")
    private MoveEntity move;

    /**
     * Количество.
     */
    @Column(name = "ACTUAL_COUNT")
    private Integer count;


    public MoveResourceEntity() {
    }

    public MoveResourceEntity(Long id, ResourceEntity resource, MoveEntity move, Integer count) {
        this.id = id;
        this.resource = resource;
        this.move = move;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public ResourceEntity getResource() {
        return resource;
    }

    public void setResource(ResourceEntity resource) {
        this.resource = resource;
    }

    public MoveEntity getMove() {
        return move;
    }

    public void setMove(MoveEntity move) {
        this.move = move;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
