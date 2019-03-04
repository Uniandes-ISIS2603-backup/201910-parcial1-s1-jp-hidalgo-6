/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.dtos;

import co.edu.uniandes.csw.recipes.entities.IngredienteEntity;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;

/**
 *
 * @author CesarF
 */
public class IngredienteDTO {
    
    private String name;
    private Long calorias;
    private Long id;
    
    public IngredienteDTO(){
    
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

    public Long getCalorias() {
        return calorias;
    }

    public void setCalorias(Long calorias) {
        this.calorias = calorias;
    }

    

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    public IngredienteDTO(IngredienteEntity ingrediente) {
        this.id = ingrediente.getId();
        this.name = ingrediente.getName();
        this.calorias = ingrediente.getClorias();
    }
    
    public IngredienteEntity toEntity() {
        IngredienteEntity entity = new IngredienteEntity();
        entity.setId(this.id);
        entity.setName(this.name);    
        entity.setClorias(this.calorias);
        return entity;
    }
    
   
    
}
