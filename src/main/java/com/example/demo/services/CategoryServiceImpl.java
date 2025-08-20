package com.example.demo.services;

import com.example.demo.Entity.Category;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.CategoryRepo;
import com.example.demo.exceptions.APIException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.CategoryDTO;
import com.example.demo.payloads.CategoryResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Transactional
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(Category category) {
        Category savedCategory = categoryRepo.findByCategoryName(category.getCategoryName());

        if(savedCategory != null){
            throw new APIException("Category with the name '"+category.getCategoryName()+"' already exists!!!");
        }
        savedCategory =categoryRepo.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortBy.equalsIgnoreCase("asc")?Sort.by(sortOrder).ascending():Sort.by(sortBy).descending();

        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category> pageCategory= categoryRepo.findAll(pageDetails);

        List<Category> categories =pageCategory.getContent();

        List<CategoryDTO>categoryDTOs=categories.stream().map(c->modelMapper.map(c, CategoryDTO.class)).toList();

        CategoryResponse categoryResponse= new CategoryResponse();
        categoryResponse.setContent(categoryDTOs);
        categoryResponse.setLastPage(pageCategory.isLast());
        categoryResponse.setPageNumber(pageCategory.getNumber());
        categoryResponse.setPageSize(pageCategory.getSize());
        categoryResponse.setTotalPages(pageCategory.getTotalPages());
        categoryResponse.setTotalElements(pageCategory.getTotalElements());

        return categoryResponse;
    }

    @Override
    public CategoryDTO updateCategory(Category category, Long categoryId) {
        Category savedCategory = categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        category.setCategoryId(categoryId);
        savedCategory=categoryRepo.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category= categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        List<Product>products=category.getProducts();

        products.forEach(product -> {
            productService.deleteProduct(product.getProductId());
        });

        categoryRepo.delete(category);

        return "Category with categoryId: "+categoryId+" deleted successfully!";
    }
}
