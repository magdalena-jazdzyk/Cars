package mk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.model.enums.Colour;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    private String model;
    private double mileage;
    private BigDecimal price;
    private Colour colour;
    private Set<String> components;

    @Override
    public String toString() {
        return "model='" + model + '\'' +
                ", mileage=" + mileage +
                ", price=" + price +
                ", colour=" + colour +
                ", components=" + components
                ;
    }
}
