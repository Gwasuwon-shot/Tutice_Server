package gwasuwonshot.tutice.schedule.entity;

import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import gwasuwonshot.tutice.lesson.entity.DayOfWeek;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.Payment;
import gwasuwonshot.tutice.lesson.entity.RegularSchedule;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public static List<Schedule> autoCreateSchedule(LocalDate startDate, Long count, Lesson lesson){ //처음생성시는 lesson의 스타트데이트, 연장시는 마지막 회차+1일
        //1. List를 startDate에서 가장 가까운 요일순으로 정렬
        List <RegularSchedule> regularScheduleList = lesson.getRegularScheduleList();



        //!!!  잘들어오나?
        System.out.println("시작날짜 : "+startDate);
        System.out.println("회차 : "+count);
        System.out.println("레슨아이디 : "+lesson.getIdx());
        regularScheduleList.forEach(lrs->System.out.println("수업의 요일들 : "+lrs.getDayOfWeek()));



        //일단 regularScheduleList를 요일순서로 정렬
        Collections.sort( regularScheduleList, new Comparator<RegularSchedule>() {
            @Override
            public int compare(RegularSchedule o1, RegularSchedule o2) {
                Long difference = o1.getDayOfWeek().getIndex() - o2.getDayOfWeek().getIndex();
                return difference.intValue();
            }
        });

        //!!! 월화수목금으로 정렬된
        regularScheduleList.forEach(rs->System.out.println(rs.getDayOfWeek().getValue()));

        //가장 가까운 요일 찾기, 같거나 큰 요일. 만약 key가 가장 큰 요일이면 첫번째 원소
        System.out.println(startDate);

        Long startDateDayOfWeek = Long.valueOf(startDate.getDayOfWeek().getValue()); //시작날짜 요일

        System.out.println(startDate+"의 요일 : "+startDateDayOfWeek);



        Integer low = 0;
        Integer high = regularScheduleList.size()-1;
        Integer mid = 0;
        Integer latestDayOfWeekListIndex=7;

        while(low<= high){
            mid = (low + high) / 2;
            if(startDateDayOfWeek.equals(regularScheduleList.get(mid).getDayOfWeek().getIndex())){
                latestDayOfWeekListIndex=mid; //수업시작일이 요일일때의 regularSchedulList의 인덱스
                break;
            }else if(startDateDayOfWeek < regularScheduleList.get(mid).getDayOfWeek().getIndex()){
                high = mid - 1;
            }
            else {
                low = mid + 1;
            }
        }
        // ex. startDate의 요일이 일요일이면 일 또는 월요일이 가장 가까운 날짜
        if(latestDayOfWeekListIndex.equals(7)){ //위 로직에서 같은 요일이 없던경우
            if(low>high){//시작날짜 요일이 가장큰경우
                latestDayOfWeekListIndex=0;
            }
            latestDayOfWeekListIndex=low; //가장 가까운 큰 요일
        }

        List<RegularSchedule> sortedRegularScheduleList = regularScheduleList.subList(latestDayOfWeekListIndex,regularScheduleList.size());
        sortedRegularScheduleList.addAll(regularScheduleList.subList(0,latestDayOfWeekListIndex));


        //로그

        System.out.println("시작날짜에서 가장 가까운 요일 : "+regularScheduleList.get(latestDayOfWeekListIndex).getDayOfWeek().getValue());
        sortedRegularScheduleList.forEach(srsl -> System.out.println(srsl.getDayOfWeek().getValue()));


        //2. 1의 정렬된 리스트에서 count 만큼 반복해 스케쥴 생성

        Long cycle;
        if(lesson.getPayment().equals(Payment.PRE_PAYMENT)){
            cycle = Long.valueOf(lesson.getPaymenRecordList().size());
        }
        else{
            cycle = Long.valueOf(lesson.getPaymenRecordList().size()+1);

        }
        List<Schedule> createdScheduleList = new ArrayList<>();
        for(int i =0 ; i < count; i++){
            int tempI = i%sortedRegularScheduleList.size();
            int week = i/sortedRegularScheduleList.size();
            DayOfWeek dayOfWeek = sortedRegularScheduleList.get(tempI).getDayOfWeek();
            LocalTime startTime = sortedRegularScheduleList.get(tempI).getStartTime();
            LocalTime endTime = sortedRegularScheduleList.get(tempI).getEndTime();
            LocalDate date = startDate.plusWeeks(week).with(TemporalAdjusters.next(java.time.DayOfWeek.of(dayOfWeek.getIndex().intValue())));
            createdScheduleList.add(Schedule.builder()
                    .lesson(lesson)
                    .date(date)
                    .cycle(cycle) //해당 레슨의 paymentRecord보기
                    .startTime(startTime)
                    .endTime(endTime)
                    .build());

        }

        createdScheduleList.forEach(cs -> System.out.println(cs.getDate()));

        return createdScheduleList;


    }

    public static void main(String[] args) {
        DayOfWeek[] a = DayOfWeek.values();
        for(int i =0 ; i < a.length; i++){
            System.out.println(i+" : "+a[i].getValue());
        }
    }
}
