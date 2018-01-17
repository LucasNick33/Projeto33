package dao;

import beans.ItemVenda;
import beans.Venda;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ItemVendaDao {
    
    private ItemVenda item;

    public ItemVenda getItem() {
        return item;
    }

    public void setItem(ItemVenda item) {
        this.item = item;
    }
    
    public Boolean inserir(){
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try{
            t = s.beginTransaction();
            s.save(item);
            t.commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if(t != null){
                t.rollback();
            }
        }
        return false;
    }
    
    public Boolean atualizar(){
        Session s = BaseDao.getConexao();
        Transaction t = null;
        try{
            t = s.beginTransaction();
            s.update(item);
            t.commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            if(t != null){
                t.rollback();
            }
        }
        return false;
    }
    
    public List<ItemVenda> listar(Venda venda){
        Session s = BaseDao.getConexao();
        List<ItemVenda> itens = new ArrayList<>();
        try{
            Query query = s.createQuery("SELECT ItemVenda FROM ItemVenda INNER JOIN Venda v WHERE v.id = ?");
            query.setParameter(0, venda.getId());
            itens = query.list();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    return itens;
    }
    
}
