package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Venda implements Serializable {
    
    @Id
    private Long id;
    private Long idUsuario;
    private Long idCliente;
    private BigDecimal porcentagemDesconto;

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

    public BigDecimal getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(BigDecimal porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
    }
    
}
