package by.ilyinyauhen.workexchangeapp.validator;

public class LocalDateTimeStringFormatValidator { //todo need test  00:12 - to 0:12 in code with Integer.parseInt()

    private final int LOCAL_DATE_TIME_LENGTH = 19;
    private final int LOCAL_DATE_LENGTH = 10;
    private final int LOCAL_TIME_LENGTH = 8;

    private final char[] ALLOWED_DELIMITERS_ARRAY = new char[]{' ', 'T'};

    private final int LOCAL_DATE_FIRST_VAR_START_INDEX = 0;
    private final int LOCAL_DATE_FIRST_VAR_END_INDEX = 4;
    private final int LOCAL_DATE_SECOND_VAR_START_INDEX = 5;
    private final int LOCAL_DATE_SECOND_VAR_END_INDEX = 7;
    private final int LOCAL_DATE_THIRD_VAR_START_INDEX = 8;
    private final int LOCAL_DATE_THIRD_VAR_END_INDEX = 10;

    private final int LOCAL_TIME_FIRST_VAR_START_INDEX = 0;
    private final int LOCAL_TIME_FIRST_VAR_END_INDEX = 2;
    private final int LOCAL_TIME_SECOND_VAR_START_INDEX = 3;
    private final int LOCAL_TIME_SECOND_VAR_END_INDEX = 5;
    private final int LOCAL_TIME_THIRD_VAR_START_INDEX = 6;
    private final int LOCAL_TIME_THIRD_VAR_END_INDEX = 8;

    private final int YEAR_MIN_VALUE = 0;
    private final int YEAR_MAX_VALUE = 3000;
    private final int MONTH_MIN_VALUE = 1;
    private final int MONTH_MAX_VALUE = 12;
    private final int DAY_MIN_VALUE = 1;
    private final int DAY_MAX_VALUE = 31;

    private final int HOUR_MIN_VALUE = 0;
    private final int HOUR_MAX_VALUE = 23;
    private final int MINUTE_MIN_VALUE = 0;
    private final int MINUTE_MAX_VALUE = 59;
    private final int SECOND_MIN_VALUE = 0;
    private final int SECOND_MAX_VALUE = 59;

    public boolean validateLocalDateTimeString(String stringDateTime) {
        return stringDateTime != null && stringDateTime.length() == LOCAL_DATE_TIME_LENGTH &&
                validateLocalDateTimeDelimiter(stringDateTime) &&
                validateLocalDateString(stringDateTime.substring(0, LOCAL_DATE_LENGTH)) &&
                validateLocalTimeString(stringDateTime.substring(LOCAL_DATE_LENGTH + 1, LOCAL_DATE_TIME_LENGTH));
    }

    public boolean validateLocalDateString(String stringDate) {
        return validateLocalDateTimePart(stringDate, LOCAL_DATE_LENGTH,
                LOCAL_DATE_FIRST_VAR_START_INDEX, LOCAL_DATE_FIRST_VAR_END_INDEX,
                LOCAL_DATE_SECOND_VAR_START_INDEX, LOCAL_DATE_SECOND_VAR_END_INDEX,
                LOCAL_DATE_THIRD_VAR_START_INDEX, LOCAL_DATE_THIRD_VAR_END_INDEX,
                YEAR_MIN_VALUE, YEAR_MAX_VALUE, MONTH_MIN_VALUE, MONTH_MAX_VALUE, DAY_MIN_VALUE, DAY_MAX_VALUE);
    }

    public boolean validateLocalTimeString(String stringTime) {
        return validateLocalDateTimePart(stringTime, LOCAL_TIME_LENGTH,
                LOCAL_TIME_FIRST_VAR_START_INDEX, LOCAL_TIME_FIRST_VAR_END_INDEX,
                LOCAL_TIME_SECOND_VAR_START_INDEX, LOCAL_TIME_SECOND_VAR_END_INDEX,
                LOCAL_TIME_THIRD_VAR_START_INDEX, LOCAL_TIME_THIRD_VAR_END_INDEX,
                HOUR_MIN_VALUE, HOUR_MAX_VALUE, MINUTE_MIN_VALUE, MINUTE_MAX_VALUE, SECOND_MIN_VALUE, SECOND_MAX_VALUE);
    }

    private boolean validateLocalDateTimePart(String stringContainer, int stringContainerLength,
                                              int firstValueStartIndex, int firstValueEndIndex,
                                              int secondValueStartIndex, int secondValueEndIndex,
                                              int thirdValueStartIndex, int thirdValueEndIndex,
                                              int firstMinValue, int firstMaxValue,
                                              int secondMinValue, int secondMaxValue,
                                              int thirdMinValue, int thirdMaxValue) {
        if (stringContainer != null && stringContainer.length() == stringContainerLength) {
            try {
                int firstDateTimeValue = Integer.parseInt(stringContainer.substring(firstValueStartIndex, firstValueEndIndex));
                int secondDateTimeValue = Integer.parseInt(stringContainer.substring(secondValueStartIndex, secondValueEndIndex));
                int thirdDateTimeValue = Integer.parseInt(stringContainer.substring(thirdValueStartIndex, thirdValueEndIndex));
                return ((firstDateTimeValue >= firstMinValue && firstDateTimeValue <= firstMaxValue) &&
                        (secondDateTimeValue >= secondMinValue && secondDateTimeValue <= secondMaxValue) &&
                        (thirdDateTimeValue >= thirdMinValue && thirdDateTimeValue <= thirdMaxValue));
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean validateLocalDateTimeDelimiter(String dateTimeString) {
        boolean isValid = false;
        int index = 0;
        char container = dateTimeString.charAt(LOCAL_DATE_LENGTH);
        while (!isValid && index < ALLOWED_DELIMITERS_ARRAY.length) {
            if (ALLOWED_DELIMITERS_ARRAY[index] == container) {
                isValid = true;
            }
            ++index;
        }
        return isValid;
    }

}
