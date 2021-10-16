package apap.tugas.sielekthor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.TipeModel;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.TipeService;

import org.springframework.validation.BindingResult;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BarangController {

    @Qualifier("barangServiceImpl")
    @Autowired
    private BarangService barangService;

    @Qualifier("tipeServiceImpl")
    @Autowired
    private TipeService tipeService;

    @GetMapping("/barang/tambah")
    public String addBarangFormPage(Model model) {
        model.addAttribute("barang", new BarangModel());
        List<TipeModel> listTipe = tipeService.getListTipe();
        model.addAttribute("listTipe", listTipe);
        return "form-add-barang";
    }

    @PostMapping(value = "/barang/tambah", params = { "submitBarang" })
    public String addBarangSubmitPage(@ModelAttribute BarangModel barang, Model model) {
        barangService.addBarang(barang);
        model.addAttribute("idBarang", barang.getIdBarang());
        model.addAttribute("kodeBarang", barang.getKodeBarang());
        return "add-barang";
    }

    @GetMapping("/barang")
    public String listBarang(Model model) {
        List<BarangModel> listBarang = barangService.getListBarang();
        model.addAttribute("listBarang", listBarang);
        return "viewall-barang";
    }

    @GetMapping("/barang/{idBarang}")
    public String viewDetailBarangPage(@PathVariable Long idBarang, Model model) {
        BarangModel barang = barangService.getBarangByidBarang(idBarang);
        model.addAttribute("barang", barang);
        return "view-barang";
    }

    @GetMapping("/barang/ubah/{idBarang}")
    public String updateBarangFormPage(@PathVariable Long idBarang, Model model) {
        BarangModel barang = barangService.getBarangByidBarang(idBarang);
        List<TipeModel> listTipe = tipeService.getListTipe();
        model.addAttribute("listTipe", listTipe);
        model.addAttribute("barang", barang);
        return "form-update-barang";
    }

    @PostMapping("/barang/ubah")
    public String updatebarangSubmitPage(@ModelAttribute BarangModel barang, Model model) {
        BarangModel updatedbarang = barangService.updateBarang(barang);
        model.addAttribute("idBarang", updatedbarang.getIdBarang());
        model.addAttribute("kodeBarang", barang.getKodeBarang());
        return "update-barang";
    }

    // @GetMapping("/barang/hapus/{idBarang}")
    // public String deleteBarangPage(@PathVariable Long idBarang, Model model) {
    // BarangModel barang = barangService.getBarangByidBarang(idBarang);
    // List<BarangModel> listBarang = barangService.getListBarang();
    // listBarang.remove(barang);
    // barangService.deleteBarangByidBarang(idBarang);
    // model.addAttribute("barang", barang);
    // return "delete-barang";
    // }

    @GetMapping("/barang/cari")
    public String cariBarangFormPage(@RequestParam(value = "idTipe") Long idTipe,
            @RequestParam(value = "isAvailable") Boolean isAvailable, Model model) {

        List<TipeModel> listTipe = tipeService.getListTipe();
        List<BarangModel> temp = new ArrayList<>();
        TipeModel tipe = tipeService.getTipeByidTipe(idTipe);
        List<BarangModel> listBarangByTipe = tipe.getListBarang();

        for (BarangModel barang : listBarangByTipe) {
            if (barang.getStokBarang() > 0 && isAvailable == true) {
                temp.add(barang);
            } else if (barang.getStokBarang() <= 0 && isAvailable == false) {
                temp.add(barang);
            }
        }

        model.addAttribute("listTipe", listTipe);
        model.addAttribute("listBarang", listBarangByTipe);
        model.addAttribute("listBarang", temp);
        return "cari-barang";
    }

    @GetMapping("/filter-barang")
    public String cariBarangPage(Model model) {
        List<TipeModel> listTipe = tipeService.getListTipe();
        List<BarangModel> listBarang = barangService.getListBarang();

        model.addAttribute("listTipe", listTipe);
        model.addAttribute("listBarang", listBarang);
        return "cari-barang";
    }

}
