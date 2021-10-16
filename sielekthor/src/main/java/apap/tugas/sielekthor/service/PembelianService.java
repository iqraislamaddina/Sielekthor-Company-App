package apap.tugas.sielekthor.service;
import java.util.List;

import apap.tugas.sielekthor.model.PembelianModel;
 
public interface PembelianService {
    void addPembelian(PembelianModel pembelian);
    List<PembelianModel> getListPembelian();
    PembelianModel getPembelianByidPembelian(Long idPembelian);
    PembelianModel updatePembelian(PembelianModel Pembelian);
    void deletePembelianByidPembelian(Long idPembelian);
    int getTotaPembelian(PembelianModel pembelian);
    String getNoInvoice(PembelianModel pembelian);
    String updateNoInvoice(PembelianModel pembelian);
    int getTotal(PembelianModel pembelian);
    int getJumBarang(PembelianModel pembelian);
}
