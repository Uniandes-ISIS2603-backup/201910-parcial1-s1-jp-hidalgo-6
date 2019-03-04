/*
MIT License

Copyright (c) 2019 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.recipes.test.logic;

import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.ejb.RecipeLogic;
import co.edu.uniandes.csw.recipes.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recipes.persistence.RecipePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica para la receta.
 *
 * @author ISIS2603
 */
@RunWith(Arquillian.class)
public class RecipeLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private RecipeLogic recipeLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<RecipeEntity> data = new ArrayList<>();


    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecipeEntity.class.getPackage())
                .addPackage(RecipeLogic.class.getPackage())
                .addPackage(RecipePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from RecipeEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
       
        for (int i = 0; i < 3; i++) {
            RecipeEntity entity = factory.manufacturePojo(RecipeEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Recipe
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createRecipeTest() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        RecipeEntity result = recipeLogic.createRecipe(newEntity);
        Assert.assertNotNull(result);
        RecipeEntity entity = em.find(RecipeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createRecipeWithInvalidName() throws BusinessLogicException
    {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName(null);
        recipeLogic.createRecipe(newEntity);
    }
    @Test(expected = BusinessLogicException.class)
    public void createRecipeWithSameName() throws BusinessLogicException
    {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        RecipeEntity newEntity1 = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName("nombre");
        newEntity1.setName("nombre");
        recipeLogic.createRecipe(newEntity);
        recipeLogic.createRecipe(newEntity1);
    }
    @Test(expected = BusinessLogicException.class)
    public void createRecipeWithInvalidDescription() throws BusinessLogicException
    {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setDescription(null);
        recipeLogic.createRecipe(newEntity);
    }
}
