package pl.me.jhonylemon;

import pl.me.jhonylemon.vial.Vials;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final Movement movement;
    private final Node parent;
    private final List<Node> children;
    private final Long depth;

    public Node(Movement movement, Node parent, Long depth) {
        this.movement = movement;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.depth = depth;
    }

    public Long childDepth() {
        return depth+1;
    }

    public Long getDepth() {
        return depth;
    }

    public void add(Node children) {
        this.children.add(children);
    }

    public boolean isChildrenEmpty() {
        return this.children.isEmpty();
    }

    private void remove(Node children) {
        this.children.remove(children);
    }

    public void removeFromParent() {
        this.parent.remove(this);
    }

    public Node getChild(int index) {
        return children.get(index);
    }

    public boolean isSolution(Vials vials) {
        return children.isEmpty() && vials.isSameColor();
    }

    public Node getParent() {
        return parent;
    }

    public Movement getMovement() {
        return movement;
    }

}
