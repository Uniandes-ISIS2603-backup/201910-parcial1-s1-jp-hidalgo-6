/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.entities;

import javax.persistence.Entity;

/**
 *
 * @author CesarF
 */
@Entity
public class IngredienteEntity extends BaseEntity {
    private String name;
    private Long clorias;
    
    public IngredienteEntity(){
    
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public Long getClorias() {
        return clorias;
    }

    public void setClorias(Long clorias) {
        this.clorias = clorias;
    }

    
}
