package apap.tugas.sielekthor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.repository.MemberDb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{
 
    @Autowired
    MemberDb memberDB;
 
    @Override
    public void addMember(MemberModel member) {
        memberDB.save(member);
    }
 
    @Override
    public List<MemberModel> getListMember() {
        return memberDB.findAll();
    }

    @Override
    public MemberModel getMemberByidMember(Long idMember) {
        Optional<MemberModel> member = memberDB.findByidMember(idMember);
        if (member.isPresent())
            return member.get();
        else
            return null;
    }

    @Override
    public MemberModel updateMember(MemberModel member) {
        memberDB.save(member);
        return member;
    }
}
