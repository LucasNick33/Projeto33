package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
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
    @Column(name="id")
    private Long id;
    @Column(nullable = false, name="nome")
    private String nome;
    @Column(nullable = false, name="marca")
    private String marca;
    @Column(name="descricao")
    private String descricao;
    @Column(name="preco_compra")
    private BigDecimal precoCompra;
    @Column(name="preco_venda")
    private BigDecimal precoVenda;
    @Column(nullable = false, name="categoria")
    private String categoria;
    @Column(name="porcentagem_desconto")
    private BigDecimal porcentagemDesconto;
    @Column(name="sugestao")
    private Boolean sugestao;
    @Column(name="ativo")
    private Boolean ativo;
    
    public Produto(){
        descricao = "";
        precoCompra = BigDecimal.ZERO;
        precoVenda = BigDecimal.ZERO;
        porcentagemDesconto = BigDecimal.ZERO;
        sugestao = false;
        ativo = true;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    
    @Override
    public String toString(){
        return getCategoria() + ": " + getMarca() + " " + getNome() + " = " + NumUtils.formataValorMonetario(getPrecoVenda());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
