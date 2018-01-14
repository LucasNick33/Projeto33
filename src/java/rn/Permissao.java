package rn;

public enum Permissao {
    
    CADASTRO_USUARIO(1, "Cadastro de usuário"),
    CADASTRO_PRODUTO(2, "Cadastro de produto"),
    CADASTRO_CLIENTE(3, "Cadastro de cliente"),
    DESCONTO_VENDA(4, "Desconto na venda"),
    DESCONTO_ITEM_VENDA(5, "Desconto no item de venda");
    
    private final Integer id;
    private final String descricao;
    
    Permissao(Integer id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return getDescricao() + " (" + getId() + ")";
    }
    
}
