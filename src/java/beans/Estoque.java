package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    uniqueConstraints=
        @UniqueConstraint(columnNames={"idproduto_id", "nome"})
)
public class Estoque implements Serializable {
    
    @Id
    @Column(name="id")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Produto.class)
    private Long idProduto;
    @Column(name="quantidade")
    private BigDecimal quantidade;
    @Column(nullable = false, name="nome")
    private String nome;

    public Estoque(){
        quantidade = BigDecimal.ZERO;
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

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
