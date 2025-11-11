package com.arcitech.repository;

import com.arcitech.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByTeamType(String teamType);
    List<Team> findByTeamLeaderId(Long leaderId);
    List<Team> findByMembersId(Long memberId);
    List<Team> findByActive(boolean active);
}