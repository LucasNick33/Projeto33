package dao;

import beans.Estoque;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

public class EstoqueDao {
    
    private Estoque estoque;

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }
    
    public List<String> listarNomes(){
        Session s = BaseDao.getConexao();
        List<String> estoques = new ArrayList<>();
        try{
            estoques = s.createQuery("SELECT nome FROM Estoque GROUP BY nome").list();
        } catch (Exception ex){
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estoques;
    }
    
}
