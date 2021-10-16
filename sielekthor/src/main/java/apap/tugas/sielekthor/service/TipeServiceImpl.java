package apap.tugas.sielekthor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tugas.sielekthor.model.TipeModel;
import apap.tugas.sielekthor.repository.TipeDb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipeServiceImpl implements TipeService{
 
    @Autowired
    TipeDb TipeDB;
 
    @Override
    public void addTipe(TipeModel Tipe) {
        TipeDB.save(Tipe);
    }
 
    @Override
    public List<TipeModel> getListTipe() {
        return TipeDB.findAll();
    }

    @Override
    public TipeModel getTipeByidTipe(Long idTipe) {
        Optional<TipeModel> tipe = TipeDB.findByidTipe(idTipe);
        if (tipe.isPresent())
            return tipe.get();
        else
            return null;
    }
}
