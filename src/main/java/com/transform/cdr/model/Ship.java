package com.transform.cdr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ship")
public class Ship implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "company_id")
	private int companyId;
	@Column(name = "monthly_fee")
	private double monthlyFee;
	@Column(name = "static_ip_fee")
	private double staticIpFee;
	@Column(name = "account_type")
	private int accountType;
	@Column(name = "voice_to_terrestial")
	private double voiceToTerrestial;
	@Column(name = "voice_to_cellular")
	private double voiceToCellular;
	/*
	 * @Column(name = "price1") private double price1;
	 * 
	 * @Column(name = "price2") private double price2;
	 */
	@Column(name = "imsi1")
	private String imsi1;
	@Column(name = "imsi2")
	private String imsi2;
	@Column(name = "fee_static_charge")
	private double feeAndStaticCharge;
	@Column(name = "mapping_name")
	private String mappingName;
	@Column(name = "iridium_citadel_monthly_fee")
	private double iridiumCitadelMonthlyFee;
	@Column(name = "voice_rebate")
	private double voiceRebate;
	@Column(name = "data_rebate")
	private double dataRebate;
	@Transient
	private String shipType;

	public Ship() {
		super();
	}

	public Ship(int id, String name, int companyId, double monthlyFee, double staticIpFee, int accountType,
			double voiceToTerrestial,
			double voiceToCellular/* , double price1, double price2 */, String imsi1, String imsi2,
			double feeAndStaticCharge, String mappingName, double iridiumCitadelMonthlyFee, String shipType,
			double voiceRebate, double dataRebate) {
		super();
		this.id = id;
		this.name = name;
		this.companyId = companyId;
		this.monthlyFee = monthlyFee;
		this.staticIpFee = staticIpFee;
		this.accountType = accountType;
		this.voiceToTerrestial = voiceToTerrestial;
		this.voiceToCellular = voiceToCellular;
		/*
		 * this.price1 = price1; this.price2 = price2;
		 */
		this.imsi1 = imsi1;
		this.imsi2 = imsi2;
		this.feeAndStaticCharge = feeAndStaticCharge;
		this.mappingName = mappingName;
		this.iridiumCitadelMonthlyFee = iridiumCitadelMonthlyFee;
		this.shipType = shipType;
		this.voiceRebate = voiceRebate;
		this.dataRebate = dataRebate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public double getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	public double getStaticIpFee() {
		return staticIpFee;
	}

	public void setStaticIpFee(double staticIpFee) {
		this.staticIpFee = staticIpFee;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public double getVoiceToTerrestial() {
		return voiceToTerrestial;
	}

	public void setVoiceToTerrestial(double voiceToTerrestial) {
		this.voiceToTerrestial = voiceToTerrestial;
	}

	public double getVoiceToCellular() {
		return voiceToCellular;
	}

	public void setVoiceToCellular(double voiceToCellular) {
		this.voiceToCellular = voiceToCellular;
	}

	/*
	 * public double getPrice1() { return price1; }
	 * 
	 * public void setPrice1(double price1) { this.price1 = price1; }
	 * 
	 * public double getPrice2() { return price2; }
	 * 
	 * public void setPrice2(double price2) { this.price2 = price2; }
	 */
	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public String getImsi1() {
		return imsi1;
	}

	public void setImsi1(String imsi1) {
		this.imsi1 = imsi1;
	}

	public String getImsi2() {
		return imsi2;
	}

	public void setImsi2(String imsi2) {
		this.imsi2 = imsi2;
	}

	public double getFeeAndStaticCharge() {
		return feeAndStaticCharge;
	}

	public void setFeeAndStaticCharge(double feeAndStaticCharge) {
		this.feeAndStaticCharge = feeAndStaticCharge;
	}

	public String getMappingName() {
		return mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	public double getIridiumCitadelMonthlyFee() {
		return iridiumCitadelMonthlyFee;
	}

	public void setIridiumCitadelMonthlyFee(double iridiumCitadelMonthlyFee) {
		this.iridiumCitadelMonthlyFee = iridiumCitadelMonthlyFee;
	}

	public double getVoiceRebate() {
		return voiceRebate;
	}

	public void setVoiceRebate(double voiceRebate) {
		this.voiceRebate = voiceRebate;
	}

	public double getDataRebate() {
		return dataRebate;
	}

	public void setDataRebate(double dataRebate) {
		this.dataRebate = dataRebate;
	}

}
