package com.example.distributedProject.services;

import com.example.distributedProject.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant,Integer> {
}
