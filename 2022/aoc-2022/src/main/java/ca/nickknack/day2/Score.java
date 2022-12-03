package ca.nickknack.day2;

import java.util.Arrays;
import java.util.Optional;

public enum Score {
    WIN(6, 'Z'),
    DRAW(3, 'Y'),
    LOSE(0, 'X');

    public final int modifier;
    public final char code;

    private Score(int modifier, char code) {
        this.modifier = modifier;
        this.code = code;
    }

    public static Optional<Score> getByCode(char code) {
        return Arrays.stream(Score.values())
                .filter(score -> score.code == code)
                .findFirst();
    }
}
