package pl.me.jhonylemon;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final List<Vial> vials = new ArrayList<>();
    static {
        vials.add(new Vial(List.of(
                Color.LIGHT_GREEN,
                Color.VIOLET,
                Color.LIGHT_PINK,
                Color.LIGHT_BLUE
        ), 4,0));

        vials.add(new Vial(List.of(
                Color.ORANGE,
                Color.DARK_PINK,
                Color.LIGHT_PINK,
                Color.RED
        ), 4,1));

        vials.add(new Vial(List.of(
                Color.RED,
                Color.GREEN,
                Color.DARK_PINK,
                Color.BLUE
        ), 4,2));

        vials.add(new Vial(List.of(
                Color.LIGHT_PINK,
                Color.ORANGE,
                Color.YELLOW,
                Color.YELLOW
        ), 4,3));

        vials.add(new Vial(List.of(
                Color.ORANGE,
                Color.GREEN,
                Color.LIGHT_BLUE,
                Color.GRAY_PINK
        ), 4,4));

        vials.add(new Vial(List.of(
                Color.GRAY,
                Color.ORANGE,
                Color.YELLOW,
                Color.LIGHT_GREEN
        ), 4,5));

        vials.add(new Vial(List.of(
                Color.BLUE,
                Color.GRAY,
                Color.GRAY,
                Color.LIGHT_GREEN
        ), 4,6));

        vials.add(new Vial(List.of(
                Color.BLUE,
                Color.DARK_PINK,
                Color.LIGHT_GREEN,
                Color.GRAY_PINK
        ), 4,7));

        vials.add(new Vial(List.of(
                Color.VIOLET,
                Color.LIGHT_BLUE,
                Color.VIOLET,
                Color.DARK_PINK
        ), 4,8));

        vials.add(new Vial(List.of(
                Color.GREEN,
                Color.VIOLET,
                Color.GREEN,
                Color.GRAY_PINK
        ), 4,9));

        vials.add(new Vial(List.of(
                Color.GRAY,
                Color.YELLOW,
                Color.RED,
                Color.GRAY_PINK
        ), 4,10));

        vials.add(new Vial(List.of(
                Color.LIGHT_BLUE,
                Color.BLUE,
                Color.LIGHT_PINK,
                Color.RED
        ), 4,11));

        vials.add(new Vial(4,12));
        vials.add(new Vial(4,13));
    }

    private static final Vials vialsObject = new Vials(vials);

    private static Vials vis = new Vials(
            List.of(
                    new Vial(List.of(
                            Color.LIGHT_BLUE,
                            Color.DARK_PINK,
                            Color.RED,
                            Color.LIGHT_PINK
                    ), 4,0),
                    new Vial(List.of(
                            Color.RED,
                            Color.LIGHT_BLUE,
                            Color.RED,
                            Color.LIGHT_PINK
                    ), 4,1),
                    new Vial(List.of(
                            Color.LIGHT_BLUE,
                            Color.LIGHT_BLUE,
                            Color.DARK_PINK,
                            Color.LIGHT_PINK
                    ), 4,2),
                    new Vial(List.of(
                            Color.DARK_PINK,
                            Color.DARK_PINK,
                            Color.RED,
                            Color.LIGHT_PINK
                    ), 4,3),
                    new Vial(4,4),
                    new Vial(4,5)
            )
    );

    public static void main(String[] args) {
        Vials vials = vis.copy();
        Vials print = vis.copy();

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
