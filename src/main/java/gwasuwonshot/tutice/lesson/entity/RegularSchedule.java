package gwasuwonshot.tutice.lesson.entity;


import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegularSchedule extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "lesson_idx", nullable = false,  foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Lesson lesson;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    DayOfWeek dayOfWeek;


    @Builder
    public RegularSchedule(Lesson lesson, LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek){
        this.lesson = lesson;
        this.startTime=startTime;
        this.endTime=endTime;
        this.dayOfWeek=dayOfWeek;

    }

    public static List<List<Integer>> groupByTimeRegularScheduleIndexList(List<RegularSchedule> regularScheduleList){
        // 어떤함수인지 : regularScheduleList를 받아, 해당 리스트안의 시간을 기준으로 그룹화할 원소를 이중리스트로 알려줌
        // 들어오는 인자의 index를 기준으로 알려줌
        // ex. input = [{월, 14:00, 15:00},{화, 14:00, 15:00},{수, 15:00, 16:00}] -> output = [[0,1],[2]]

        List<List<Integer>> result = new ArrayList<>();

        Map<String, List<Integer>> timeSlotMap = new HashMap<>();


        for (int i = 0; i < regularScheduleList.size(); i++) {
            RegularSchedule schedule = regularScheduleList.get(i);
            String timeSlotKey = schedule.getStartTime() + "-" + schedule.getEndTime();

            if (timeSlotMap.containsKey(timeSlotKey)) {
                timeSlotMap.get(timeSlotKey).add(i);
            } else {
                List<Integer> newIndexList = new ArrayList<>();
                newIndexList.add(i);
                timeSlotMap.put(timeSlotKey, newIndexList);
            }
        }

        for (List<Integer> indexList : timeSlotMap.values()) {
            result.add(indexList);
        }

        return result;
    }



    public static void sortedByDayOfWeek(List<RegularSchedule> regularScheduleList){
        //regularScheduleList를 요일순서로 정렬
        Collections.sort( regularScheduleList, new Comparator<RegularSchedule>() {
            @Override
            public int compare(RegularSchedule o1, RegularSchedule o2) {
                return o1.getDayOfWeek().getIndex() - o2.getDayOfWeek().getIndex();
            }
        });
    }

    public static void sortedByDateAndDayOfWeek(List<RegularSchedule> regularScheduleList, LocalDate date ){
        //regularScheduleList를 주어진 날짜에 다가오는 가까운 요일 순서대로 정렬
        Collections.sort(regularScheduleList,
                Comparator.comparingInt(
                        day -> calculateDayDistance(day.getDayOfWeek(), DayOfWeek.getByJavaDayOfWeek(date.getDayOfWeek())) ));
    }

    public static int calculateDayDistance(DayOfWeek day, DayOfWeek standardDay) {
        int distance = day.getIndex() - standardDay.getIndex();
        if(distance<0){
            return distance +7;
        }
        return distance;
    }

    public static RegularSchedule findLatestRegularSchedule(LocalDate startDate, List<RegularSchedule> regularScheduleList){
        // 시작날짜에 다가오는 가장 가까운 요일 index를 리턴
        // (즉 리스트에 있는 요일이 [월, 목, 수] 일때 현재요일이 월이면 월요일의 regularSchedule return, 화 이면 수요일의 reguarSchedule return
        sortedByDateAndDayOfWeek(regularScheduleList,startDate);
        return regularScheduleList.get(0);
    }


    public static RegularSchedule toEntity(Lesson lesson, LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        return RegularSchedule.builder()
                .lesson(lesson)
                .startTime(startTime)
                .endTime(endTime)
                .dayOfWeek(dayOfWeek)
                .build();
    }

    public static RegularSchedule toTemporaryEntity(String dayOfWeek, String startTime, String endTime) {
        return RegularSchedule.builder()
                .dayOfWeek(DayOfWeek.getByValue(dayOfWeek))
                .startTime(DateAndTimeConvert.stringConvertLocalTime(startTime))
                .endTime(DateAndTimeConvert.stringConvertLocalTime(endTime))
                .build();
    }


}
