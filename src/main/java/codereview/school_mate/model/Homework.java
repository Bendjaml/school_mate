package codereview.school_mate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta. persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok. Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "homeworks")
public class Homework {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "date", nullable = false)
        private LocalDateTime date;

        @Column(name = "description_homeworks", nullable = false)
        private String descriptionHomeworks;

        @ManyToOne
        @JoinColumn(name = "subject_id", nullable = false)
        private Subject subject;

        @ManyToOne
        @JoinColumn(name = "class_id", nullable = false)
        private SchoolClass schoolClass;

}
