package by.ilyin.workexchange.util;

public class SecurityDataCleaner {

    private static final char ZERO_SIGN = '0';

    public static void cleanCharArrays(char[] array, char[]... arrays) {
        if (array != null) {
            cleanCharArrayValues(array);
        }
        for (int arrObjIndex = 0; arrObjIndex < arrays.length; ++arrObjIndex) {
            if (arrays[arrObjIndex] != null) {
                cleanCharArrayValues(arrays[arrObjIndex]);
            }
        }
    }

    private static void cleanCharArrayValues(char[] array) {
        for (int signIndex = 0; signIndex < array.length; ++signIndex) {
            array[signIndex] = ZERO_SIGN;
        }
    }

    public static void cleanStringBuilders(StringBuilder sb, StringBuilder... sbArr) {
        if (sb != null) {
            cleanStringBuilderValues(sb);
        }
        for (int sbArrElemIndex = 0; sbArrElemIndex < sbArr.length; ++sbArrElemIndex) {
            if (sbArr[sbArrElemIndex] != null) {
                cleanStringBuilderValues(sbArr[sbArrElemIndex]);
            }
        }
    }

    private static void cleanStringBuilderValues(StringBuilder sb) {
        int length = sb.length();
        for (int signIndex = 0; signIndex < length; ++signIndex) {
            sb.setCharAt(signIndex, ZERO_SIGN);
        }
    }

    public static void cleanStringBuffers(StringBuffer sb, StringBuffer... sbArr) {
        if (sb != null) {
            cleanStringBufferValues(sb);
        }
        for (int sbArrElemIndex = 0; sbArrElemIndex < sbArr.length; ++sbArrElemIndex) {
            if (sbArr[sbArrElemIndex] != null) {
                cleanStringBufferValues(sbArr[sbArrElemIndex]);
            }
        }
    }

    private static void cleanStringBufferValues(StringBuffer sb) {
        int length = sb.length();
        for (int signIndex = 0; signIndex < length; ++signIndex) {
            sb.setCharAt(signIndex, ZERO_SIGN);
        }
    }
}
