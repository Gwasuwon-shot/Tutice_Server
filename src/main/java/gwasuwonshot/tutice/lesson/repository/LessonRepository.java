package gwasuwonshot.tutice.lesson.repository;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByParentsIdxAndIsFinishedAndDeletedAtIsNull(Long parentsIdx, Boolean isFinished);

    List<Lesson> findAllByTeacherIdxAndIsFinishedAndDeletedAtIsNull(Long teacherIdx, Boolean isFinished);
    Optional<Lesson> findByIdxAndIsFinishedAndDeletedAtIsNull(Long lessonIdx, boolean isFinished);

    Optional<Lesson> findByIdxAndDeletedAtIsNull(Long lessonIdx);


}

