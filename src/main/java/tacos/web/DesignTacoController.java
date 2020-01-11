package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.domain.Ingredient;
import tacos.domain.Order;
import tacos.domain.Taco;
import tacos.domain.Type;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepository) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepository = tacoRepository;
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute( name = "order")
    public Order order() {
        return new Order();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        fetchIngredientsAndPopulateModel(model);
        return "design";
    }

    @PostMapping
    public String processDesign(Model model, @ModelAttribute Order order, @Valid @ModelAttribute("taco") Taco taco, Errors errors) {
        if (errors.hasErrors()) {
            fetchIngredientsAndPopulateModel(model);
            return "design";
        }

        Taco saved = tacoRepository.save(taco);
        order.addTaco(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    private void fetchIngredientsAndPopulateModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);
        Arrays.stream(Type.values()).forEach(type -> model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type)));
    }

}
