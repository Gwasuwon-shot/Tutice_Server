package gwasuwonshot.tutice.lesson.entity;


import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
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

    public static List<RegularSchedule> dayOfWeekSortedReglarScheduleList(List<RegularSchedule> regularScheduleList){
        //regularScheduleList를 요일순서로 정렬
        Collections.sort( regularScheduleList, new Comparator<RegularSchedule>() {
            @Override
            public int compare(RegularSchedule o1, RegularSchedule o2) {
                Long difference = o1.getDayOfWeek().getIndex() - o2.getDayOfWeek().getIndex();
                return difference.intValue();
            }
        });

        return regularScheduleList;
    }

    public static List<RegularSchedule> createSortedReglarScheduleList(LocalDate startDate, List<RegularSchedule> regularScheduleList){
        //regularScheduleList를 요일순서로 정렬

        //요일순 정렬전
        System.out.println("요일순 정렬전");
        regularScheduleList.forEach(rs->{System.out.println(rs.getDayOfWeek());});
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        RegularSchedule.dayOfWeekSortedReglarScheduleList(regularScheduleList);

        //요일순 정렬후
        System.out.println("요일순 정렬후");
        regularScheduleList.forEach(rs->{System.out.println(rs.getDayOfWeek());});
        System.out.println("????????????????????");

        // startDate와 가장 가까운 요일의 정기일정 찾기. 만약 시작날짜가 정기요일중 가장 나중이면, 가장빠른 정기요일이 가까운요일
        Long startDateDayOfWeek = Long.valueOf(startDate.getDayOfWeek().getValue()); //시작날짜 요일

        Integer low = 0;
        Integer high = regularScheduleList.size()-1;
        Integer mid = 0;
        Integer latestDayOfWeekListIndex=7;

        while(low<= high){
            mid = (low + high) / 2;

            System.out.println("low : "+low);
            System.out.println("high : "+high);
            System.out.println("mid : "+mid);


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
        System.out.println("low : "+low);
        System.out.println("high : "+high);
        System.out.println("latestDayOfWeekListIndex : "+latestDayOfWeekListIndex);
        // ex. startDate의 요일이 일요일이면 일 또는 월요일이 가장 가까운 날짜
        if(latestDayOfWeekListIndex.equals(7)){ //위 로직에서 같은 요일이 없던경우
            if(low>high){//시작날짜 요일이 가장큰경우
                latestDayOfWeekListIndex=0;
            }else {
                latestDayOfWeekListIndex=low; //가장 가까운 큰 요일
            }
        }
        System.out.println("가장 가까운 요일 index : "+latestDayOfWeekListIndex);

        System.out.println("가장 가까운 요일 : "+regularScheduleList.get(latestDayOfWeekListIndex).getDayOfWeek());

        //가장 가까운 RegularSchedule을 기준으로 재정렬
        List<RegularSchedule> sortedRegularScheduleList = regularScheduleList.subList(latestDayOfWeekListIndex,regularScheduleList.size());
        sortedRegularScheduleList.addAll(regularScheduleList.subList(0,latestDayOfWeekListIndex));

        return sortedRegularScheduleList;

    }

}
