package tacos.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class Taco {

    @NotNull
    @Size(min = 5, message = "Nazwa musi się składać z przynajmniej 5 znaków.")
    private String name;

    @Size(min = 1, message = "Proszę wybrać chociaż 1 składnik")
    private List<String> ingredients = new ArrayList<>();

}
