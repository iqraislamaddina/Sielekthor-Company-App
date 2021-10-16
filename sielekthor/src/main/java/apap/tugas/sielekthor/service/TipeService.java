package apap.tugas.sielekthor.service;
import java.util.List;

import apap.tugas.sielekthor.model.TipeModel;
 
public interface TipeService {
    void addTipe(TipeModel Tipe);
    List<TipeModel> getListTipe();
    TipeModel getTipeByidTipe(Long idTipe);
    
}
