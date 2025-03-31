package pl.me.jhonylemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static pl.me.jhonylemon.VialPrinter.print;

public class Node {
    private final Vials vials;
    private final Movement movement;
    private final Integer depth;
    private final Node parent;
    private final List<Node> children;

    public Node(Vials vials, Movement movement, Integer depth, Node parent) {
        this.vials = vials;
        this.movement = movement;
        this.depth = depth;
        this.parent = parent;
        this.children = new ArrayList<>();
        generateChildren();
        if (children.isEmpty() && vials.isSameColor()) {
            print(this);
        }
    }

    public Node getParent() {
        return parent;
    }

    public Integer getDepth() {
        return depth;
    }

    public Movement getMovement() {
        return movement;
    }

    public List<Vial> getVials() {
        return vials.getVials();
    }

    public void generateChildren() {
        vials.stream().map(nodeCreation()).forEach(children::add);
    }

    private Function<Map.Entry<Vials, Movement>, Node> nodeCreation() {
        return e -> new Node(
                e.getKey(),
                e.getValue(),
                this.depth+1,
                this
        );
    }

}
