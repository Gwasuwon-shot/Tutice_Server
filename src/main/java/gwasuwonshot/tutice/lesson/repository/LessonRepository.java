package gwasuwonshot.tutice.lesson.repository;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByParentsIdxAndIsFinished(Long parentsIdx, Boolean isFinished);

    List<Lesson> findAllByTeacherIdxAndIsFinished(Long teacherIdx, Boolean isFinished);


}

