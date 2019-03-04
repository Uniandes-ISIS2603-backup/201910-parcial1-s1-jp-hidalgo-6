/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.persistence;

import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author CesarF
 */

@Stateless
public class RecipePersistence {
    private static final Logger LOGGER = Logger.getLogger(RecipePersistence.class.getName());
    
    @PersistenceContext(unitName = "recipesPU")
    protected EntityManager em;
    
  
    public RecipeEntity find(Long id) {
        return em.find(RecipeEntity.class, id);
    }
    
   /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param recipeEntity objeto receta que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public RecipeEntity create(RecipeEntity recipeEntity) {
        LOGGER.log(Level.INFO, "Creando una receta nuevo");
        em.persist(recipeEntity);
        LOGGER.log(Level.INFO, "Receta creado");
        return recipeEntity;
    }
    /**
     * Busca si hay algun libro con el Name que se envía de argumento
     *
     * @param name: Name de la editorial que se está buscando
     * @return null si no existe ningun libro con el Name del argumento. Si
     * existe alguno devuelve el primero.
     */
    public RecipeEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando libros por Name ", name);
        // Se crea un query para buscar libros con el Name que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From RecipeEntity e where e.name = :name", RecipeEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<RecipeEntity> sameName = query.getResultList();
        RecipeEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar recetas por nombre ", name);
        return result;
    }

}
