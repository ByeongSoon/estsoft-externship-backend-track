package com.shop.projectlion.domain.member.service;

import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.domain.member.repository.MemberRepository;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.shop.projectlion.global.error.exception.ErrorCode.ALREADY_REGISTERED_MEMBER;
import static com.shop.projectlion.global.error.exception.ErrorCode.MISMATCHED_PASSWORD;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void register(Member member, String password, String confirmPassword) throws BusinessException{

        validConfirmPassword(password, confirmPassword);
        validAlreadyMember(member.getEmail());

        memberRepository.save(member);

    }

    private void validConfirmPassword(String password, String confirmPassword) throws BusinessException{
        if (!password.equals(confirmPassword)) {
            throw new BusinessException(MISMATCHED_PASSWORD);
        }
    }

    private void validAlreadyMember(String email) throws BusinessException{
        if(memberRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(ALREADY_REGISTERED_MEMBER);
        }
    }

    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

}
