package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemVenda implements Serializable {
    
    @Id
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Produto.class)
    private Long idProduto;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Venda.class)
    private Long idVenda;
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;
    private BigDecimal quantidade;
    private BigDecimal porcentagemDesconto;
    private Boolean sugestao;
    private Timestamp dataGarantia;
    private BigDecimal porcentagemGarantia;

    public ItemVenda(){
        id = Calendar.getInstance().getTimeInMillis();
    }
    
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

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
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
    
}
