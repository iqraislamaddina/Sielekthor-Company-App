package apap.tugas.sielekthor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.service.MemberService;
import apap.tugas.sielekthor.service.PembelianBarangService;
import apap.tugas.sielekthor.service.PembelianService;

import org.springframework.validation.BindingResult;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

    @Qualifier("memberServiceImpl")
    @Autowired
    private MemberService memberService;

    @Autowired
    private PembelianBarangService pembelianBarangService;

    @Autowired
    private PembelianService pembelianService;

    @GetMapping("/member/tambah")
    public String addMemberFormPage(Model model) {
        model.addAttribute("member", new MemberModel());
        return "form-add-Member";
    }

    @RequestMapping(value = "/member/tambah")
    public String addMemberSubmitPage(@ModelAttribute MemberModel member, Model model) {
        memberService.addMember(member);
        System.out.print("TANGGAL LAHIR: " + member.getTanggalLahir());
        System.out.print("TANGGAL PENDATARAN: " + member.getTanggalPendaftaran());
        model.addAttribute("idMember", member.getIdMember());
        return "add-member";
    }

    @GetMapping("/member")
    public String listMember(Model model) {
        List<MemberModel> listMember = memberService.getListMember();
        model.addAttribute("listMember", listMember);
        return "viewall-member";
    }

    @GetMapping("/member/ubah/{idMember}")
    public String updateMemberFormPage(@PathVariable Long idMember, Model model) {
        MemberModel member = memberService.getMemberByidMember(idMember);

        model.addAttribute("member", member);
        return "form-update-member";
    }

    @PostMapping("/member/ubah")
    public String updateMemberSubmitPage(@ModelAttribute MemberModel member, Model model) {
        MemberModel updatedMember = memberService.updateMember(member);

        List<PembelianModel> capek = updatedMember.getListPembelian();
        for (PembelianModel p : capek) {
            p.setNoInvoice(pembelianService.updateNoInvoice(p));
            pembelianService.updatePembelian(p);

        }

        model.addAttribute("member", updatedMember);
        model.addAttribute("idMember", updatedMember.getIdMember());
        return "update-member";
    }
}
