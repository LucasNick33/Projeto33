package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Venda implements Serializable {

    @Id
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Usuario.class)
    private Long idUsuario;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Cliente.class)
    private Long idCliente;
    private BigDecimal valor;
    private Timestamp dataVenda;
    private BigDecimal porcentagemDesconto;
    private String estoque;
    private Boolean pago;
    private Boolean ativo;
    @Transient
    private List<ItemVenda> itens;
    @Transient
    private List<Pagamento> pagamentos;

    public Venda(){
        valor = BigDecimal.ZERO;
        dataVenda = new Timestamp(Calendar.getInstance().getTimeInMillis());
        porcentagemDesconto = BigDecimal.ZERO;
        pago = false;
        ativo = true;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Timestamp getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Timestamp dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(BigDecimal porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
    }

    public String getEstoque() {
        return estoque;
    }

    public void setEstoque(String estoque) {
        this.estoque = estoque;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public BigDecimal getDesconto() {
        return valor.subtract(getTotal());
    }

    public void setDesconto(BigDecimal valor) {
        porcentagemDesconto = valor.divide(this.valor, MathContext.DECIMAL128).multiply(new BigDecimal(100));
    }

    public BigDecimal getValorPago() {
        BigDecimal pagamentoParcial = BigDecimal.ZERO;
        for (Pagamento pgmt : getPagamentos()) {
            pagamentoParcial = pagamentoParcial.add(pgmt.getValor());
        }
        return pagamentoParcial;
    }

    public BigDecimal getValorNaoPago() {
        return getTotal().subtract(getValorPago());
    }

    /**
     *
     * @return valor da venda - desconto
     */
    public BigDecimal getTotal() {
        return valor.multiply(porcentagemDesconto.divide(new BigDecimal(100), MathContext.DECIMAL128));
    }

}
