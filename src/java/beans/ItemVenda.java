package beans;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemVenda implements Serializable {
    
    @Id
    private Long id;
    private Long idProduto;
    private Long idVenda;
    private Double precoCompra;
    private Double precoVenda;
    private Double quantidade;
    private Double porcentagemDesconto;
    private Boolean sugestao;
    private Timestamp dataGarantia;
    private Double porcentagemGarantia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Long getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Long idVenda) {
        this.idVenda = idVenda;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(Double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(Double porcentagemDesconto) {
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

    public Double getPorcentagemGarantia() {
        return porcentagemGarantia;
    }

    public void setPorcentagemGarantia(Double porcentagemGarantia) {
        this.porcentagemGarantia = porcentagemGarantia;
    }
    
}
