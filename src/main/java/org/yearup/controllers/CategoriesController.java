package org.yearup.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;


@RestController
@RequestMapping("/categpries")
@CrossOrigin
public class CategoriesController
{
    private CategoryDao categoryDao;
    private ProductDao productDao;



    public CategoriesController(CategoryDao categoryDao, ProductDao productDao)
    {
       this.categoryDao = categoryDao;
       this.productDao = productDao;
    }
    @GetMapping
    public List<Category> getAll()
    {

        return categoryDao.getAllCategories();
    }

   @GetMapping("/{id}")
    public Category getById(@PathVariable int id)
    {

        return categoryDao.getById(id);
    }


    @GetMapping("{categoryId}/products")
    public List<Product> listByCategoryId(@PathVariable int categoryId)
    {

       return productDao.listByCategoryId(categoryId);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Category addCategory(@RequestBody Category category)
    {

        return categoryDao.create(category);
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        categoryDao.update(id,category);
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable int id)
    {

        categoryDao.delete(id);
    }
}
