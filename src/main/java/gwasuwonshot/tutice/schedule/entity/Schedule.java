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
        //처음생성시는 lesson의 스타트데이트, 연장시는 마지막 회차+1일
        //1. List를 startDate에서 가장 가까운 요일순으로 정렬
        List <RegularSchedule> regularScheduleList = lesson.getRegularScheduleList();

        //!!!  잘들어오나?
        System.out.println("시작날짜 : "+startDate);
        System.out.println("회차 : "+count);
        System.out.println("레슨아이디 : "+lesson.getIdx());
        regularScheduleList.forEach(lrs->System.out.println("수업의 요일들 : "+lrs.getDayOfWeek()));


        // TODO 정기일정이 1개이면 근야 하면됨!!
        if(regularScheduleList.size()>1){
            List<RegularSchedule> sortedRegularScheduleList = RegularSchedule.createSortedReglarScheduleList(startDate,regularScheduleList);
        }
        List<RegularSchedule> sortedRegularScheduleList = regularScheduleList;

        //2. 1의 정렬된 리스트에서 count 만큼 반복해 스케쥴 생성

        System.out.println("sortedRegularScheduleList.forEach");
        sortedRegularScheduleList.forEach(srs->System.out.println(srs.getDayOfWeek()));
        System.out.println("---------------------------------");



        List<Schedule> createdScheduleList = new ArrayList<>();
        for(int i =0 ; i < count; i++){
            System.out.println("i : "+i);
            System.out.println("sortedRegularScheduleList.size() : "+sortedRegularScheduleList.size());

            int tempI = i%sortedRegularScheduleList.size();
            System.out.println("tempI : "+tempI);

            int week = i/sortedRegularScheduleList.size();
            System.out.println("week : "+week);

            DayOfWeek dayOfWeek = sortedRegularScheduleList.get(tempI).getDayOfWeek();
            System.out.println("dayOfWeek : "+dayOfWeek.getValue());

            LocalTime startTime = sortedRegularScheduleList.get(tempI).getStartTime();
            LocalTime endTime = sortedRegularScheduleList.get(tempI).getEndTime();
            LocalDate date = startDate.plusWeeks(week).with(TemporalAdjusters.next(java.time.DayOfWeek.of(dayOfWeek.getIndex().intValue())));
            System.out.println("date : "+DateAndTimeConvert.localDateConvertString(date));
            System.out.println();

            createdScheduleList.add(Schedule.builder()
                    .lesson(lesson)
                    .date(date)
                    .cycle(lesson.getCycle()) //해당 레슨의 paymentRecord보기
                    .startTime(startTime)
                    .endTime(endTime)
                    .build());

        }

        createdScheduleList.forEach(cs -> System.out.println(cs.getDate()));

        return createdScheduleList;


    }

    // TODO 중복 코드 없애기
    public static List<Schedule> autoCreateTemporarySchedule(LocalDate startDate, Long count, List <RegularSchedule> regularScheduleList){

        if(regularScheduleList.size()>1){
            List<RegularSchedule> sortedRegularScheduleList = RegularSchedule.createSortedReglarScheduleList(startDate,regularScheduleList);
        }

        List<RegularSchedule> sortedRegularScheduleList = regularScheduleList;
        List<Schedule> createdScheduleList = new ArrayList<>();
        for(int i =0 ; i < count; i++){
            int tempI = i%sortedRegularScheduleList.size();
            int week = i/sortedRegularScheduleList.size();
            DayOfWeek dayOfWeek = sortedRegularScheduleList.get(tempI).getDayOfWeek();
            LocalTime startTime = sortedRegularScheduleList.get(tempI).getStartTime();
            LocalTime endTime = sortedRegularScheduleList.get(tempI).getEndTime();
            LocalDate date = startDate.plusWeeks(week).with(TemporalAdjusters.next(java.time.DayOfWeek.of(dayOfWeek.getIndex().intValue())));

            createdScheduleList.add(Schedule.builder()
                    .date(date)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build());

        }

        createdScheduleList.forEach(cs -> System.out.println(cs.getDate()));
        return createdScheduleList;
    }

    public void updateSchedule(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

//    public static void main(String[] args) {
//        LocalDate startDate = DateAndTimeConvert.stringConvertLocalDate("2023-07-16");
//        for(int i =0 ; i < 9; i++){
//            System.out.println("i : "+i);
//            int tempI = i%2;
//            int week = i/2;
//            System.out.println("tempI : "+tempI);
//            System.out.println("week : "+week);
//            LocalDate date = startDate.plusWeeks(week).with(TemporalAdjusters.next(java.time.DayOfWeek.of(dayOfWeek.getIndex().intValue())));
//
//
//
//
//        }
//    }
    public void updateScheduleAttendance(String status) {
        this.status = ScheduleStatus.getScheduleStatusByValue(status);
    }

}
