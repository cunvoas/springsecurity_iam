package com.github.cunvoas.iam.web.front.delegation;

public class DelegationRoleForm {
	
	private Integer id;
	private String code;
	private String libelle;
	/**
	 * Getter for id.
	 * @return the id
	 */
	protected Integer getId() {
		return id;
	}
	/**
	 * Setter for id.
	 * @param id the id to set
	 */
	protected void setId(Integer id) {
		this.id = id;
	}
	/**
	 * Getter for code.
	 * @return the code
	 */
	protected String getCode() {
		return code;
	}
	/**
	 * Setter for code.
	 * @param code the code to set
	 */
	protected void setCode(String code) {
		this.code = code;
	}
	/**
	 * Getter for libelle.
	 * @return the libelle
	 */
	protected String getLibelle() {
		return libelle;
	}
	/**
	 * Setter for libelle.
	 * @param libelle the libelle to set
	 */
	protected void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
}
