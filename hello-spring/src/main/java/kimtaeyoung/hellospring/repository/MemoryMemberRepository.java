package kimtaeyoung.hellospring.repository;

import kimtaeyoung.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    //store는 회원정보를 저장하는데 사용되는 hashmap이며 회원의 고유 식별자를 키로 사용하고 해당 회원 정보를 값으로 저장
    private static long sequence = 0L;
    //여기서 이 sequence는 0,1,2 이렇게 키값을 설정해주는것
    @Override
    public Member save(Member member) {
        //멤버 세이브할때 우선 시퀀스값 하나 올려주고
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
        //이렇게하면 store에 넣기전에 멤버 id값을 세팅해주고
        //store에 저장함 그럼 맵에 저장이 되겠지
    }

    @Override
    public Optional<Member> findById(Long id) {
        //여기 findbyid는 store에서 추출해야함
        //근데 만약 추출되는 값이 null일수도 있으니까 Optional.ofNullable로 감싸줘야함
        //이렇게 해주면 클라이언트 측에서 처리 가능
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        //이름을 찾으려면 람다를 써서 그냥 store에서 돌리면됨
        //람다를 사용하여서 받은 name이 member안에있는 name이랑 같은지 확인 후 찾으면 반환
        //여기서는 findAny에 따로 옵셔널 null값 처리를 안해줘도되는 이유가 이 값이 위에보이는 optional로 처리가 됨

            return store.values().stream()
                    .filter(member -> member.getName().equals(name))
                    .findAny();
    }
    @Override
    public List<Member> findAll() {
        //store에 있는 벨류들이 멤버들인데 이게 반환이 됨
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
