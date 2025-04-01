package pl.me.jhonylemon;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Vials {
    @SuppressWarnings("java:S1700")
    private final List<Vial> vials = new ArrayList<>();
    private final Deque<Movement> movements = new ArrayDeque<>();

    public Vials(List<Vial> vials) {
        if (vials.isEmpty()) {
            throw new IllegalArgumentException("Vials cannot be empty");
        }
        this.vials.addAll(vials);
    }

    public List<Vial> getVials() {
        return vials;
    }

    public Deque<Movement> getMovements() {
        return movements;
    }

    public Integer size() {
        return vials.size();
    }

    /**
     * Moves marbles from one vial to another. Respects the rules of the game.
     * @param from Index of the vial to move from.
     * @param to Index of the vial to move to.
     * @return {@link Movement} object with the details of the move.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @throws IllegalStateException if the move is not possible or already exists.
     */
    public Movement move(Integer from, Integer to) {
        if (from < 0 || from >= vials.size() || to < 0 || to >= vials.size()) {
            throw new IndexOutOfBoundsException("Vial index out of bounds");
        }
        if (Objects.equals(from, to)) {
            throw new IllegalStateException("Cannot move to the same vial");
        }

        Vial fromVial = vials.get(from);
        Vial toVial = vials.get(to);

        if (toVial.isEmpty() && fromVial.isSameColor()) {
            throw new IllegalStateException("Cannot move from sorted vial to empty vial");
        }

        List<Color> fromVialContents = Stream.of(fromVial.getAll(), toVial.getAll())
                .flatMap(Function.identity())
                .toList();
        List<Color> marbles = fromVial.pop();

        try {
            toVial.push(marbles);
        } catch (Exception e) {
            fromVial.add(marbles);
            throw new IllegalStateException("Cannot move marbles", e);
        }
        Movement movement = new Movement(from, to, marbles, fromVialContents);

        if (movements.contains(movement) || movements.contains(movement.reverse())) {
            fromVial.add(marbles);
            toVial.remove(marbles);
            throw new IllegalStateException("Move already exists");
        }

        movements.push(movement);

        return movement;
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
        toVial.add(movement.getMarbles());
        fromVial.remove(movement.getMarbles());
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
                .map(tryMove())
                .filter(Objects::nonNull);
    }

    /**
     * Tries to move marbles from one vial to another.
     * @return {@link Function} that takes a {@link Map.Entry} of vials and returns a {@link Movement} or {@code null}
     * if move is not possible.
     */
    private Function<Map.Entry<Vial, Vial>, Movement> tryMove() {
        return e -> {
            try {
                Vials copy = copy();
                return copy.move(e.getKey().getIndex(), e.getValue().getIndex());
            } catch (Exception ignored) {
                return null;
            }
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
    protected Vials copy() {
        return new Vials(this.vials.stream().map(Vial::copy).toList());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vials vials1)) return false;
        return Objects.equals(getVials(), vials1.getVials());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getVials());
    }
}
