package com.finalproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("categorie")
public class CategorieDTO {
	private int categorieNo;
	private String categorieName;
	
	
	public int getCategorieNo() {
		return categorieNo;
	}
	public void setCategorieNo(int categorieNo) {
		this.categorieNo = categorieNo;
	}
	public String getCategorieName() {
		return categorieName;
	}
	public void setCategorieName(String categorieName) {
		this.categorieName = categorieName;
	}
	
	
	@Override
	public String toString() {
		return "CategorieDTO [categorieNo=" + categorieNo + ", categorieName=" + categorieName + "]";
	}
	

}
