package ru.alekseytimko.tgBot.service;

import ru.alekseytimko.tgBot.entity.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryByName(String name);
    List<Category> getCategoriesByParentId(Long parentId);
}
