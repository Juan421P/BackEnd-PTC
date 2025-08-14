package ptc2025.backend.Respositories.courseEnrollments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptc2025.backend.Entities.courseEnrollments.CourseEnrollmentEntity;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollmentEntity, String> { }

