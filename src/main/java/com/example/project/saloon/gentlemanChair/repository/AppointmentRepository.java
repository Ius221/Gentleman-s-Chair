package com.example.project.saloon.gentlemanChair.repository;

import com.example.project.saloon.gentlemanChair.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}
