package com.jpa.dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.dashboard.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findById(String id);
}
