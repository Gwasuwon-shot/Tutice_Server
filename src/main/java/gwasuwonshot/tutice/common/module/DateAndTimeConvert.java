package gwasuwonshot.tutice.common.module;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

public class DateAndTimeConvert {
    public static Time stringConvertTime(String stringTime){ //"hh:mm:00" 형식이어야함

        return Time.valueOf(stringTime);
    }

    public static String timeConvertString(Time time){

        String timeFullString = time.toString();

        return timeFullString.substring(0,timeFullString.length()-3); //HH:mm 형식으로 줌
    }

    public static Date stringConvertDate(String stringDate){ //"yyyy-mm-dd 형식이어야함

        //import java.sql.Date;
        return Date.valueOf(stringDate);

    }

    public static String stringConvertDate(Date date){

        //import java.sql.Date;
        //"yyyy-mm-dd 형식이어야함
        return date.toString();
    }

    public static String nowDateConvertDate(){
        //현재날짜를 'yyyy-mm-dd'에 형식의 string으로 만들어주기
        LocalDate nowDate = LocalDate.now();
        return nowDate.toString();
    }



}
