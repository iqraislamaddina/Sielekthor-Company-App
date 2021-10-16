package apap.tugas.sielekthor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.repository.BarangDb;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

@Service
@Transactional
public class BarangServiceImpl implements BarangService{

    @Autowired
    BarangDb barangDb;
    
    @Override
    public void addBarang(BarangModel barang){ 
        barangDb.save(barang); 
    }

    @Override
    public BarangModel getBarangByidBarang(Long idBarang) {
        Optional<BarangModel> barang = barangDb.findByidBarang(idBarang);
        if (barang.isPresent())
            return barang.get();
        else
            return null;
    }
    
    @Override
    public BarangModel updateBarang(BarangModel barang) {
        barangDb.save(barang);
        return barang;
    }

    @Override
    public void deleteBarangByidBarang(Long idBarang){
        Optional<BarangModel> barang = barangDb.findByidBarang(idBarang);
        if (barang.isPresent()){
            BarangModel BarangModel = barang.get();
            barangDb.delete(BarangModel);
        }
            
    }

    @Override
    public List<BarangModel> getListBarang() {
        return barangDb.findAll();
    }

}
