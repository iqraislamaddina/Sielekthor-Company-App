package apap.tugas.sielekthor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.repository.PembelianDb;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

@Service
@Transactional
public class PembelianServiceImpl implements PembelianService{

    @Autowired
    PembelianDb pembelianDb;
    
    @Override
    public void addPembelian(PembelianModel pembelian){ 
        pembelianDb.save(pembelian); 
    }

    @Override
    public PembelianModel getPembelianByidPembelian(Long idPembelian) {
        Optional<PembelianModel> pembelian = pembelianDb.findByidPembelian(idPembelian);
        if (pembelian.isPresent())
            return pembelian.get();
        else
            return null;
    }
    
    @Override
    public PembelianModel updatePembelian(PembelianModel Pembelian) {
        pembelianDb.save(Pembelian);
        return Pembelian;
    }

    @Override
    public void deletePembelianByidPembelian(Long idPembelian){
        Optional<PembelianModel> Pembelian = pembelianDb.findByidPembelian(idPembelian);
        if (Pembelian.isPresent()){
            PembelianModel PembelianModel = Pembelian.get();
            pembelianDb.delete(PembelianModel);
        }
            
    }

    @Override
    public List<PembelianModel> getListPembelian() {
        return pembelianDb.findAll();
    }

    @Override
    public int getTotaPembelian(PembelianModel pembelian) {
        int total = 0;
        for (PembelianBarangModel b : pembelian.getListPembelianBarang()){
            int q = b.getQuantity();
            int h = b.getBarang().getHargaBarang();
            total += q * h;
        }

        return total;
    }

    @Override
    public int getTotal(PembelianModel pembelian) {
        int total = 0;
        for (PembelianBarangModel b : pembelian.getListPembelianBarang()){
            int q = b.getQuantity();
            total += q;
        }

        return total;
    }

    @Override
    public String getNoInvoice(PembelianModel pembelian) {
        String invoice = "";
    
        int b = 0;
        String member = pembelian.getMember().getNamaMember().substring(0,1);
        member = member.toUpperCase();
        char[] ch  = member.toCharArray();
        for(char c : ch){
            int temp = (int)c;
            int temp_integer = 64; //for upper case
            if(temp<=90 & temp>=65)
                b = temp-temp_integer;
        }

        String apasi = String.valueOf(b);
        if (apasi.length() > 1){
            apasi = apasi.substring(0,1);
        }

        int admincut = pembelian.getNamaAdmin().length();
        String admin = pembelian.getNamaAdmin().substring(admincut-1, admincut);

        // tanggalPembelian
        String tanggalcut = pembelian.getTanggalPembelian().toString();
        String hari = tanggalcut.substring(8,10);
        String bulan = tanggalcut.substring(5,7);
        String tanggal = hari + bulan;

        // isCash
        Boolean tipecut = pembelian.getIsCash();
        String tipe = "";
        if (tipecut){
            tipe = "02";
        } else {
            tipe = "01";
        }

        // Campur
        int d = Integer.parseInt(hari);
        int m = Integer.parseInt(bulan);
        int tambah = (d + m) * 5;
        String campur = Integer.toString(tambah);
        if (campur.length() == 2){
            campur = "0" + campur;
        }

        // Randomizer
        String[] strArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S","T", "U", "V", "W" };
        Random rand = new Random();
        int res1 = rand.nextInt(strArr.length);
        int res2 = rand.nextInt(strArr.length);
        String kapital = strArr[res1] + strArr[res2];

        invoice += ( apasi + admin + tanggal + tipe + campur + kapital );
        System.out.println("hadoh");
        return invoice;
    }

    public String updateNoInvoice(PembelianModel pembelian){
        int b = 0;
        String member = pembelian.getMember().getNamaMember().substring(0,1);
        member = member.toUpperCase();
        System.out.println("inisial member: " + member);
        char[] ch  = member.toCharArray();
        for(char c : ch){
            int temp = (int)c;
            int temp_integer = 64; //for upper case
            if(temp<=90 & temp>=65)
                b = temp-temp_integer;
        }

        String apasi = String.valueOf(b);
        if (apasi.length() > 1){
            apasi = apasi.substring(0,1);
        }

        
        String inv = pembelian.getNoInvoice();
        if (!apasi.equals(pembelian.getNoInvoice().substring(0,1))){
            String invcut = inv.substring(1,13);
            inv = apasi + invcut;
            }

        return inv;
    }

    public int getJumBarang(PembelianModel pembelian){
        List<PembelianBarangModel> ah = pembelian.getListPembelianBarang();
        int simpan = 0;
        for (PembelianBarangModel p : ah){
            simpan += p.getQuantity();
        }

        return simpan;
    }

}
