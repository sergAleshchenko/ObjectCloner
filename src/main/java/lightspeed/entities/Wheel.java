package lightspeed.entities;

import lombok.*;

/**
 * @author Sergei Aleshchenko
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Wheel {
    private int radius;
    private int width;
}
