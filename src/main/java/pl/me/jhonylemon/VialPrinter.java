package pl.me.jhonylemon;

import com.diogonunes.jcolor.Attribute;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

public class VialPrinter {

    private VialPrinter() {}

    private static void line() {
        System.out.print("\n----------------------\n");
    }


    public static void print(Node node) {
        List<Movement> movements = new ArrayList<>();
        movements.add(node.getMovement());
        Node parent = node.getParent();
        while (parent != null) {
            Movement movement = parent.getMovement();
            if (movement != null) {
                movements.add(movement);
            }
            parent = parent.getParent();
        }
        System.out.print(MessageFormat.format("{0} moves", movements.reversed()));
    }

    public static void print(Vials vials, Node node) {
        Deque<Movement> movements = new ArrayDeque<>();
        movements.push(node.getMovement());
        Node parent = node.getParent();
        while (parent != null) {
            Movement movement = parent.getMovement();
            if (movement != null) {
                movements.push(movement);
            }
            parent = parent.getParent();
        }

        for (Movement movement : movements) {
            vials.move(movement.getVialIndexFrom(), movement.getVialIndexTo());
            print(vials);
        }
    }

    public static void print(Vials vialsObject) {
        List<Vial> vials = vialsObject.getVials();
        if (!areSameSize(vials)) {
            throw new AssertionError("Vials are not the same size");
        }

        line();

        if (vials.isEmpty()) {
            System.out.println("No vials to display.");
        } else {
            int maxSize = vials.getFirst().getMaxCapacity();
            List<List<Attribute>> printableContents = vials.stream()
                    .map(Vial::getPrintableContents)
                    .map(pc -> {
                        List<Attribute> attributes = new ArrayList<>(pc);
                        while (attributes.size() < maxSize) {
                            attributes.addFirst(null);
                        }
                        return attributes;
                    })
                    .toList();
            for (int i = -1; i < maxSize; i++) {
                if (i == -1) {
                    for (int j = 0; j < printableContents.size(); j++) {
                        if (j >= 10) {
                            System.out.print(Integer.valueOf(j)+" ");
                        } else {
                            System.out.print(Integer.valueOf(j)+"  ");
                        }
                    }
                } else {
                    for (List<Attribute> printableContent : printableContents) {
                        Attribute attribute = printableContent.size() > i ? printableContent.get(i) : null;
                        if (attribute == null) {
                            System.out.print("X  ");
                        } else {
                            System.out.print(colorize("O  ", attribute));
                        }
                    }
                }
                if (i < maxSize - 1) {
                    System.out.print("\n");
                }
            }
        }

        line();
    }

    private static boolean areSameSize(List<Vial> vials) {
        if (vials.isEmpty()) {
            return true;
        }
        int maxSize = vials.getFirst().getMaxCapacity();
        for (Vial vial : vials) {
            if (vial.getMaxCapacity() != maxSize) {
                return false;
            }
        }
        return true;
    }

}
