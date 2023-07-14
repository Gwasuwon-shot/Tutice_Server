package gwasuwonshot.tutice.lesson.repository;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByTeacherIdx(Long teacherIdx);

    List<Lesson> findAllByParentsIdx(Long parentsIdx);

}

