package codereview.school_mate.repository;

import codereview.school_mate.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework,Long> {
    List<Homework> findBySchoolClassId(Long classId);
}
