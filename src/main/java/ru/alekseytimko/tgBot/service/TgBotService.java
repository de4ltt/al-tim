package ru.alekseytimko.tgBot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alekseytimko.tgBot.entity.Category;
import ru.alekseytimko.tgBot.entity.Client;
import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.entity.Product;

import java.util.List;
import java.util.Optional;

@Component
public class TgBotService {

    private final TelegramBot bot = new TelegramBot("7588247044:AAF3xBdZf8XrjqNUzroA3QKwg9ttnNp1nBw");

    private final ProductService productService;
    private final ClientService clientService;
    private final CategoryService categoryService;
    private final ClientOrderService clientOrderService;
    private final OrderProductService orderProductService;

    @Autowired
    public TgBotService(ProductService productService,
                        ClientService clientService,
                        CategoryService categoryService,
                        ClientOrderService clientOrderService,
                        OrderProductService orderProductService) {
        this.productService = productService;
        this.clientService = clientService;
        this.categoryService = categoryService;
        this.clientOrderService = clientOrderService;
        this.orderProductService = orderProductService;
    }

    @PostConstruct
    private void start() {
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void processUpdate(Update update) {
        if (update.message() != null && update.message().text() != null) {
            handleTextMessage(update.message());
        } else if (update.callbackQuery() != null) {
            handleCallback(update.callbackQuery());
        }
    }

    private void handleTextMessage(Message message) {
        long externalId = message.from().id();
        String fullName = message.from().firstName() + " " + Optional.ofNullable(message.from().lastName()).orElse("");
        String phone = Optional.ofNullable(message.contact()).map(Contact::phoneNumber).orElse("unknown");
        String address = "unknown";

        Client client = clientService.getByExternalId(externalId);
        if (client == null) {
            Client newClient = new Client();
            newClient.setExternalId(externalId);
            newClient.setFullName(fullName.trim());
            newClient.setPhoneNumber(phone);
            newClient.setAddress(address);
            client = clientService.save(newClient);
        }

        ClientOrder activeOrder = clientOrderService.getActiveOrder(client);
        if (activeOrder == null)
            clientOrderService.createOrder(client.getId());

        String text = message.text();
        long chatId = message.chat().id();

        switch (text) {
            case "/start", "В основное меню" -> {
                showMainMenu(chatId);
            }
            case "Категории" -> {
                showCategories(chatId, null);
            }
            case "Оформить заказ" -> {
                handleOrderConfirmation(chatId, client);
            }
            default -> {
                Category cat = categoryService.getCategoryByName(text);

                if (cat != null) {
                    showCategories(chatId, cat.getId());
                    if (cat.getParent() != null)
                        showCategoryProducts(chatId, cat);
                } else
                    bot.execute(new SendMessage(chatId, "Неизвестная команда"));
            }
        }
    }

    private void showMainMenu(long chatId) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(
                new KeyboardButton("Категории")
        );
        markup.addRow(new KeyboardButton("Оформить заказ"));
        markup.resizeKeyboard(true);

        bot.execute(new SendMessage(chatId, "Выберите действие").replyMarkup(markup));
    }

    private void showCategories(long chatId, Long parentId) {
        List<Category> categories = categoryService.getCategoriesByParentId(parentId);
        List<KeyboardButton> buttons = categories.stream()
                .map(cat -> new KeyboardButton(cat.getName()))
                .toList();

        if (buttons.isEmpty()) return;

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(buttons.toArray(KeyboardButton[]::new));
        markup.resizeKeyboard(true);

        if (parentId != null)
            markup.addRow(new KeyboardButton("В основное меню"));

        markup.addRow(new KeyboardButton("Оформить заказ"));
        bot.execute(new SendMessage(chatId, "Категории:").replyMarkup(markup));
    }

    public void showCategoryProducts(long chatId, Category category) {
        List<Product> products = productService.getProductsByCategoryId(category.getId());
        if (products.isEmpty()) return;

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        for (Product product : products) {
            InlineKeyboardButton button = new InlineKeyboardButton(
                    String.format("%s — %.2f₽", product.getName(), product.getPrice())
            ).callbackData("product:" + product.getId());
            markup.addRow(button);
        }

        bot.execute(new SendMessage(chatId, "Товары:").replyMarkup(markup));
    }

    private void handleCallback(CallbackQuery query) {
        String data = query.data();
        long chatId = query.message().chat().id();
        long externalId = query.from().id();

        Client client = clientService.getByExternalId(externalId);
        ClientOrder order = clientOrderService.getActiveOrder(client);

        System.out.println(order);

        if (order == null)
            return;

        if (data.startsWith("product:")) {
            Long productId = Long.parseLong(data.replace("product:", ""));

            Product product = productService.getProductById(productId);

            orderProductService.addProductToOrder(order, product);
            String msg = String.format("✅ %s добавлен в заказ. Цена: %.2f₽", product.getName(), product.getPrice());
            bot.execute(new SendMessage(chatId, msg));
        }
    }

    private void handleOrderConfirmation(long chatId, Client client) {
        ClientOrder order = clientOrderService.getActiveOrder(client);
        if (order == null) {
            bot.execute(new SendMessage(chatId, "У вас нет активного заказа."));
            return;
        }

        List<Product> products = orderProductService.getProductsInOrder(order);
        if (products.isEmpty()) {
            bot.execute(new SendMessage(chatId, "Ваш заказ пуст."));
            return;
        }

        double total = products.stream().mapToDouble(Product::getPrice).sum();

        StringBuilder summary = new StringBuilder("🧾 Ваш заказ оформлен:\n");
        for (Product p : products) {
            summary.append("- ").append(p.getName()).append(" — ").append(p.getPrice()).append("₽\n");
        }
        summary.append("Итого: ").append(total).append("₽");

        clientOrderService.closeOrder(order.getId());
        clientOrderService.createOrder(client.getId());

        bot.execute(new SendMessage(chatId, summary.toString()));
    }
}


