package apap.tugas.sielekthor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tipe")
public class TipeModel implements Serializable{
    @Id
    @Max(20)
    @Column(name="id_tipe")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipe; 

    @NotNull
    @Size(max=30)
    @Column(name="nama_tipe", nullable = false)
    private String namaTipe;

    @Column(name="deskripsi_tipe", nullable = false)
    private Boolean deskripsiTipe;

    //Relasi dengan AgensiModel
    @OneToMany(mappedBy = "tipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<BarangModel> listBarang;
}
