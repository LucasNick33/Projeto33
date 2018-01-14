package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pagamento implements Serializable {
    
    @Id
    private Long id;
    private Long idVenda;
    private BigDecimal valor;
    private String tipo;
    private Timestamp dataVencimento;
    private Boolean pago;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Long idVenda) {
        this.idVenda = idVenda;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Timestamp getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Timestamp dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }
    
}
