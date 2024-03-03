package kimtaeyoung.hellospring.service;

import kimtaeyoung.hellospring.domain.Member;
import kimtaeyoung.hellospring.repository.MemberRepository;
import kimtaeyoung.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    //마찬가지로 매번 테스트마다 메모리 초기회

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    //테스트 코드 작성시 예외처리 부분도 중요하기에
    //아까 처리한 이름중복이되는지 테스트 코드도 작성
    @Test
    void sameNameJoin() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        //밑에 try catch 문법 대신에 이런 함수를 활용해서 써도됨
        //() -> memberService.join(member2) 이걸 실행했을때 IllegalStateException.class 이게 터져야한다는뜻
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다.", e.getMessage());
//        try{
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            //발생되는 메세지랑 작성해뒀던 메세지랑 출력값을 비교하여 제대로 되는지 비교
//            assertEquals("이미 존재하는 회원입니다.", e.getMessage());
//        }
        //then

    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}