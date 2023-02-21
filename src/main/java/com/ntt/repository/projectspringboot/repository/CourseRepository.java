package com.ntt.repository.projectspringboot.repository;

import com.ntt.repository.projectspringboot.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}