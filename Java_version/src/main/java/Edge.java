import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangshao
 */
@Getter
@Setter
public class Edge {
    private int index;
    private Vertex a;
    private Vertex b;

    public Edge() {
    }

    Edge(final Vertex a, final Vertex b, final int index) {
        this.a = a;
        this.b = b;
        this.index = index;
    }

    public boolean has(final Vertex v) {
        return v.getIndex() == a.getIndex() || v.getIndex() == b.getIndex();
    }

    public List<Vertex> getVertices() {
        final List<Vertex> vertices = new ArrayList<>();
        vertices.add(a);
        vertices.add(b);
        return vertices;
    }

    public Vertex getOtherVertex(final Vertex v) {
        if (a != v) {
            return a;
        } else {
            return b;
        }
    }

    public double getLength() {
        return MathUtils.getMod(MathUtils.minusVector(this.a.getCoords(), this.b.getCoords()));
    }

    @Override
    public int hashCode() {
        return this.a.hashCode() + this.b.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Edge) {
            final Edge edge = (Edge) obj;
            return (this.a.getIndex() == edge.a.getIndex() && this.b.getIndex() == edge.b.getIndex()) || (this.b.getIndex() == edge.a.getIndex() && this.a.getIndex() == edge.b.getIndex());
        }
        return false;
    }
}
