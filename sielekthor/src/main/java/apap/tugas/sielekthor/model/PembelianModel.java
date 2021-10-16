package apap.tugas.sielekthor.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "pembelian")

public class PembelianModel {
    @Id
    @Column(name="id_pembelian")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPembelian;

    @NotNull
    @Column(name="total_pembelian", nullable = false)
    private Integer totalPembelian;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd:MM:yyyy")
    private Timestamp tanggalPembelian;

    @NotNull
    @Size(max=255)
    @Column(name="nama_admin", nullable = false)
    private String namaAdmin;

    @NotNull
    @Size(max=255)
    @Column(name="no_invoice", nullable = false)
    private String noInvoice;

    @NotNull
    @Column(name="is_cash", nullable = false)
    private Boolean isCash;

    //Relasi dengan TourGuideModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_member", referencedColumnName = "id_member", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberModel member;

    //Relasi dengan DestinasiModel
    @OneToMany(mappedBy = "pembelian", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<PembelianBarangModel> listPembelianBarang;
}
