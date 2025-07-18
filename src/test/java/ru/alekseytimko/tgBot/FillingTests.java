package ru.alekseytimko.tgBot;

import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.alekseytimko.tgBot.entity.Category;
import ru.alekseytimko.tgBot.entity.Product;
import ru.alekseytimko.tgBot.repository.CategoryRepository;
import ru.alekseytimko.tgBot.repository.ProductRepository;

@SpringBootTest
public class FillingTests {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public FillingTests(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Test
    void createCategories() {

        saveCategory("Пицца", null);
        Category rolls = saveCategory("Роллы", null);
        Category burgers = saveCategory("Бургеры", null);
        Category beverages = saveCategory("Напитки", null);

        saveCategory("Классические роллы", rolls);
        saveCategory("Запеченные роллы", rolls);
        saveCategory("Сладкие роллы", rolls);
        saveCategory("Наборы", rolls);

        saveCategory("Классические бургеры", burgers);
        saveCategory("Острые бургеры", burgers);

        saveCategory("Газированные напитки", beverages);
        saveCategory("Энергетические напитки", beverages);
        saveCategory("Соки", beverages);
        saveCategory("Другие", beverages);

    }

    @Test
    void createProducts() {

        Category rolls = categoryRepository.findByName("Классические роллы");

        saveProduct(rolls, "Классические роллы 1", "Описание классических роллов 1", 150.99d);
        saveProduct(rolls, "Классические роллы 2", "Описание классических роллов 2", 260.99d);
        saveProduct(rolls, "Классические роллы 3", "Описание классических роллов 3", 370.99d);

        rolls = categoryRepository.findByName("Запеченные роллы");

        saveProduct(rolls, "Запеченные роллы 1", "Описание запеченых роллов 1", 150.99d);
        saveProduct(rolls, "Запеченные роллы 2", "Описание запеченых роллов 2", 260.99d);
        saveProduct(rolls, "Запеченные роллы 3", "Описание запеченых роллов 3", 370.99d);

        rolls = categoryRepository.findByName("Сладкие роллы");

        saveProduct(rolls, "Сладкие роллы 1", "Описание сладких роллов 1", 150.99d);
        saveProduct(rolls, "Сладкие роллы 2", "Описание сладких роллов 2", 260.99d);
        saveProduct(rolls, "Сладкие роллы 3", "Описание сладких роллов 3", 370.99d);

        rolls = categoryRepository.findByName("Наборы");

        saveProduct(rolls, "Набор 1", "Описание набора 1", 150.99d);
        saveProduct(rolls, "Набор 2", "Описание набора 2", 260.99d);
        saveProduct(rolls, "Набор 3", "Описание набора 3", 370.99d);

        Category burgers = categoryRepository.findByName("Классические бургеры");

        saveProduct(burgers, "Классический бургер 1", "Описание классического бургера 1", 150.99d);
        saveProduct(burgers, "Классический бургер 2", "Описание классического бургера 2", 260.99d);
        saveProduct(burgers, "Классический бургер 3", "Описание классического бургера 3", 370.99d);

        burgers = categoryRepository.findByName("Острые бургеры");

        saveProduct(burgers, "Острый бургер 1", "Описание острого бургера 1", 150.99d);
        saveProduct(burgers, "Острый бургер 2", "Описание острого бургера 2", 260.99d);
        saveProduct(burgers, "Острый бургер 3", "Описание острого бургера 3", 370.99d);

        Category beverages = categoryRepository.findByName("Газированные напитки");

        saveProduct(beverages, "Газированный напиток 1", "Описание газированного напитка 1", 150.99d);
        saveProduct(beverages, "Газированный напиток 2", "Описание газированного напитка 2", 260.99d);
        saveProduct(beverages, "Газированный напиток 3", "Описание газированного напитка 3", 370.99d);

        beverages = categoryRepository.findByName("Энергетические напитки");

        saveProduct(beverages, "Энергетический напиток 1", "Описание энергетического напитка 1", 150.99d);
        saveProduct(beverages, "Энергетический напиток 2", "Описание энергетического напитка 2", 260.99d);
        saveProduct(beverages, "Энергетический напиток 3", "Описание энергетического напитка 3", 370.99d);

        beverages = categoryRepository.findByName("Соки");

        saveProduct(beverages, "Сок 1", "Описание сока 1", 150.99d);
        saveProduct(beverages, "Сок 2", "Описание сока 2", 260.99d);
        saveProduct(beverages, "Сок 3", "Описание сока 3", 370.99d);

        beverages = categoryRepository.findByName("Другие");

        saveProduct(beverages, "Другой напиток 1", "Описание другого напитка 1", 150.99d);
        saveProduct(beverages, "Другой напиток 2", "Описание другого напитка 2", 260.99d);
        saveProduct(beverages, "Другой напиток 3", "Описание другого напитка 3", 370.99d);
    }

    private Category saveCategory(String name, @Nullable Category parent) {
        Category category = new Category();
        category.setName(name);
        category.setParent(parent);
        return categoryRepository.save(category);
    }

    private void saveProduct(Category category, String name, String description, Double price) {
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        productRepository.save(product);
    }
}