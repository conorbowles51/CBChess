package engine;

import org.apache.commons.lang3.ArrayUtils;

public class EngineUtils {
    public static final float[] KNIGHT_SCORES = {
            0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,
            0.0f,0.1f,0.1f,0.1f,0.1f,0.1f,0.1f,0.0f,
            0.0f,0.1f,0.3f,0.3f,0.3f,0.3f,0.1f,0.0f,
            0.0f,0.1f,0.3f,0.5f,0.5f,0.3f,0.1f,0.0f,
            0.0f,0.1f,0.3f,0.5f,0.5f,0.3f,0.1f,0.0f,
            0.0f,0.1f,0.3f,0.3f,0.3f,0.3f,0.1f,0.0f,
            0.0f,0.1f,0.1f,0.1f,0.1f,0.1f,0.1f,0.0f,
            0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,
    };

    public static final float[] WHITE_PAWN_POS_SCORES = {
            // A     B      C      D      E      F      G      H
            0.60f, 0.60f, 0.60f, 0.60f, 0.60f, 0.60f, 0.60f, 0.60f,
            0.40f, 0.40f, 0.40f, 0.40f, 0.40f, 0.40f, 0.40f, 0.40f,
            0.05f, 0.05f, 0.10f, 0.20f, 0.20f, 0.10f, 0.05f, 0.05f,
            0.01f, 0.02f, 0.04f, 0.04f, 0.04f, 0.00f, 0.02f, 0.02f,
            0.01f, 0.01f, 0.1f, 0.15f, 0.15f, 0.00f, 0.01f, 0.01f,
            0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.00f, 0.01f, 0.01f,
            0.00f, 0.00f, 0.00f, 0.00f, 0.00f, 0.00f, 0.00f, 0.00f,
            0.00f, 0.00f, 0.00f, 0.00f, 0.00f, 0.00f, 0.00f, 0.00f,
    };

    public static final float[] BLACK_PAWN_SCORES = reverse(WHITE_PAWN_POS_SCORES);

    private static float[] reverse(float[] arr){
        float[] a = ArrayUtils.clone(arr);
        ArrayUtils.reverse(a);
        return a;
    }

}
