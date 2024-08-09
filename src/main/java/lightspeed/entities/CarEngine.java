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
public class CarEngine {
    private String model;
    private int volume;
}
