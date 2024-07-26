
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement st;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public int cadastrarProduto(ProdutosDTO produto) {

        conn = conectaDAO.connectDB();
        int status;
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES(?,?,?)";
        try {

            st = conn.prepareStatement(sql);
            st.setString(1, produto.getNome());
            st.setInt(2, produto.getValor());
            st.setString(3, produto.getStatus());
            status = st.executeUpdate();
            return status;

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: ");
            return ex.getErrorCode();
        }

    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        
        conn = conectaDAO.connectDB();
        String sql = "SELECT id, nome, valor, status FROM produtos ORDER BY nome ASC";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ArrayList<ProdutosDTO> listagem = new ArrayList<>();
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                listagem.add(produto);
            }

            return listagem;

        } catch (SQLException e) {
            return null;
        }

    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        
        conn = conectaDAO.connectDB();
        String sql = "SELECT id, nome, valor, status FROM produtos WHERE status = 'Vendido' ORDER BY nome ASC";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ArrayList<ProdutosDTO> listagem = new ArrayList<>();
            
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                listagem.add(produto);
            }

            return listagem;

        } catch (SQLException e) {
            return null;
        }

    }
    
    public void venderProduto(int id) {
        
        conn = conectaDAO.connectDB();

        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
        }
        
    }

}
