package rn;

import beans.Cliente;
import beans.Estoque;
import beans.ItemVenda;
import beans.Pagamento;
import beans.Produto;
import beans.Venda;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import util.StringUtils;

public class RNVenda {

    private final VariaveisGlobais variaveisGlobais;
    private Venda venda;
    private String mensagem;
    private final Boolean checarEstoque;
    private ItemVenda item;
    private ItemVenda itemEditavel;
    private Pagamento pagamento;
    private Boolean editandoVenda;
    private Cliente cliente;

    public RNVenda(VariaveisGlobais variaveisGlobais) {
        this.variaveisGlobais = variaveisGlobais;
        
        cliente = variaveisGlobais.getClienteDao().getCliente();
        
        venda = new Venda();
        venda.setId(Calendar.getInstance().getTimeInMillis());
        venda.setIdUsuario(variaveisGlobais.getUsuario().getId());
        venda.setItens(new ArrayList<>());
        venda.setPagamentos(new ArrayList<>());
        venda.setEstoque(variaveisGlobais.getUsuario().getEstoque());
        editandoVenda = false;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        variaveisGlobais.getClienteDao().setCliente(cliente);
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void adicionarItem(Produto produto, BigDecimal quantidade) {
        for (ItemVenda iv : venda.getItens()) {
            if (iv.getProduto().equals(produto)) {
                item = iv;
                itemEditavel = iv.copiar();
                itemEditavel.setQuantidade(itemEditavel.getQuantidade().add(quantidade));
                alterarItem();
                return;
            }
        }

        if (checarEstoque && !produto.getSugestao()) {
            List<Estoque> estoque = variaveisGlobais.getEstoqueDao().listar(produto);
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
            if (variaveisGlobais.getEstoqueDao().retirarDoEstoque(item)) {
                venda.getItens().add(item);
            } else {
                mensagem = "erro ao adicionar item de venda!";
            }
        } else {
            venda.getItens().add(item);
        }
        calcularValorVenda();
    }

    public void alterarItem() {
        if (itemEditavel.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            retirarItem(itemEditavel);
            return;
        }

        BigDecimal diferencaQuantidade = itemEditavel.getQuantidade().subtract(item.getQuantidade());
        if (checarEstoque && !itemEditavel.getProduto().getSugestao() && !diferencaQuantidade.equals(BigDecimal.ZERO)) {
            List<Estoque> estoque = variaveisGlobais.getEstoqueDao().listar(itemEditavel.getProduto());
            if (estoque.isEmpty() || estoque.get(0).getQuantidade().compareTo(diferencaQuantidade) < 0) {
                itemEditavel.setQuantidade(item.getQuantidade());
                mensagem = "Produto sem estoque!";
            } else {
                BigDecimal quantidade = itemEditavel.getQuantidade();
                itemEditavel.setQuantidade(diferencaQuantidade.negate());
                if (variaveisGlobais.getEstoqueDao().adicionarAoEstoque(itemEditavel)) {
                    itemEditavel.setQuantidade(quantidade);
                } else {
                    itemEditavel.setQuantidade(item.getQuantidade());
                    mensagem = "Erro ao editar item de venda!";
                }
            }
        }

        if (!Permissao.temPermissao(variaveisGlobais.getUsuario().getPermissoes(), Permissao.DESCONTO_ITEM_VENDA) || itemEditavel.getPorcentagemDesconto().compareTo(itemEditavel.getProduto().getPorcentagemDesconto()) > 0) {
            itemEditavel.setPorcentagemDesconto(item.getPorcentagemDesconto());
        }

        ItemVenda iv = venda.getItens().get(venda.getItens().indexOf(item));
        iv = itemEditavel;
        calcularValorVenda();
    }

    public void retirarItem(ItemVenda item) {
        if (checarEstoque && !item.getProduto().getSugestao()) {
            if (variaveisGlobais.getEstoqueDao().adicionarAoEstoque(item)) {
                venda.getItens().remove(item);
            } else {
                mensagem = "Erro ao retirar item de venda!";
            }
        } else {
            venda.getItens().remove(item);
        }
        calcularValorVenda();
    }

    private void calcularValorVenda() {
        BigDecimal totalItens = BigDecimal.ZERO;
        for (ItemVenda iv : venda.getItens()) {
            if(iv.getProduto().getSugestao()){
               continue;
            }
            totalItens = totalItens.add(iv.getTotalItem());
        }
        venda.setValor(totalItens);
    }

    public void adicionarPagamento(Pagamento pagamento) {
        if (venda.getValorNaoPago().compareTo(pagamento.getValor()) < 0) {
            mensagem = "Valor desse pagamento é maior que o valor não pago!";
            return;
        }
        pagamento.setTipo(StringUtils.padronizar(pagamento.getTipo()));
        venda.getPagamentos().add(pagamento);
    }

    public void retirarPagamento(Pagamento pagamento) {
        venda.getPagamentos().remove(pagamento);
    }

    public void cancelarVenda() {
        for (ItemVenda iv : venda.getItens()) {
            if (checarEstoque && !iv.getProduto().getSugestao()) {
                while (!variaveisGlobais.getEstoqueDao().adicionarAoEstoque(iv)) {
                }
            }
        }
    }

    public void finalizarVenda() {
        if(variaveisGlobais.getUsuario().getPorcentagemDesconto().compareTo(venda.getPorcentagemDesconto()) < 0){
            mensagem = "Usuário não tem permissão para dar essa porcentagem de desconto na venda!";
            return;
        }
        
        if (editandoVenda && !Permissao.temPermissao(variaveisGlobais.getUsuario().getPermissoes(), Permissao.EDITAR_VENDA)) {
            mensagem = "Usuário não tem permissão para editar venda!";
            return;
        }

        if (venda.getValorNaoPago().compareTo(BigDecimal.ZERO) > 0 && (cliente.getLimiteDebto() == null || cliente.getLimiteDebto().compareTo(variaveisGlobais.getClienteDao().debto().add(venda.getValorNaoPago())) < 0)) {
            mensagem = "A venda não foi paga e o cliente não tem limite de debto disponível!";
            return;
        }
        
        if (editandoVenda) {
            atualizarVenda();
        } else {
            inserirVenda();
        }
    }

    private void inserirVenda() {
        variaveisGlobais.getVendaDao().setVenda(venda);
        if (!variaveisGlobais.getVendaDao().inserir()) {
            mensagem = "Erro ao inserir venda!";
            return;
        }
        for (Pagamento pgmt : venda.getPagamentos()) {
            variaveisGlobais.getPagamentoDao().setPagamento(pgmt);
            while (!variaveisGlobais.getPagamentoDao().inserir()) {
            }
        }
        for (ItemVenda item : venda.getItens()) {
            variaveisGlobais.getItemDao().setItem(item);
            while (!variaveisGlobais.getItemDao().inserir()) {
            }
        }
    }

    private void atualizarVenda() {
        variaveisGlobais.getVendaDao().setVenda(venda);
        if (!variaveisGlobais.getVendaDao().atualizar()) {
            mensagem = "Erro ao atualizar venda!";
            return;
        }
        for (Pagamento pgmt : venda.getPagamentos()) {
            variaveisGlobais.getPagamentoDao().setPagamento(pgmt);
            while (!variaveisGlobais.getPagamentoDao().atualizar()) {
            }
        }
        for (ItemVenda item : venda.getItens()) {
            variaveisGlobais.getItemDao().setItem(item);
            while (!variaveisGlobais.getItemDao().atualizar()) {
            }
        }
    }

    public void carregarVenda(Venda venda){
        editandoVenda = true;
        venda.setItens(variaveisGlobais.getItemDao().listar(venda));
        venda.setPagamentos(variaveisGlobais.getPagamentoDao().listar(venda));
        this.venda = venda;
    }
    
}
