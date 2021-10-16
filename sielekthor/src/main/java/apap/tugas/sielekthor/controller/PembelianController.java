package apap.tugas.sielekthor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.model.TipeModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.MemberService;
import apap.tugas.sielekthor.service.PembelianBarangService;
import apap.tugas.sielekthor.service.PembelianService;
import apap.tugas.sielekthor.service.TipeService;

import org.springframework.validation.BindingResult;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PembelianController {

    @Qualifier("pembelianServiceImpl")
    @Autowired
    private PembelianService pembelianService;

    @Autowired
    private TipeService tipeService;

    @Autowired
    private BarangService barangService;

    @Autowired
    private PembelianBarangService pembelianBarangService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/pembelian/tambah")
    public String addpembelianFormPage(Model model) {
        model.addAttribute("pembelian", new PembelianModel());
        List<TipeModel> listTipe = tipeService.getListTipe();
        List<MemberModel> listMember = memberService.getListMember();
        model.addAttribute("listTipe", listTipe);
        model.addAttribute("listMember", listMember);
        return "form-add-pembelian";
    }

    @PostMapping(value = "/pembelian/tambah", params = { "submitpembelian" })
    public String addPembelianSubmitPage(@ModelAttribute PembelianModel pembelian, Model model) {

        System.out.println("print disini: " + pembelian.getListPembelianBarang());
        for (PembelianBarangModel barang : pembelian.getListPembelianBarang()){
            if (pembelianBarangService.isAvailable(barang) == true){
                int totalbeli = pembelianService.getTotaPembelian(pembelian);
                pembelian.setTotalPembelian(totalbeli);
        
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                pembelian.setTanggalPembelian(timestamp);
        
                String invo = pembelianService.getNoInvoice(pembelian);
                pembelian.setNoInvoice(invo);
        
                List<PembelianBarangModel> rb = pembelian.getListPembelianBarang();
                pembelian.setListPembelianBarang(new ArrayList<PembelianBarangModel>());
        
                pembelian.setTanggalPembelian(timestamp);
                pembelianService.addPembelian(pembelian);
        
                for (PembelianBarangModel pb : rb){
                    pb.setPembelian(pembelian);
                    Date apa = pembelianBarangService.getTanggalGaransi(pb);
                    pb.setTanggalGaransi(apa);
        
                    pembelian.getListPembelianBarang().add(pb);
                    System.out.println(pb.getIdPembelianBarang());
                    pembelianBarangService.addPembelianBarang(pb);
                }
                
                
            } else {
                model.addAttribute("error_message", "Stok barang kurang!");
                return "error";
            }
        }

        model.addAttribute("noInvoice", pembelian.getNoInvoice());
        model.addAttribute("idPembelian", pembelian.getIdPembelian());
        return "add-pembelian";
        
    }

    @GetMapping("/pembelian")
    public String listPembelian(Model model) {
        List<PembelianModel> listPembelian = pembelianService.getListPembelian();

        model.addAttribute("listPembelian", listPembelian);
        return "viewall-pembelian";
    }

    @GetMapping("/pembelian/{idPembelian}")
    public String viewDetailPembelianPage(@PathVariable Long idPembelian, Model model) {
        PembelianModel pembelian = pembelianService.getPembelianByidPembelian(idPembelian);
        MemberModel member = pembelian.getMember();
        List<PembelianBarangModel> listPembelianBarang = pembelian.getListPembelianBarang();
        int jumlah = pembelianService.getJumBarang(pembelian);

        model.addAttribute("jumlah", jumlah);
        model.addAttribute("member", member);
        model.addAttribute("listPembelianBarang", listPembelianBarang);
        model.addAttribute("pembelian", pembelian);
        return "view-pembelian";
    }

    @PostMapping(value = "/pembelian/tambah", params = { "addRow" })
    public String addRow(@ModelAttribute PembelianModel pembelian, BindingResult bindingResult, Model model) {
        List<BarangModel> listBarang = barangService.getListBarang();


        if (pembelian.getListPembelianBarang() == null) {
            pembelian.setListPembelianBarang(new ArrayList<PembelianBarangModel>());
        }

        List<PembelianBarangModel> listPembelianBarang = pembelian.getListPembelianBarang();
        listPembelianBarang.add(new PembelianBarangModel());

        List<MemberModel> listMember = memberService.getListMember();
        model.addAttribute("listMember", listMember);
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("listPembelianBarang", listPembelianBarang);
        model.addAttribute("pembelian", pembelian);
        return "form-add-pembelian";
    }

    @RequestMapping(value = "/pembelian/tambah", method = RequestMethod.POST, params = { "deleteRow" })
    public String deleteRow(@ModelAttribute PembelianModel pembelian, final BindingResult bindingResult,
            final HttpServletRequest req, Model model) {
        List<BarangModel> listBarang = barangService.getListBarang();

        final Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
        pembelian.getListPembelianBarang().remove(rowId.intValue());

        model.addAttribute("listBarang", listBarang);
        model.addAttribute("pembelian", pembelian);
        return "form-add-pembelian";
    }

    @PostMapping(value="/pembelian/hapus/{idPembelian}")
    public String deletePembelianPage(@PathVariable Long idPembelian, Model model) {
        PembelianModel pembelian = pembelianService.getPembelianByidPembelian(idPembelian);
        List<PembelianModel> listpembelian = pembelianService.getListPembelian();
        for (PembelianBarangModel p : pembelian.getListPembelianBarang()){
            pembelianBarangService.deletePembelianBarang(p);
            barangService.updateBarang(p.getBarang());
        }

        listpembelian.remove(pembelian);
        pembelianService.deletePembelianByidPembelian(idPembelian);
        model.addAttribute("pembelian", pembelian);
        return "delete-pembelian";
    }

    @GetMapping("/pembelian/cari")
    public String cariPembelianFormPage(
        @RequestParam(value="idMember") Long idMember,
        @RequestParam(value="isCash") Boolean isCash,
        Model model) {

        MemberModel member = memberService.getMemberByidMember(idMember);
        List<MemberModel> listMember = memberService.getListMember();
        List<PembelianModel> pb = member.getListPembelian();
        List<PembelianModel> temp = new ArrayList<>();
        for (PembelianModel p : pb){
            if (p.getIsCash().equals(isCash)){
                temp.add(p);
            }
        }
        
        System.out.println(isCash);
        model.addAttribute("listPembelian", temp);
        model.addAttribute("listMember", listMember);
        return "cari-pembelian";
    }

    @GetMapping("/filter-pembelian")
    public String cariPembelianPage(Model model) {
        List<MemberModel> listMember = memberService.getListMember();
        List<PembelianBarangModel> listPembelian =  new ArrayList<>();
        
        model.addAttribute("listMember", listMember);
        model.addAttribute("listPembelian", listPembelian);
        return "cari-pembelian";
    }

}
