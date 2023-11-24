package gwasuwonshot.tutice.schedule.entity;

import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.entity.DayOfWeek;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.RegularSchedule;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "lesson_idx", nullable = false,  foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Lesson lesson;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status = ScheduleStatus.NO_STATUS; //기본값 상태없음은 db의 ddl로 넣기

    @Column(nullable = false)
    private Long cycle;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;


    @Builder
    public Schedule(Lesson lesson, LocalDate date,
                    Long cycle, LocalTime startTime, LocalTime endTime){
        this.lesson=lesson;
        this.date = date;
        this.cycle=cycle;
        this.startTime = startTime;
        this.endTime=endTime;
    }

    public Boolean isMatchedStatus(ScheduleStatus status){
        if(this.status.equals(status)){
            return true;
        }
        return false;
    }

    public static List<Schedule> autoCreateSchedule(LocalDate startDate, Long count, Lesson lesson){
        //처음생성시는 lesson의 스타트데이트, 연장시는 마지막 회차+1일을 기준으로 시작날짜 정함

        //1. List를 startDate에서 가장 가까운 요일순으로 정렬
        List <RegularSchedule> regularScheduleList = lesson.getRegularScheduleList();
        if(regularScheduleList.size()>1){
            RegularSchedule.sortedByDateAndDayOfWeek(regularScheduleList,startDate);
        }
        List<RegularSchedule> sortedRegularScheduleList = regularScheduleList;


        //2. 1의 정렬된 리스트에서 count 만큼 반복해 스케쥴 생성
        List<Schedule> createdScheduleList = new ArrayList<>();
        for(int i =0 ; i < count; i++){
            int tempI = i%sortedRegularScheduleList.size();
            int week = i/sortedRegularScheduleList.size();

            DayOfWeek dayOfWeek = sortedRegularScheduleList.get(tempI).getDayOfWeek();
            LocalTime startTime = sortedRegularScheduleList.get(tempI).getStartTime();
            LocalTime endTime = sortedRegularScheduleList.get(tempI).getEndTime();
            LocalDate date = startDate.plusWeeks(week).with(TemporalAdjusters.nextOrSame(DayOfWeek.getJavaDayOfWeek(dayOfWeek)));

            createdScheduleList.add(Schedule.builder()
                    .lesson(lesson)
                    .date(date)
                    .cycle(lesson.getCycle()) //해당 레슨의 paymentRecord보기
                    .startTime(startTime)
                    .endTime(endTime)
                    .build());
        }
        return createdScheduleList;
    }

    // TODO 중복 코드 없애기
    public static List<Schedule> autoCreateTemporarySchedule(LocalDate startDate, Long count, List <RegularSchedule> regularScheduleList){

        if(regularScheduleList.size()>1){
            RegularSchedule.sortedByDateAndDayOfWeek(regularScheduleList, startDate);
        }

        List<RegularSchedule> sortedRegularScheduleList = regularScheduleList;
        List<Schedule> createdScheduleList = new ArrayList<>();
        for(int i =0 ; i < count; i++){
            int tempI = i%sortedRegularScheduleList.size();
            int week = i/sortedRegularScheduleList.size();
            DayOfWeek dayOfWeek = sortedRegularScheduleList.get(tempI).getDayOfWeek();
            LocalTime startTime = sortedRegularScheduleList.get(tempI).getStartTime();
            LocalTime endTime = sortedRegularScheduleList.get(tempI).getEndTime();
            LocalDate date = startDate.plusWeeks(week).with(TemporalAdjusters.nextOrSame(DayOfWeek.getJavaDayOfWeek(dayOfWeek)));

            createdScheduleList.add(Schedule.builder()
                    .date(date)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build());

        }

        return createdScheduleList;
    }

    public void updateSchedule(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public void updateScheduleAttendance(String status) {
        this.status = ScheduleStatus.getScheduleStatusByValue(status);
    }

}
