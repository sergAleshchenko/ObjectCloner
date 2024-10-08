package lightspeed.entities;

import lombok.*;
import java.util.List;

/**
 * @author Sergei Aleshchenko
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Car {
    private String model;
    private int year;
    private CarEngine engine;
    private List<Wheel> wheels;
}
