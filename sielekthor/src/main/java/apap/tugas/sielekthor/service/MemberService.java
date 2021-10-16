package apap.tugas.sielekthor.service;
import java.util.List;

import apap.tugas.sielekthor.model.MemberModel;
 
public interface MemberService {
    void addMember(MemberModel member);
    MemberModel updateMember(MemberModel member);
    List<MemberModel> getListMember();
    MemberModel getMemberByidMember(Long idMember);
    
}
