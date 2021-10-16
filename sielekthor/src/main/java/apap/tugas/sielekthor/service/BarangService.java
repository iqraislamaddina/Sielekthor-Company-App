package apap.tugas.sielekthor.service;

import java.util.List;

import apap.tugas.sielekthor.model.BarangModel;

public interface BarangService {
    void addBarang(BarangModel barang);
    BarangModel getBarangByidBarang(Long idBarang);
    BarangModel updateBarang(BarangModel barang);
    void deleteBarangByidBarang(Long idBarang);
    List<BarangModel> getListBarang();
    
}
