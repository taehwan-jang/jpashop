package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member memberA = Member.builder()
                .name("memberA")
                .build();

        //when
        Long savedId = memberService.join(memberA);

        //then
        Assertions.assertEquals(memberA,memberService.findMember(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원() throws Exception {
        //given
        Member memberA = Member.builder()
                .name("memberA")
                .build();
        Member memberB = Member.builder()
                .name("memberA")
                .build();
        //when
        memberService.join(memberA);
        memberService.join(memberB);
        //예외가 발생해야한다.

        //then
        fail("예외가 발생해야 한다.");
    }

}