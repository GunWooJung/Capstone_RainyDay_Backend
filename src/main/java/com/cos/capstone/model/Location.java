package com.cos.capstone.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Location {

	@Id
	@Column(name = "location_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long locationId;

	@ManyToOne
	@JoinColumn(name = "scheduleId")
	private Schedule scheduleId;
	
	@Column(name = "name")	//이름
	private String name;
	
	@Column(name = "lat")	//위도 경도 주소 ny ny 지역코드
	private double lat;
	
	@Column(name = "lng")
	private double lng;
	
    @Column(name = "depart_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime departTime;
    
    @Column(name = "dest_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime destTime;
	
    @Column(name = "nx")
	private int nx;
	
	@Column(name = "ny")
	private int ny;
	
	@Column(name = "regioncode")
	private String regioncode;
	
	public Location(Location other) {
	        this.name = other.name;
	        this.lat = other.lat;
	        this.lng = other.lng;
	        this.departTime = other.departTime;
	        this.destTime = other.destTime;
	        this.nx = other.nx;
	        this.ny = other.ny;
	        this.regioncode = other.regioncode;
	 }
	
}
