package pl.me.jhonylemon;

import com.diogonunes.jcolor.Attribute;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Movement {
    private final Integer vialIndexFrom;
    private final Integer vialIndexTo;
    private final List<Color> marbles;
    private final List<Color> usedVialsContents;

    public Movement(Integer vialIndexFrom, Integer vialIndexTo, List<Color> marbles, List<Color> usedVialsContents) {
        this.vialIndexFrom = vialIndexFrom;
        this.vialIndexTo = vialIndexTo;
        this.marbles = Collections.unmodifiableList(marbles);
        this.usedVialsContents = Collections.unmodifiableList(usedVialsContents);
    }

    public Integer getVialIndexFrom() {
        return vialIndexFrom;
    }

    public Integer getVialIndexTo() {
        return vialIndexTo;
    }

    public List<Color> getMarbles() {
        return marbles;
    }

    public List<Color> getUsedVialsContents() {
        return usedVialsContents;
    }

    public Movement reverse() {
        return new Movement(vialIndexTo, vialIndexFrom, marbles, usedVialsContents);
    }

    public List<Attribute> getPrintableContents() {
        return marbles.stream()
                .map(Color::getColor)
                .toList();
    }

    public String toString() {
        return "Movement{" +
                "vialIndexFrom=" + vialIndexFrom +
                ", vialIndexTo=" + vialIndexTo +
                ", marbles=" + marbles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movement movement)) return false;
        return Objects.equals(getVialIndexFrom(), movement.getVialIndexFrom()) &&
                Objects.equals(getVialIndexTo(), movement.getVialIndexTo()) &&
                Objects.equals(getMarbles(), movement.getMarbles()) && Objects.equals(getUsedVialsContents(), movement.getUsedVialsContents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVialIndexFrom(), getVialIndexTo(), getMarbles(), getUsedVialsContents());
    }
}
