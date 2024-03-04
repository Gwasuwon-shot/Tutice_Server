package gwasuwonshot.tutice.lesson.entity;

import gwasuwonshot.tutice.common.resolver.enumValue.EnumModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter
@RequiredArgsConstructor
public enum DayOfWeek implements EnumModel {
    MONDAY("DAY_OF_WEEK_MONDAY","월",1),
    TUESDAY("DAY_OF_WEEK_TUESDAY","화",2),
    WEDNESDAY("DAY_OF_WEEK_WEDNESDAY","수",3),
    THURSDAY("DAY_OF_WEEK_THURSDAY","목",4),
    FRIDAY("DAY_OF_WEEK_FRIDAY","금",5),
    SATURDAY("DAY_OF_WEEK_SATURDAY","토",6),
    SUNDAY("DAY_OF_WEEK_SUNDAY","일",7);

    private final String key;
    private final String value;
    private final Integer index; // java.time.DayOfWeek와 호환을 위해 만듬

    private static HashMap<Integer, DayOfWeek> dayOfWeekByIndex = new HashMap<>();
    private static HashMap<String, DayOfWeek> dayOfWeekByValue = new HashMap<>();
    static {
        Arrays.stream(values()).forEach(d -> {
            dayOfWeekByIndex.put(d.getIndex(),d);
            dayOfWeekByValue.put(d.getValue(),d);
        });
    }

    public static DayOfWeek getByIndex(Integer index){
        return dayOfWeekByIndex.getOrDefault(index,null);
    }

    public static DayOfWeek getByJavaDayOfWeek(java.time.DayOfWeek dayOfWeek){
        return getByIndex(dayOfWeek.getValue());
    }

    public static java.time.DayOfWeek getJavaDayOfWeek(DayOfWeek dayOfWeek){
        return java.time.DayOfWeek.of(dayOfWeek.getIndex().intValue());
    }

    //TODO
    public static Integer getIndexByValue(String value){
        return Arrays.stream(DayOfWeek.values())
                .filter(p -> p.getValue().equals(value))
                .map(DayOfWeek::getIndex)
                .findAny()
                .orElseThrow();
    }


    public static DayOfWeek getByValue(String value){
        return dayOfWeekByValue.getOrDefault(value,null);
    }

    public static void sorted(List<DayOfWeek> dayOfWeekList){
        if (dayOfWeekList.size() > 1) {
            Collections.sort(dayOfWeekList, new Comparator<DayOfWeek>() {
                @Override
                public int compare(DayOfWeek o1, DayOfWeek o2) {
                    return o1.getIndex() - o2.getIndex();
                }
            });
        }
    }


}
