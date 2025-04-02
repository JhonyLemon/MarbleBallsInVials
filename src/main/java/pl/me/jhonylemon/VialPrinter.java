package pl.me.jhonylemon;

import com.diogonunes.jcolor.Attribute;
import pl.me.jhonylemon.vial.Vials;

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
            vials.applyMovement(movement);
            print(vials);
            System.out.print("Movement: "+ movement);
        }
    }

    public static void print(Vials vialsObject) {
        List<List<Attribute>> vials = vialsObject.getPrintableContents();

        line();

        if (vials.isEmpty()) {
            System.out.println("No vials to display.");
        } else {
            int maxSize = vials.stream()
                    .mapToInt(List::size)
                    .max()
                    .orElseThrow();
            List<List<Attribute>> printableContents = vials.stream()
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

}
