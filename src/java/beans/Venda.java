package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Venda implements Serializable {
    
    @Id
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Usuario.class)
    private Long idUsuario;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Cliente.class)
    private Long idCliente;
    private BigDecimal total;
    private BigDecimal porcentagemDesconto;
    private String estoque;

    public Venda(){
        id = Calendar.getInstance().getTimeInMillis();
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
    
}
