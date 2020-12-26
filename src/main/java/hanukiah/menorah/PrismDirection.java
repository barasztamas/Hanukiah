package hanukiah.menorah;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PrismDirection {
    TOP(-1),
    BOTTOM(1),
    ;
    public final int rightOffset;

    public static PrismDirection value(int i) { return values()[i % values().length]; }
}
