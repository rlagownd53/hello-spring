package study.hellospring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.hellospring.domain.Member;
import study.hellospring.repository.MemberRepository;
import study.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     * @param member
     * @return
     */
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원 X
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     * @return
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 개별 조회
     * @param id
     * @return
     */
    public Optional<Member> findOne(Long id) {
        return memberRepository.findById(id);
    }
}
