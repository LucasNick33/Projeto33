package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import util.NumUtils;

@Entity
@Table(
    uniqueConstraints=
        @UniqueConstraint(columnNames={"nome", "marca"})
)
public class Produto implements Serializable {
    
    @Id
    private Long id;
    private String nome;
    private String marca;
    private String descricao;
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;
    private String categoria;
    private BigDecimal porcentagemDesconto;
    private Boolean sugestao;
    private Timestamp dataGarantia;
    private BigDecimal porcentagemGarantia;
    private Boolean ativo;

    public Produto(){
        id = Calendar.getInstance().getTimeInMillis();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }
    
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(BigDecimal porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
    }

    public Boolean getSugestao() {
        return sugestao;
    }

    public void setSugestao(Boolean sugestao) {
        this.sugestao = sugestao;
    }

    public Timestamp getDataGarantia() {
        return dataGarantia;
    }

    public void setDataGarantia(Timestamp dataGarantia) {
        this.dataGarantia = dataGarantia;
    }

    public BigDecimal getPorcentagemGarantia() {
        return porcentagemGarantia;
    }

    public void setPorcentagemGarantia(BigDecimal porcentagemGarantia) {
        this.porcentagemGarantia = porcentagemGarantia;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    
    @Override
    public String toString(){
        return getCategoria() + ": " + getMarca() + " " + getNome() + " = " + NumUtils.formataValor(getPrecoVenda());
    }
    
}
