/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.dtos;

import co.edu.uniandes.csw.recipes.entities.IngredienteEntity;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CesarF
 */
public class RecipeDetailDTO extends RecipeDTO {
    private List<IngredienteDTO> ingredientes;
    public RecipeDetailDTO(){
    
    }
    
    public RecipeDetailDTO(RecipeEntity entity){
        super(entity);
       if(entity.getIngredientes()!=null)
       {
           ingredientes=new ArrayList<>();
           for(IngredienteEntity ie:entity.getIngredientes())
           {
               ingredientes.add(new IngredienteDTO(ie));
           }
       }
    }
    

    public List<IngredienteDTO> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteDTO> ingredientes) {
        this.ingredientes = ingredientes;
    }
    public RecipeEntity toEntity()
    {
        RecipeEntity res = super.toEntity();
         if(ingredientes!=null)
        {
            List<IngredienteEntity> ie = new ArrayList<>();
            for(IngredienteDTO ied: getIngredientes())
            {
                ie.add(ied.toEntity());
            }
        }
         return res;
    }
}
