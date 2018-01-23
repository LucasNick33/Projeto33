package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import util.NumUtils;

@Entity
public class ItemVenda implements Serializable {
    
    @Id
    @Column(name="id")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Produto.class)
    private Long idProduto;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Venda.class)
    private Long idVenda;
    @Column(name="preco_compra")
    private BigDecimal precoCompra;
    @Column(name="preco_venda")
    private BigDecimal precoVenda;
    @Column(name="quantidade")
    private BigDecimal quantidade;
    @Column(name="porcentagem_desconto")
    private BigDecimal porcentagemDesconto;
    @Column(name="sugestao")
    private Boolean sugestao;
    @Transient
    private Produto produto;

    public ItemVenda(){
        precoCompra = BigDecimal.ZERO;
        precoVenda = BigDecimal.ZERO;
        quantidade = BigDecimal.ZERO;
        porcentagemDesconto = BigDecimal.ZERO;
        sugestao = false;
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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        this.setIdProduto(produto.getId());
        this.setPrecoCompra(produto.getPrecoCompra());
        this.setPrecoVenda(produto.getPrecoVenda());
        this.setSugestao(produto.getSugestao());
    }

    public ItemVenda copiar(){
        ItemVenda item = new ItemVenda();
        item.id = id;
        item.idVenda = idVenda;
        item.idProduto = idProduto;
        item.porcentagemDesconto = porcentagemDesconto;
        item.produto = produto;
        item.precoCompra = precoCompra;
        item.precoVenda = precoVenda;
        item.quantidade = quantidade;
        item.sugestao = sugestao;
        return item;
    }
    
    public BigDecimal getDesconto(){
        return getPorcentagemDesconto().divide(new BigDecimal(100), MathContext.DECIMAL128).multiply(getValorItem());
    }
    
    public void setDesconto(BigDecimal valor){
        porcentagemDesconto = valor.divide(getValorItem(), MathContext.DECIMAL128).multiply(new BigDecimal(100));
    }
    
    /**
     *
     * @return preço de venda x quantidade
     */
    public BigDecimal getValorItem(){
        return getPrecoVenda().multiply(getQuantidade());
    }
    
    /**
     *
     * @return (preço de venda x quantidade) - desconto
     */
    public BigDecimal getTotalItem(){
        return getValorItem().subtract(getDesconto());
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
        final ItemVenda other = (ItemVenda) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return produto.getNome() + " (" + NumUtils.quantidade(quantidade) + " X " + NumUtils.formataValor(precoVenda) + ") - " + NumUtils.formataValor(getDesconto())  + " = " + NumUtils.formataValor(getTotalItem());
    }
    
}
