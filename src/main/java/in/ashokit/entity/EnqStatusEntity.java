package in.ashokit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="ALT_ENQSTATUS")
@Data
public class EnqStatusEntity
{
	@Id
	@GeneratedValue
	private Integer statusId;
	private String statusName;
}
