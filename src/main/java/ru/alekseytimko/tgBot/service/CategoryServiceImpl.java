package ru.alekseytimko.tgBot.service;

import jakarta.persistence.Access;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alekseytimko.tgBot.entity.Category;
import ru.alekseytimko.tgBot.repository.CategoryRepository;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getCategoriesByParentId(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }
}
