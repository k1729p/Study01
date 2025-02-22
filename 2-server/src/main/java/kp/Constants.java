package kp;

import java.util.function.LongBinaryOperator;
import java.util.function.LongFunction;

/**
 * The constants.
 */
@SuppressWarnings("doclint:missing")
public final class Constants {
    public static final long DEP_INDEX_LOWER_BOUND = 1;
    public static final long DEP_INDEX_UPPER_BOUND = 2;
    public static final long EMP_INDEX_LOWER_BOUND = 1;
    public static final long EMP_INDEX_UPPER_BOUND = 2;

    public static final LongFunction<String> DEP_NAME_FUN = "D-Name-%02d"::formatted;
    public static final LongBinaryOperator EMP_INDEX_FUN = (depIndex, empIndex) -> 100 * depIndex + empIndex;
    public static final LongFunction<String> EMP_F_NAME_FUN = "EF-Name-%d"::formatted;
    public static final LongFunction<String> EMP_L_NAME_FUN = "EL-Name-%d"::formatted;
    private static final String ROOT = "/";
    public static final String LOAD_SAMPLE_DATASET_PATH = ROOT + "loadSampleDataset";
    public static final String LOAD_SAMPLE_DATASET_RESULT = "The sample dataset was loaded with success.";

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
