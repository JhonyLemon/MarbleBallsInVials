package pl.me.jhonylemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Vials {
    @SuppressWarnings("java:S1700")
    private final List<Vial> vials = new ArrayList<>();

    public Vials(List<Vial> vials) {
        if (vials.isEmpty()) {
            throw new IllegalArgumentException("Vials cannot be empty");
        }
        this.vials.addAll(vials);
    }

    private Movement move(Integer from, Integer to) {
        if (from < 0 || from >= vials.size() || to < 0 || to >= vials.size()) {
            throw new IndexOutOfBoundsException("Vial index out of bounds");
        }
        Vial fromVial = vials.get(from);
        Vial toVial = vials.get(to);
        List<Color> marbles = fromVial.get();
        toVial.add(marbles);
        return new Movement(fromVial.getIndex(), toVial.getIndex(), marbles);
    }

    public Integer size() {
        return vials.size();
    }

    public Stream<Map.Entry<Vials, Movement>> stream() {
        return vials.parallelStream()
                .map(v0 -> vials.parallelStream().map(v1 -> Map.entry(v0, v1)).toList())
                .flatMap(List::parallelStream)
                .filter(e -> !Objects.equals(e.getKey().getIndex(), e.getValue().getIndex()))
                .filter(canMove())
                .map(moveCloned())
                .filter(Objects::nonNull);
    }

    private Predicate<Map.Entry<Vial,Vial>> canMove() {
        return e -> {
            Color from = e.getKey().peek();
            Color to = e.getValue().peek();
            if (Objects.isNull(from)) {
                return false;
            }
            if (Objects.isNull(to)) {
                return true;
            }
            return Objects.equals(e.getKey().peek(), e.getValue().peek());
        };
    }

    private Function<Map.Entry<Vial, Vial>, Map.Entry<Vials, Movement>> moveCloned() {
        return e -> {
            Vials clonedVials = this.clone();
            try {
                Movement movement = clonedVials.move(e.getKey().getIndex(), e.getValue().getIndex());
                return Map.entry(clonedVials, movement);
            } catch (Exception ignored) {
                return null;
            }
        };
    }

    public boolean isSameColor() {
        return vials.stream().allMatch(Vial::isSameColor);
    }

    protected Vials clone() {
        return new Vials(this.vials.stream().map(Vial::clone).toList());
    }

    public List<Vial> getVials() {
        return vials;
    }
}
