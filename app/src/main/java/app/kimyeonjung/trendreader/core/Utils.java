package app.kimyeonjung.trendreader.core;

import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static Date stringToDate(String input) {
        //2017년 12월 5일 9시 34분
        String[] temp = input
                .replaceAll("년", "")
                .replaceAll("월", "")
                .replaceAll("일", "")
                .replaceAll("시", "")
                .replaceAll("분", "")
                .split("\\s");

        Calendar calendar = Calendar.getInstance();
        calendar.set(
                Integer.parseInt(temp[0]),
                Integer.parseInt(temp[1]),
                Integer.parseInt(temp[2]),
                Integer.parseInt(temp[3]),
                Integer.parseInt(temp[4])
        );

        return calendar.getTime();
    }
}
