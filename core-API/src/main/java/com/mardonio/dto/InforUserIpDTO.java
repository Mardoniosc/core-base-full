package com.mardonio.dto;

import java.io.Serializable;

import com.mardonio.domain.InforUserIp;

public class InforUserIpDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String status;
	private String country;
	private String countryCode;
	private String region;
	private String regionName;
	private String city;
	private String zip;
	private Float lat;
	private Float lon;
	private String timezone;
	private String isp;
	private String org;
	private String query;
	
	private Integer idUser;
	
	public InforUserIpDTO() {}

	public InforUserIpDTO(InforUserIp obj) {
		super();
		id = obj.getId();
		status = obj.getStatus();
		country = obj.getCountry();
		countryCode = obj.getCountryCode();
		region = obj.getRegion();
		regionName = obj.getRegionName();
		city = obj.getCity();
		zip = obj.getZip();
		lat = obj.getLat();
		lon = obj.getLon();
		timezone = obj.getTimezone();
		isp = obj.getIsp();
		org = obj.getOrg();
		query = obj.getQuery();
		idUser = obj.getUsuario().getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLon() {
		return lon;
	}

	public void setLon(Float lon) {
		this.lon = lon;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}	

}
