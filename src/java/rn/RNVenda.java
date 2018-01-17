package rn;

import beans.Estoque;
import beans.ItemVenda;
import beans.Pagamento;
import beans.Produto;
import beans.Venda;
import dao.EstoqueDao;
import dao.VariaveisGlobais;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import util.StringUtils;

public class RNVenda {

    private Venda venda;
    private String mensagem;
    private final EstoqueDao estoqueDao;
    private final Boolean checarEstoque;
    private ItemVenda item;
    private ItemVenda itemEditavel;
    private Pagamento pagamento;

    public RNVenda() {
        venda = new Venda();
        venda.setId(Calendar.getInstance().getTimeInMillis());
        venda.setItens(new ArrayList<>());
        venda.setPagamentos(new ArrayList<>());
        venda.setValor(BigDecimal.ZERO);
        venda.setDataVenda(new Timestamp(venda.getId()));

        estoqueDao = new EstoqueDao();
        estoqueDao.setEstoque(new Estoque());
        estoqueDao.getEstoque().setNome(VariaveisGlobais.usuario.getEstoque());

        checarEstoque = true;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public ItemVenda getItem() {
        return item;
    }

    public void setItem(ItemVenda item) {
        this.item = item;
        itemEditavel = item.copiar();
    }

    public ItemVenda getItemEditavel() {
        return itemEditavel;
    }

    public void setItemEditavel(ItemVenda itemEditavel) {
        this.itemEditavel = itemEditavel;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void adicionarItem(Produto produto, BigDecimal quantidade) {
        for (ItemVenda iv : venda.getItens()) {
            if(iv.getProduto().equals(produto)){
                item = iv;
                itemEditavel = iv.copiar();
                itemEditavel.setQuantidade(itemEditavel.getQuantidade().add(quantidade));
                alterarItem();
                return;
            }
        }
        
        if (checarEstoque && !produto.getSugestao()) {
            List<Estoque> estoque = estoqueDao.listar(produto);
            if (estoque.isEmpty() || estoque.get(0).getQuantidade().compareTo(quantidade) < 0) {
                mensagem = "Produto sem estoque!";
                return;
            }
        }

        ItemVenda item = new ItemVenda();
        item.setId(Calendar.getInstance().getTimeInMillis());
        item.setIdVenda(venda.getId());
        item.setProduto(produto);
        item.setQuantidade(quantidade);

        if (checarEstoque && !produto.getSugestao()) {
            if (estoqueDao.retirarDoEstoque(item)) {
                venda.getItens().add(item);
            } else {
                mensagem = "erro ao adicionar item de venda!";
            }
        } else {
            venda.getItens().add(item);
        }
    }

    public void alterarItem() {
        if (itemEditavel.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            retirarItem(itemEditavel);
            return;
        }

        BigDecimal diferencaQuantidade = itemEditavel.getQuantidade().subtract(item.getQuantidade());
        if (checarEstoque && !itemEditavel.getProduto().getSugestao() && !diferencaQuantidade.equals(BigDecimal.ZERO)) {
            List<Estoque> estoque = estoqueDao.listar(itemEditavel.getProduto());
            if (estoque.isEmpty() || estoque.get(0).getQuantidade().compareTo(diferencaQuantidade) < 0) {
                itemEditavel.setQuantidade(item.getQuantidade());
                mensagem = "Produto sem estoque!";
            } else {
                BigDecimal quantidade = itemEditavel.getQuantidade();
                itemEditavel.setQuantidade(diferencaQuantidade.negate());
                if(estoqueDao.adicionarAoEstoque(itemEditavel)){
                    itemEditavel.setQuantidade(quantidade);
                } else {
                    itemEditavel.setQuantidade(item.getQuantidade());
                    mensagem = "Erro ao editar item de venda!";
                }
            }
        }

        if (!Permissao.temPermissao(VariaveisGlobais.usuario.getPermissoes(), Permissao.DESCONTO_ITEM_VENDA) || itemEditavel.getPorcentagemDesconto().compareTo(itemEditavel.getProduto().getPorcentagemDesconto()) > 0) {
            itemEditavel.setPorcentagemDesconto(item.getPorcentagemDesconto());
        }

        ItemVenda iv = venda.getItens().get(venda.getItens().indexOf(item));
        iv = itemEditavel;
    }

    public void retirarItem(ItemVenda item) {
        if (checarEstoque && !item.getProduto().getSugestao()) {
            if (estoqueDao.adicionarAoEstoque(item)) {
                venda.getItens().remove(item);
            } else {
                mensagem = "Erro ao retirar item de venda!";
            }
        } else {
            venda.getItens().remove(item);
        }
    }

    public void calcularTotal(){
        BigDecimal totalItens = BigDecimal.ZERO;
        for(ItemVenda iv : venda.getItens()){
            totalItens = totalItens.add(iv.getTotalItem());
        }
        venda.setValor(totalItens);
    }
    
    public void adicionarPagamento(Pagamento pagamento){
        if(venda.getValorNaoPago().compareTo(pagamento.getValor()) < 0){
            mensagem = "Valor desse pagamento é maior que o valor não pago!";
            return;
        }
        pagamento.setTipo(StringUtils.padronizar(pagamento.getTipo()));
        venda.getPagamentos().add(pagamento);
    }
    
    public void retirarPagamento(Pagamento pagamento){
        venda.getPagamentos().remove(pagamento);
    }
    
    public void cancelarVenda(){
        for (ItemVenda iv : venda.getItens()) {
            while(!estoqueDao.adicionarAoEstoque(iv)){
            }
        }
    }
    
}
