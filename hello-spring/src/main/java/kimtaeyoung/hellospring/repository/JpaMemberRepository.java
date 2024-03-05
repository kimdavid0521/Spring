package kimtaeyoung.hellospring.repository;

import jakarta.persistence.EntityManager;
import kimtaeyoung.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    //jpa는 엔티티 메니저를 통 모든걸 동작함
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        //이렇게하면 jpa가 다 알아서 데이터를 넣어줌
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //이렇게 식별자랑 id값을 넘겨주면 찾아줌
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        //이것도 마찬가지로 식별자랑 이름값 넣으면 찾아줌
        Member member = em.find(Member.class, name);
        return Optional.ofNullable(member);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("selected m from Member m", Member.class)
    }
}
