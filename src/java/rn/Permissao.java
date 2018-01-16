package rn;

public enum Permissao {

    CADASTRO_USUARIO(1, "Cadastro de usuário"),
    CADASTRO_PRODUTO(2, "Cadastro de produto"),
    CADASTRO_CLIENTE(3, "Cadastro de cliente"),
    CADASTRO_ESTOQUE(4, "Cadastro de estoque"),
    DESCONTO_ITEM_VENDA(5, "Desconto no item de venda"),
    CONFIGURACOES(6, "Configurações");

    private final Integer id;
    private final String descricao;

    Permissao(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Permissao getPermissaoPorId(Integer id) {
        Permissao[] permissoes = values();
        for (Permissao permissao : permissoes) {
            if (permissao.getId().equals(id)) {
                return permissao;
            }
        }
        return null;
    }

    public static Permissao getPermissaoPorDescricao(String descricao) {
        Permissao[] permissoes = values();
        for (Permissao permissao : permissoes) {
            if (permissao.getDescricao().equalsIgnoreCase(descricao.trim())) {
                return permissao;
            }
        }
        return null;
    }

    public static String idParaDescricao(String str) {
        String ret = " ";
        for (String s : str.split(",")) {
            if(s.isEmpty()){
                continue;
            }
            int id = Integer.valueOf(s.trim());
            Permissao p = getPermissaoPorId(id);
            if (p != null) {
                ret += p.getDescricao() + ",";
            }
        }
        return ret.substring(0, ret.length() - 1).trim();
    }

    public static String descricaoParaId(String str) {
        String ret = " ";
        for (String s : str.split(",")) {
            if(s.isEmpty()){
                continue;
            }
            String desc = s.trim();
            Permissao p = getPermissaoPorDescricao(desc);
            if (p != null) {
                ret += p.getId()+ ",";
            }
        }
        return ret.substring(0, ret.length() - 1).trim();
    }

    @Override
    public String toString() {
        return getDescricao() + " (" + getId() + ")";
    }

}
