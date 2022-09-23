package com.shop.projectlion;

import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.domain.member.model.enumclass.MemberType;
import com.shop.projectlion.domain.member.repository.MemberRepository;
import com.shop.projectlion.domain.member.model.enumclass.MemberRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class MemberRepositoryTest extends ProjectLionApplicationTests{

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @Transactional
    @DisplayName("회원 등록 테스트")
    public void register() {
        Member member = Member.builder()
            .memberName("byeongsoon")
            .memberType(MemberType.GENERAL)
            .email("byeongsoon94@kakao.com")
            .password("12341234123")
            .refreshToken(null)
            .role(MemberRole.ADMIN)
            .tokenExpirationTime(null)
            .build();

        Member newMember = memberRepository.save(member);

        Assertions.assertNotNull(newMember);
    }

    @Test
    @DisplayName("회원 조회 테스트")
    @Transactional(readOnly = true)
    public void read() {
        Optional<Member> member = memberRepository.findById(1L);

        if (member.isPresent()) {
            member.stream().forEach(s -> {
                System.out.println("Member ID : " + s.getMemberId());
                System.out.println("Email : " + s.getEmail());
                System.out.println("Password : " + s.getPassword());
                System.out.println("Name : " + s.getMemberName());
                System.out.println("Role : " + s.getRole().getTitle());
                System.out.println("Create Time : " + s.getCreateTime());
            });
        }

        Assertions.assertNotNull(member);
    }

}
