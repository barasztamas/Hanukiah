package hanukiah.menorah;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PrismDirection {
    UP(-1),
    DOWN(1),
    ;
    public final int rightOffset;

    public static PrismDirection value(int i) { return values()[i % values().length]; }
}
