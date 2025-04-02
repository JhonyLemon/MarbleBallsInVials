package pl.me.jhonylemon;

import pl.me.jhonylemon.vial.Vials;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final Vials VIALS_BIG = Vials.of(
            List.of(
                    Color.LIGHT_GREEN,
                    Color.VIOLET,
                    Color.LIGHT_PINK,
                    Color.LIGHT_BLUE
            ),
            List.of(
                    Color.ORANGE,
                    Color.DARK_PINK,
                    Color.LIGHT_PINK,
                    Color.RED
            ),
            List.of(
                    Color.RED,
                    Color.GREEN,
                    Color.DARK_PINK,
                    Color.BLUE
            ),
            List.of(
                    Color.LIGHT_PINK,
                    Color.ORANGE,
                    Color.YELLOW,
                    Color.YELLOW
            ),
            List.of(
                    Color.ORANGE,
                    Color.GREEN,
                    Color.LIGHT_BLUE,
                    Color.GRAY_PINK
            ),
            List.of(
                    Color.GRAY,
                    Color.ORANGE,
                    Color.YELLOW,
                    Color.LIGHT_GREEN
            ),
            List.of(
                    Color.BLUE,
                    Color.GRAY,
                    Color.GRAY,
                    Color.LIGHT_GREEN
            ),
            List.of(
                    Color.BLUE,
                    Color.DARK_PINK,
                    Color.LIGHT_GREEN,
                    Color.GRAY_PINK
            ),
            List.of(
                    Color.VIOLET,
                    Color.LIGHT_BLUE,
                    Color.VIOLET,
                    Color.DARK_PINK
            ),
            List.of(
                    Color.GREEN,
                    Color.VIOLET,
                    Color.GREEN,
                    Color.GRAY_PINK
            ),
            List.of(
                    Color.GRAY,
                    Color.YELLOW,
                    Color.RED,
                    Color.GRAY_PINK
            ),
            List.of(
                    Color.LIGHT_BLUE,
                    Color.BLUE,
                    Color.LIGHT_PINK,
                    Color.RED
            ),
            Collections.emptyList(),
            Collections.emptyList()
      );

    public static final Vials VIALS_MEDIUM = Vials.of(
            List.of(
                    Color.RED,
                    Color.DARK_PINK,
                    Color.BLUE,
                    Color.GREEN
            ),
            List.of(
                    Color.LIGHT_BLUE,
                    Color.LIGHT_PINK,
                    Color.RED,
                    Color.DARK_PINK
            ),
            List.of(
                    Color.DARK_PINK,
                    Color.LIGHT_PINK,
                    Color.LIGHT_BLUE,
                    Color.LIGHT_PINK
            ),
            List.of(
                    Color.RED,
                    Color.RED,
                    Color.DARK_PINK,
                    Color.LIGHT_PINK
            ),
            List.of(
                    Color.GREEN,
                    Color.LIGHT_BLUE,
                    Color.BLUE,
                    Color.GREEN
            ),
            List.of(
                    Color.LIGHT_BLUE,
                    Color.BLUE,
                    Color.GREEN,
                    Color.BLUE
            ),
            Collections.emptyList(),
            Collections.emptyList()
    );

    public static final Vials VIALS_SMALL_MEDIUM = Vials.of(
            List.of(
                    Color.LIGHT_BLUE,
                    Color.DARK_PINK,
                    Color.RED,
                    Color.LIGHT_PINK
            ),
            List.of(
                    Color.RED,
                    Color.LIGHT_BLUE,
                    Color.RED,
                    Color.LIGHT_PINK
            ),
            List.of(
                    Color.LIGHT_BLUE,
                    Color.LIGHT_BLUE,
                    Color.DARK_PINK,
                    Color.LIGHT_PINK
            ),
            List.of(
                    Color.DARK_PINK,
                    Color.DARK_PINK,
                    Color.RED,
                    Color.LIGHT_PINK
            ),
            Collections.emptyList(),
            Collections.emptyList()
    );

    public static final Vials VIALS_SMALL = Vials.of(
            List.of(
                    Color.LIGHT_BLUE,
                    Color.LIGHT_BLUE,
                    Color.LIGHT_PINK,
                    Color.LIGHT_PINK
            ),
            List.of(
                    Color.RED,
                    Color.RED,
                    Color.LIGHT_BLUE,
                    Color.LIGHT_BLUE
            ),
            List.of(
                    Color.LIGHT_PINK,
                    Color.LIGHT_PINK,
                    Color.RED,
                    Color.RED
            ),
            Collections.emptyList()
    );

    public static void main(String[] args) {
        Vials vials = VIALS_BIG.copy();
        Vials print = VIALS_BIG.copy();

        VialPrinter.print(vials);
        System.out.println("Starting search...");

        Solver solver = new DepthFirstSearch();
        Node node = solver.solve(vials);
        System.out.println("Finished search.");

        if (Objects.isNull(node)) {
            System.out.println("No solution found.");
        } else {
            System.out.println("Solution found:");
            VialPrinter.print(node);
            VialPrinter.print(print, node);
        }
    }

}
