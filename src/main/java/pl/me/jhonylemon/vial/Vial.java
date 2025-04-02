package pl.me.jhonylemon.vial;

import com.diogonunes.jcolor.Attribute;
import pl.me.jhonylemon.Color;

import java.util.*;
import java.util.stream.Stream;

class Vial {
    private final Integer index;
    private final Deque<Color> vialStack;
    private final Integer maxCapacity;

    Vial(List<Color> initialColors, Integer maxCapacity, Integer index) {
        this.vialStack = new ArrayDeque<>(initialColors);
        this.maxCapacity = maxCapacity;
        this.index = index;
    }

    Integer getIndex() {
        return index;
    }

    Integer getMaxCapacity() {
        return maxCapacity;
    }

    List<Attribute> getPrintableContents() {
        return vialStack.stream().map(Color::getColor).toList();
    }

    Integer size() {
        return vialStack.size();
    }

    List<Color> getAll() {
        return vialStack.stream().toList();
    }

    /**
     * Checks if the vial can pop.
     * @return {@code true} if the vial can pop, {@code false} otherwise.
     */
    boolean canPop() {
        return !vialStack.isEmpty() && !isFullSameColor();
    }

    /**
     * Removes list of given colors from the vial.
     * @param colors {@link List} of {@link Color} to be removed from the vial.
     */
    void pop(List<Color> colors) {
        colors.forEach(vialStack::remove);
    }

    /**
     * Returns list of same colors from the top of the vial without removing them.
     * @return {@link List} of {@link Color} from the top of the vial.
     */
    public List<Color> peek() {
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
     * Checks if the vial can push the given list of colors.
     * @param colors {@link List} of {@link Color} to be pushed to the vial.
     * @return {@code true} if the vial can push the colors, {@code false} otherwise.
     */
    boolean canPush(List<Color> colors) {
        int size = colors.size();
        return (vialStack.size() + size <= maxCapacity) &&
                !colors.isEmpty() &&
                colors.stream().allMatch(colors.getFirst()::equals) &&
                (vialStack.isEmpty() || Objects.equals(vialStack.peek(), colors.getFirst()));
    }

    /**
     * Add list of colors to the vial.
     * @param color {@link List} of {@link Color} to be added to the vial.
     */
    public void push(List<Color> color) {
        color.forEach(vialStack::push);
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
    Vial copy() {
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
