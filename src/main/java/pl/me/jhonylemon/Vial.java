package pl.me.jhonylemon;

import com.diogonunes.jcolor.Attribute;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Vial {
    private final Integer index;
    private final Deque<Color> vialStack;
    private final Integer maxCapacity;

    public Vial(Integer maxCapacity, Integer index) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than zero");
        }
        this.maxCapacity = maxCapacity;
        this.vialStack = new ArrayDeque<>(maxCapacity);
        this.index = index;
    }

    public Vial(List<Color> initialColors, Integer maxCapacity, Integer index) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than zero");
        }
        if (initialColors.size() > maxCapacity) {
            throw new IllegalArgumentException("Initial colors exceed max capacity");
        }
        this.vialStack = new ArrayDeque<>(initialColors);
        this.maxCapacity = maxCapacity;
        this.index = index;
    }

    public List<Color> get() {
        if (isSameColor()) {
            throw new IllegalStateException("Vial is already sorted");
        }
        List<Color> colors = new ArrayList<>();
        Color color = vialStack.peek();
        while (!vialStack.isEmpty() && vialStack.peek().equals(color)) {
            colors.add(vialStack.pop());
        }
        return colors;
    }

    public Integer getIndex() {
        return index;
    }

    public void add(Color color) {
        if (vialStack.size() >= maxCapacity) {
            throw new IllegalStateException("Vial is full");
        }
        if (!vialStack.isEmpty() && !vialStack.peek().equals(color)) {
            throw new IllegalStateException("Previous color must be the same");
        }
        vialStack.push(color);
    }

    public Color peek() {
        return vialStack.peek();
    }

    public void add(List<Color> color) {
        if (color.size() > maxCapacity) {
            throw new IllegalStateException("Vial is smaller than the colors");
        }
        if (color.isEmpty()) {
            throw new IllegalArgumentException("Colors cannot be empty");
        }
        Color firstColor = color.getFirst();
        if (!color.stream().allMatch(firstColor::equals)) {
            throw new IllegalArgumentException("All colors must be the same");
        }
        if (!vialStack.isEmpty() && !vialStack.peek().equals(firstColor)) {
            throw new IllegalStateException("Previous color must be the same");
        }
        if (color.size() + vialStack.size() > maxCapacity) {
            throw new IllegalStateException("Vial is full");
        }
        color.forEach(vialStack::push);
    }

    public boolean isSameColor() {
        if (vialStack.isEmpty()) {
            return true;
        }
        if (vialStack.size() != maxCapacity) {
            return false;
        }
        Color color = vialStack.peek();
        for (Color otherMarbles : vialStack) {
            if (!otherMarbles.equals(color)) {
                return false;
            }
        }
        return true;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public List<Attribute> getPrintableContents() {
        return vialStack.stream().map(Color::getColor).toList();
    }

    protected Vial clone() {
        return new Vial(this.vialStack.stream().toList(), this.maxCapacity, this.index);
    }
}
