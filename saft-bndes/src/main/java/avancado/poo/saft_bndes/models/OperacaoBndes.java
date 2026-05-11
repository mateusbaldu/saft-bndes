package avancado.poo.saft_bndes.models;

import avancado.poo.saft_bndes.converters.BigDecimalConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "operacao_bndes")
public class OperacaoBndes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CsvBindByName(column = "cliente")
    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @CsvBindByName(column = "setor_cnae")
    @Column(name = "setor")
    private String setor;

    @CsvCustomBindByName(column = "valor_da_operacao_em_reais", converter = BigDecimalConverter.class)
    @Column(name = "valor")
    private BigDecimal valor;

    @CsvBindByName(column = "uf")
    @Column(name = "estado")
    private String estado;

    @CsvDate("yyyy-MM-dd")
    @CsvBindByName(column = "data_da_contratacao")
    @Column(name = "data_contratacao")
    private LocalDate data;

    public OperacaoBndes() {
    }

    public OperacaoBndes(Long id, String nomeEmpresa, String setor, BigDecimal valor, String estado, LocalDate data) {
        this.id = id;
        this.nomeEmpresa = nomeEmpresa;
        this.setor = setor;
        this.valor = valor;
        this.estado = estado;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OperacaoBndes{" +
                "id=" + id +
                ", nomeEmpresa='" + nomeEmpresa + '\'' +
                ", setor='" + setor + '\'' +
                ", valor=" + valor +
                ", estado='" + estado + '\'' +
                ", data=" + data +
                '}';
    }
}
