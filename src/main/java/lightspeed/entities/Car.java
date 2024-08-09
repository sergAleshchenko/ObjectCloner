package lightspeed.entities;

import lombok.*;

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
}
