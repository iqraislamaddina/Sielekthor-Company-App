package apap.tugas.sielekthor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "pembelian_barang")

public class PembelianBarangModel {
    //Relasi dengan AgensiModel

    @Id
    @Column(name="id_pembelian_barang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPembelianBarang;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_barang", referencedColumnName = "id_barang", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BarangModel barang;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pembelian", referencedColumnName = "id_pembelian", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PembelianModel pembelian;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date tanggalGaransi;

}
