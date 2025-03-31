package pl.me.jhonylemon;

import com.diogonunes.jcolor.Attribute;

import java.util.Collections;
import java.util.List;

public class Movement {
    private final Integer vialIndexFrom;
    private final Integer vialIndexTo;
    private final List<Color> marbles;

    public Movement(Integer vialIndexFrom, Integer vialIndexTo, List<Color> marbles) {
        this.vialIndexFrom = vialIndexFrom;
        this.vialIndexTo = vialIndexTo;
        this.marbles = Collections.unmodifiableList(marbles);
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
}
