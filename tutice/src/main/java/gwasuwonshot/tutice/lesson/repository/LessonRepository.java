package gwasuwonshot.tutice.lesson.repository;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}

