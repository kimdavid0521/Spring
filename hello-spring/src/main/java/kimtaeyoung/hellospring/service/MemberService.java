package kimtaeyoung.hellospring.service;

import kimtaeyoung.hellospring.domain.Member;
import kimtaeyoung.hellospring.repository.MemberRepository;
import kimtaeyoung.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public Long join(Member member) {
        //메서드로 빼줌
        validDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //같은 이름 중복 회원 판별 메서드
    private void validDuplicateMember(Member member) {
        Optional<Member> result = memberRepository.findByName(member.getName());
        //ifpresent는 저 result에 널이 아니라 어떤 값이 있으면 동작하는 것 이게 옵셔넗이기에 가능한것
        //멤버를 그냥 m이라고 한것
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        //근데 위에있는 코드처럼 적어도되지만 옵셔널이라는 저게 좀 안예뻐서 밑에있는 코드로 대체 가능
//        memberRepository.findByName(member.getName());
//            .ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.")
//        });
    }

    //전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
