package pl.me.jhonylemon.vial;

import com.diogonunes.jcolor.Attribute;
import pl.me.jhonylemon.Color;
import pl.me.jhonylemon.Movement;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Vials {
    @SuppressWarnings("java:S1700")
    private final List<Vial> vials = new ArrayList<>();
    private final Deque<Movement> movements = new ArrayDeque<>();

    private Vials(List<Vial> vials) {
        this.vials.addAll(vials);
    }

    @SafeVarargs
    public static Vials of(List<Color>... colors) {
        int maxSize = Arrays.stream(colors)
                .mapToInt(List::size)
                .max()
                .orElseThrow(() -> new IllegalArgumentException("No colors provided"));

        List<Vial> vials = new ArrayList<>();

        for (int i = 0; i < colors.length; i++) {
            Vial vial = new Vial(colors[i], maxSize, i);
            vials.add(vial);
        }
        return new Vials(vials);
    }

    /**
     * Moves marbles from one vial to another.
     * @param movement the {@link Movement} object representing the move
     */
    public void applyMovement(Movement movement) {
        Vial fromVial = vials.get(movement.getVialIndexFrom());
        Vial toVial = vials.get(movement.getVialIndexTo());
        toVial.push(movement.getMarbles());
        fromVial.pop(movement.getMarbles());
        movements.push(movement);
    }

    /**
     * Reverses the last move made
     */
    public void reverseMove() {
        if (movements.isEmpty()) {
            return;
        }
        Movement movement = movements.pop().reverse();
        Vial fromVial = vials.get(movement.getVialIndexFrom());
        Vial toVial = vials.get(movement.getVialIndexTo());
        toVial.push(movement.getMarbles());
        fromVial.pop(movement.getMarbles());
    }

    /**
     * Generates all possible moves from the current state of the vials.
     *
     * @return {@link Stream} of {@link Movement} objects representing possible moves.
     */
    public Stream<Movement> generatePossibleMoves() {
        return vials.parallelStream()
                .map(v0 -> vials.stream().map(v1 -> Map.entry(v0, v1)).toList())
                .flatMap(List::parallelStream)
                .filter(e -> !Objects.equals(e.getKey().getIndex(), e.getValue().getIndex()))
                .map(canMove())
                .filter(Objects::nonNull);
    }

    public List<List<Attribute>> getPrintableContents() {
        return vials.stream()
                .map(Vial::getPrintableContents)
                .toList();
    }

    /**
     * Checks if movement is possible.
     * @return {@code true} if the move is possible, {@code false} otherwise.
     */
    private Function<Map.Entry<Vial, Vial>, Movement> canMove() {
        return e -> {
            Integer from = e.getKey().getIndex();
            Integer to = e.getValue().getIndex();
            if (from < 0 || from >= vials.size() || to < 0 || to >= vials.size()) {
                return null;
            }
            if (Objects.equals(from, to)) {
                return null;
            }

            Vial fromVial = vials.get(from);
            Vial toVial = vials.get(to);

            if (toVial.isEmpty() && fromVial.isSameColor()) {
                return null;
            }

            if (!fromVial.canPop()) {
                return null;
            }

            List<Color> marbles = fromVial.peek();

            if (!toVial.canPush(marbles)) {
                return null;
            }

            Movement movement = new Movement(from, to, marbles, Map.entry(fromVial.getAll(), toVial.getAll()));

            if (movements.contains(movement) || movements.contains(movement.reverse())) {
                return null;
            }

            return movement;
        };
    }

    /**
     * Checks if all vials are sorted.
     *
     * @return {@code true} if all vials are sorted, {@code false} otherwise.
     */
    public boolean isSameColor() {
        return vials.stream().allMatch(Vial::isFullSameColor);
    }

    /**
     * Makes a copy of the vials.
     *
     * @return {@link Vials} object with the same vials.
     */
    public Vials copy() {
        return new Vials(this.vials.stream().map(Vial::copy).toList());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vials vials1)) return false;
        return Objects.equals(vials, vials1.vials);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vials);
    }
}
