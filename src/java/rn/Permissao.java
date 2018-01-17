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

    private static Boolean containsId(String permissoes, Permissao permissao) {
        for (String str : permissoes.split(",")) {
            int id = 0;
            try {
                id = Integer.valueOf(str.trim());
            } catch (NumberFormatException ex) {
            }
            if (permissao.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private static Boolean containsDescricao(String permissoes, Permissao permissao) {
        for (String str : permissoes.split(",")) {
            if (permissao.getDescricao().equalsIgnoreCase(str.trim())) {
                return true;
            }
        }
        return false;
    }
    
    public static Boolean temPermissao(String permissoes, Permissao permissao){
        return containsId(permissoes, permissao) ? true : containsDescricao(permissoes, permissao);
    }
    
    public static String idParaDescricao(String str) {
        String ret = " ";
        for (String s : str.split(",")) {
            if (s.isEmpty()) {
                continue;
            }
            int id = 0;
            try {
                id = Integer.valueOf(s.trim());
            } catch (NumberFormatException ex) {
            }
            if (id == 0) {
                continue;
            }
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
            if (s.isEmpty()) {
                continue;
            }
            String desc = s.trim();
            Permissao p = getPermissaoPorDescricao(desc);
            if (p != null) {
                ret += p.getId() + ",";
            }
        }
        return ret.substring(0, ret.length() - 1).trim();
    }

    @Override
    public String toString() {
        return getDescricao() + " (" + getId() + ")";
    }

}
