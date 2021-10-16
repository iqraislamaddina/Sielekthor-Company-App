package apap.tugas.sielekthor.service;
import java.sql.Date;
import java.util.List;

import apap.tugas.sielekthor.model.PembelianBarangModel;
 
public interface PembelianBarangService {
    void addPembelianBarang(PembelianBarangModel pembelianBarang);
    List<PembelianBarangModel> getListPembelianBarang();
    Date getTanggalGaransi(PembelianBarangModel pembelianBarang);
    Boolean isAvailable(PembelianBarangModel pembelianBarang);
    void deletePembelianBarang(PembelianBarangModel pembelianBarang);
}
