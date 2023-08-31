package gwasuwonshot.tutice.common.module;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.lesson.exception.invalid.InvalidDateException;
import gwasuwonshot.tutice.lesson.exception.invalid.InvalidTimeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateAndTimeConvert {

    public static String nowLocalDateConvertString(){
        //현재날짜를 'yyyy-mm-dd' 형식의 string으로 만들어주기
        LocalDate nowDate = LocalDate.now();
        return nowDate.toString();
    }

    public static String nowLocalDateConvertDayOfWeek(){
        //현재날짜를 'yyyy-mm-dd' 형식의 string으로 만들어주기
        LocalDate nowDate = LocalDate.now();
        // 현재 날짜 -> 요일
        return nowDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
    }


    public static String localDateConvertString(LocalDate localDate){
        return localDate.toString();
    }


    // TODO LocalDate에 대한 포맷 validator를 지금은 여기에 두는데 나중엔 requestDto로 옮기기
    public static LocalDate stringConvertLocalDate(String stringDate){
        try{
            return LocalDate.parse(stringDate, DateTimeFormatter.ISO_DATE);
        }catch (Exception e){
            if (e instanceof DateTimeParseException){
                throw new InvalidDateException(ErrorStatus.INVALID_DATE_EXCEPTION,ErrorStatus.INVALID_DATE_EXCEPTION.getMessage());
            }
            else{
                System.out.println(e);
                throw  e;
            }

        }
    }

    public static String localDateConvertDayOfWeek(LocalDate localDate) {
        // 현재 날짜 -> 요일
        return localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
    }

    public static String localTimeConvertString(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    // TODO Time에 대한 포맷 validator를 지금은 여기에 두는데 나중엔 requestDto로 옮기기
    public static LocalTime stringConvertLocalTime(String stringTime) {
        try{
            return LocalTime.parse(stringTime);
        }catch (Exception e){
            if (e instanceof DateTimeParseException){
                throw new InvalidTimeException(ErrorStatus.INVALID_TIME_EXCEPTION,ErrorStatus.INVALID_TIME_EXCEPTION.getMessage());
            }
            else{
                System.out.println(e);
                throw  e;
            }

        }
    }
}
