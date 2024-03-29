package metier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogueMetierImpl implements ICatalogueMetier {

	@Override
	public void addproduit(Produits p) {
		Connection conn=SingletonConnection.getConnection();
		try {
			PreparedStatement ps= conn.prepareStatement("Insert INTO produits (REF_PROD,DESIGNATION,PRIX,QUANTITE) values(?,?,?,?)");
			ps.setString(1, p.getReference());
			ps.setString(2, p.getDesignation());
			ps.setDouble(3, p.getPrix());
			ps.setInt(4, p.getQuantite());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public List<Produits> listProduit() {
		List<Produits> prods=new ArrayList<Produits>();
		Connection conn=SingletonConnection.getConnection();
		try {
			PreparedStatement ps= conn.prepareStatement("select * from produits");
			ResultSet rs =ps.executeQuery();
			while(rs.next()) {
				Produits p=new Produits();
				p.setReference(rs.getString("REF_PROD"));
				p.setDesignation(rs.getString("DESIGNATION"));
				p.setPrix(rs.getDouble("PRIX"));
				p.setQuantite(rs.getInt("QUANTITE"));
				prods.add(p);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prods;
	}

	@Override
	public List<Produits> produitParMc(String mc) {
		List<Produits> prods=new ArrayList<Produits>();
		Connection conn=SingletonConnection.getConnection();
		try {
			PreparedStatement ps= conn.prepareStatement("select * from produits where DESIGNATION like ?");
			ps.setString(1, "%"+mc+"%");
			ResultSet rs =ps.executeQuery();
			while(rs.next()) {
				Produits p=new Produits();
				p.setReference(rs.getString("REF_PROD"));
				p.setDesignation(rs.getString("DESIGNATION"));
				p.setPrix(rs.getDouble("PRIX"));
				p.setQuantite(rs.getInt("QUANTITE"));
				prods.add(p);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prods;
	}

	@Override
	public Produits getProduit(String ref) {
		Produits p=null;
		Connection conn=SingletonConnection.getConnection();
		try {
			PreparedStatement ps= conn.prepareStatement("select * from produits where REF_PROD=?");
			ps.setString(1, ref);
			ResultSet rs =ps.executeQuery();
			if(rs.next()) {
			    p=new Produits();
				p.setReference(rs.getString("REF_PROD"));
				p.setDesignation(rs.getString("DESIGNATION"));
				p.setPrix(rs.getDouble("PRIX"));
				p.setQuantite(rs.getInt("QUANTITE"));
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(p==null) {throw new RuntimeException("produit "+ref+" introuvable");}
		return p;
	}

	@Override
	public void updateProduit(Produits p) {
		// TODO Auto-generated method stub
		Connection conn=SingletonConnection.getConnection();
		try {
			PreparedStatement ps= conn.prepareStatement("update produits set DESIGNATION=?,PRIX=?,QUANTITE=? where REF_PROD=?");
			ps.setString(1, p.getDesignation());
			ps.setDouble(2, p.getPrix());
			ps.setInt(3, p.getQuantite());
			ps.setString(4, p.getReference());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void deleteProduit(String ref) {
		// TODO Auto-generated method stub
		Connection conn=SingletonConnection.getConnection();
		try {
			PreparedStatement ps= conn.prepareStatement("delete FROM produits where REF_PROD=?");
			ps.setString(1, ref);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
