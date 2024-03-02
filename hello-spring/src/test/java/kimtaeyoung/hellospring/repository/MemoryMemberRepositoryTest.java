package kimtaeyoung.hellospring.repository;

import kimtaeyoung.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //테스트가 하나씩 끝날때마다 저장소를 다 지움
    //why? 테스트 순서는 무작위로 돌아가기에 다시 써야되는 값들이 미리 들어가있거나 하는 에러를 방지하기위해
    //테스트가 하나씩 종료될때마다 저장소를 지워주는 메서드를 만들어야함 여기서 aftereach가 콜백함수처럼 매번 실행되는 것
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");
        repository.save(member);
        //여기서 값을 꺼낼때 이 findbyid가 옵셔넗이라 뒤에 .get()을 사용하여서 값을 꺼내줌
        //근데 이게 좋은 방법은 아님
       Member result = repository.findById(member.getId()).get();
       //System.out.println("result = " + (result == member));
        //이 어설트이퀄에서 첫번째 매개변수는 내가 기대하는것, 두번째가 실제 결과
        assertEquals(member, result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        //이렇게 해주면 spring 1이라는 회원과 spring2라는 회원이 가입이 된 상태

        //아까와 같이 옵셔넣은 get으로 꺼내주고 assertequal을 이용하여서 기대하는 값과 실제 결과값 비교하기
        // 그래서 member1의 이름이 spring1이니까 여기서 spring1을 찾는 걸해보면 정상 동작됨
        Member result = repository.findByName("spring1").get();
        assertEquals(member1, result);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertEquals(2,result.size());
    }
}
