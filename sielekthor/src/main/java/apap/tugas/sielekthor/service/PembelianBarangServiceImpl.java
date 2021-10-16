package apap.tugas.sielekthor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.repository.PembelianBarangDb;
import java.sql.Timestamp;

@Service
@Transactional
public class PembelianBarangServiceImpl implements PembelianBarangService{
    @Autowired
    PembelianBarangDb pembelianBarangDb;
    
    @Override
    public void addPembelianBarang(PembelianBarangModel pembelianBarang){ 
        pembelianBarangDb.save(pembelianBarang); 
    }

    @Override
    public List<PembelianBarangModel> getListPembelianBarang() {
        return pembelianBarangDb.findAll();
    }

    @Override
    public java.sql.Date getTanggalGaransi(PembelianBarangModel pembelianBarang) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(ts.getTime());
        c.add(Calendar.DAY_OF_MONTH, pembelianBarang.getBarang().getJumlahGaransi());
        ts = new Timestamp(c.getTime().getTime());
        Date dat = Date.valueOf(ts.toLocalDateTime().toLocalDate());

        return dat;
    }

    @Override
    public Boolean isAvailable(PembelianBarangModel pembelianBarang){
        Boolean flag = false;
        int beli = pembelianBarang.getQuantity();
        int stok = pembelianBarang.getBarang().getStokBarang();
        if (stok >= beli){
            int sisa = stok - beli;
            pembelianBarang.getBarang().setStokBarang(sisa);
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    @Override
    public void deletePembelianBarang(PembelianBarangModel pembelianBarang){
        int dibeli = pembelianBarang.getQuantity();
        int stok = pembelianBarang.getBarang().getStokBarang();
        int tambah = stok + dibeli;
        pembelianBarang.getBarang().setStokBarang(tambah);

    }
}
