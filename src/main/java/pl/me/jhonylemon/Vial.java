package pl.me.jhonylemon;

import com.diogonunes.jcolor.Attribute;

import java.util.*;
import java.util.stream.Stream;

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

    public Integer getIndex() {
        return index;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public List<Attribute> getPrintableContents() {
        return vialStack.stream().map(Color::getColor).toList();
    }

    public Integer size() {
        return vialStack.size();
    }

    public Stream<Color> getAll() {
        return vialStack.stream();
    }

    /**
     * Removes list of same colors from the top of the vial.
     * @return {@link List} of {@link Color} removed from the vial.
     */
    public List<Color> pop() {
        if (isFullSameColor()) {
            throw new IllegalStateException("Vial is already sorted");
        }
        List<Color> colors = new ArrayList<>();
        Color color = vialStack.peek();
        while (!vialStack.isEmpty() && vialStack.peek().equals(color)) {
            colors.add(vialStack.pop());
        }
        return colors;
    }

    /**
     * Returns list of same colors from the top of the vial without removing them.
     * @return {@link List} of {@link Color} from the top of the vial.
     */
    public List<Color> peek() {
        if (vialStack.isEmpty()) {
            return Collections.emptyList();
        }
        List<Color> colors = new ArrayList<>();
        Color color = vialStack.peek();
        for(Color c : vialStack) {
            if (c.equals(color)) {
                colors.add(c);
            } else {
                break;
            }
        }
        return colors;
    }

    /**
     * Tries to add list of same colors to the vial.
     * @param color {@link List} of {@link Color} to be added to the vial.
     * @throws IllegalArgumentException When either colors we want to add are empty or no the same as the top of the vial.
     * @throws IllegalStateException When the vial is full or the colors are not the same as the top of the vial.
     */
    public void push(List<Color> color) {
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

    /**
     * Adds list of same colors to the vial without checking if the colors are the same as the top of the vial. Used for reversing moves.
     * @param color {@link List} of {@link Color} to be added to the vial.
     */
    public void add(List<Color> color) {
        color.forEach(vialStack::push);
    }

    /**
     * Removes list of same colors from the vial without checking if the colors are the same as the top of the vial. Used for reversing moves.
     * @param color {@link List} of {@link Color} to be removed from the vial.
     */
    public void remove(List<Color> color) {
        for (Color c : color) {
            vialStack.remove(c);
        }
    }

    /**
     * Checks if the vial contains only one color.
     * @return {@code true} if the vial is sorted, {@code false} otherwise.
     */
    public boolean isSameColor() {
        if (vialStack.isEmpty()) {
            return true;
        }
        Color color = vialStack.peek();
        for (Color otherMarbles : vialStack) {
            if (!otherMarbles.equals(color)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the vial is full and all colors are the same.
     * @return {@code true} if the vial is full and all colors are the same, {@code false} otherwise.
     */
    public boolean isFullSameColor() {
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

    /**
     * Checks if the vial is empty.
     * @return {@code true} if the vial is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return vialStack.isEmpty();
    }

    /**
     * Makes a copy of the vial.
     * @return {@link Vial} object with the same colors.
     */
    protected Vial copy() {
        return new Vial(this.vialStack.stream().toList(), this.maxCapacity, this.index);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vial vial)) return false;
        return Objects.equals(getIndex(), vial.getIndex()) && Objects.equals(vialStack, vial.vialStack) && Objects.equals(getMaxCapacity(), vial.getMaxCapacity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), vialStack, getMaxCapacity());
    }

}
