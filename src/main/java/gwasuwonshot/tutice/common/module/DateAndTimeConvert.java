package gwasuwonshot.tutice.common.module;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.lesson.exception.InvalidDateException;
import gwasuwonshot.tutice.lesson.exception.InvalidTimeException;

import java.time.LocalDate;

import java.sql.Date;
import java.sql.Time;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateAndTimeConvert {

    // TODO Time에 대한 포맷 validator를 지금은 여기에 두는데 나중엔 requestDto로 옮기기
    public static Time stringConvertTime(String stringTime){ //"hh:mm:00" 형식이어야함
        try{
            return Time.valueOf(stringTime);
        }catch (Exception e){
            if (e instanceof IllegalArgumentException){
                throw new InvalidTimeException(ErrorStatus.INVALID_TIME_EXCEPTION,ErrorStatus.INVALID_TIME_EXCEPTION.getMessage());
            }
            else{
                System.out.println(e);
                throw  e;
            }

        }
    }

    public static String timeConvertString(Time time){

        String timeFullString = time.toString();

        return timeFullString.substring(0,timeFullString.length()-3); //HH:mm 형식으로 줌
    }

    // TODO Date에 대한 포맷 validator를 지금은 여기에 두는데 나중엔 requestDto로 옮기기
    public static Date stringConvertDate(String stringDate){ //"yyyy-mm-dd 형식이어야함

        try{
            return Date.valueOf(stringDate);
        }catch (Exception e){
            if (e instanceof IllegalArgumentException){
                throw new InvalidDateException(ErrorStatus.INVALID_DATE_EXCEPTION,ErrorStatus.INVALID_DATE_EXCEPTION.getMessage());
            }
            else{
                System.out.println(e);
                throw  e;
            }

        }

    }

    public static String dateConvertString(Date date){

        //import java.sql.Date;
        //"yyyy-mm-dd 형식이어야함
        return date.toString();
    }

    public static String nowDateConvertString(){
        //현재날짜를 'yyyy-mm-dd'에 형식의 string으로 만들어주기
        LocalDate nowDate = LocalDate.now();
        return nowDate.toString();
    }

    public static String dateConvertDayOfWeek(Date date){
        // 현재 날짜 -> 요일
        LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
        return localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
    }



}
