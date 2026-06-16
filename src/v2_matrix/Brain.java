package v2_matrix;

import math.Matrix;
import math.Vector;

import java.util.ArrayList;
import java.util.List;

public class Brain {
    private final Matrix association;

    private static float LEARNING_RATE = 0.3f;

    public Brain (Matrix association) {
        this.association = association;
    }

    public Brain (Matrix association, float LEARNING_RATE) {
        this.association = association;
        this.LEARNING_RATE = LEARNING_RATE;
    }

    private Vector calculateOutput (Vector inputNeurons) {
        return association.multiply(inputNeurons);
    }


}