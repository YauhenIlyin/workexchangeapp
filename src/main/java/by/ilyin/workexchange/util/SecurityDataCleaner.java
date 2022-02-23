package by.ilyin.workexchange.util;

public class SecurityDataCleaner {

    public static void cleanCharArraysValues(char[]... array) {
        if (array != null) {
            for (int arrObjIndex = 0; arrObjIndex < array.length; ++arrObjIndex) {
                cleanCharArrayValues(array[arrObjIndex]);
            }
        }
    }

    public static void cleanCharArrayValues(char[] array) {
        if (array != null) {
            for (int valueIndex = 0; valueIndex < array.length; ++valueIndex) {
                array[valueIndex] = '0';
            }
        }
    }

    /* //todo дублирование кода для Buffer и Builder или вот такое ?
    public static void cleanStringBuildersBuffersValues(Object... sbArr) {
        if (sbArr != null) {
            int sbArrLength = sbArr.length;
            for (int sbObjIndex = 0; sbObjIndex < sbArrLength; ++sbObjIndex) {
                cleanStringBuilderBufferValues(sbArr[sbObjIndex], sbArr[sbObjIndex].getClass());
            }
        }
    }


    public static void cleanStringBuilderBufferValues(Object obj, Class<?> clazz) {
        if (obj != null) {
            StringBuilder sBuilder = null;
            StringBuffer sBuffer = null;
            int sbArrLength = 0;
            if (clazz == StringBuilder.class) {
                sBuilder = (StringBuilder) obj;
                sbArrLength = sBuilder.length();
            } else if (clazz == StringBuffer.class) {
                sBuffer = (StringBuffer) obj;
                sbArrLength = sBuffer.length();
            }
            for (int sbObjIndex = 0; sbObjIndex < sbArrLength; ++sbObjIndex) {

            }
        }
    }
     */

    public static void cleanStringBuildersValues(StringBuilder... sbArr) {
        if (sbArr != null) { //todo StringBuilder[][] sbArr = null положит, поэтому проверка
            int sbArrLength = sbArr.length;
            for (int sbObjIndex = 0; sbObjIndex < sbArrLength; ++sbObjIndex) {
                cleanStringBuilderValues(sbArr[sbObjIndex]);
            }
        }
    }

    public static void cleanStringBuilderValues(StringBuilder sb) {
        if (sb != null) {
            int stringBuilderInnerValueArrLength = sb.length();
            for (int valueIndex = 0; valueIndex < stringBuilderInnerValueArrLength; ++valueIndex) {
                sb.setCharAt(valueIndex, '0');
            }
        }
    }

    public static void cleanStringBuffersValues(StringBuffer... sbArr) {
        if (sbArr != null) { //todo StringBuilder[][] sbArr = null положит, поэтому проверка
            int sbArrLength = sbArr.length;
            for (int sbObjIndex = 0; sbObjIndex < sbArrLength; ++sbObjIndex) {
                cleanStringBufferValues(sbArr[sbObjIndex]);
            }
        }
    }

    public static void cleanStringBufferValues(StringBuffer sb) {
        if (sb != null) {
            int stringBufferInnerValueArrLength = sb.length();
            for (int valueIndex = 0; valueIndex < stringBufferInnerValueArrLength; ++valueIndex) {
                sb.setCharAt(valueIndex, '0');
            }
        }
    }

}
